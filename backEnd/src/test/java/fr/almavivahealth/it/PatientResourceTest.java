package fr.almavivahealth.it;

import static fr.almavivahealth.constants.Constants.BEARER;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import fr.almavivahealth.security.jwt.TokenProvider;
import fr.almavivahealth.service.dto.BulkResult;
import fr.almavivahealth.service.dto.DietDTO;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.dto.TextureDTO;
import fr.almavivahealth.utils.TestUtils;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientResourceTest {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String AUTHORIZATION = "Authorization";

	private static final String FIRSTNAME = "Ben";

	private static final String EMAIL = "ben.lockwood@gmail.com";

	private static final String BLOOD_GROUP = "A+";

	private static final String DIET_NORMALE = "Normale";

	private static final String TEXTURE_NORMALE = DIET_NORMALE;

	private static final String SEX = "Homme";

	private static final long ID = 1L;

	private static final String LASTNAME = "Zotito";

	private static String token;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Autowired
	private TokenProvider tokenProvider;

	@Before
	public void setUp() {
		token = tokenProvider.createToken();
	}


	private static PatientDTO createPatientDTO() {
		return PatientDTO.builder()
				.firstName(FIRSTNAME)
				.lastName(LASTNAME)
				.email(EMAIL)
				.sex(SEX)
				.bloodGroup(BLOOD_GROUP)
				.state(true)
				.texture(createTextureDTO())
				.diets(Arrays.asList(createDietDTO()))
				.build();
	}

	private static TextureDTO createTextureDTO() {
		return TextureDTO.builder()
				.id(ID)
				.name(TEXTURE_NORMALE)
				.build();
	}

	private static DietDTO createDietDTO() {
		return DietDTO.builder()
				.id(ID)
				.name(DIET_NORMALE)
				.build();
	}

	@Test
	@Sql(scripts = "/scripts/insert_bdd.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void shouldCreatePatient() throws IOException, Exception {
		// Given
		final HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, BEARER + token);
		headers.set(CONTENT_TYPE, APPLICATION_JSON);
		final HttpEntity<PatientDTO> request = new HttpEntity<>(createPatientDTO(), headers);

		// When
		final ResponseEntity<PatientDTO> response = restTemplate
				.postForEntity("http://localhost:" + port + "/api/patients", request, PatientDTO.class);

		// Then
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.SC_CREATED);
	}

	@Test
	public void shouldUpdatePatient() throws IOException, Exception {
		// Given
		final HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, BEARER + token);
		headers.set(CONTENT_TYPE, APPLICATION_JSON);
		final PatientDTO patient = createPatientDTO();
		patient.setId(ID);
		patient.setFirstName("Benjamin");
		final HttpEntity<PatientDTO> request = new HttpEntity<>(patient, headers);

		// When
		final ResponseEntity<PatientDTO> response = restTemplate.exchange("http://localhost:" + port + "/api/patients",
				HttpMethod.PUT, request, PatientDTO.class);

		// Then
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.SC_OK);
	}

	@Test
	public void shouldImportPatientFile() throws IOException {
		// Given
		final HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, BEARER + token);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		final Path path = Paths.get("src", "test", "resources", "patients", "import", "patients_ok.csv");
		final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("inputfile", TestUtils.getResource(path));

		final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		// When
		final ResponseEntity<BulkResult> response = restTemplate.exchange(
				"http://localhost:" + port + "/api/patients/import", HttpMethod.POST, requestEntity, BulkResult.class);

		// Then
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.SC_OK);
	}

	@Test
	public void shouldFailDuringImportPatientFileWhenCSVHasTooManyColumns() throws IOException {
		// Given
		final HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, BEARER + token);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		final Path path = Paths.get("src", "test", "resources", "patients", "import", "patients_ko1.csv");
		final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("inputfile", TestUtils.getResource(path));

		final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		// When
		final ResponseEntity<BulkResult> response = restTemplate.exchange(
				"http://localhost:" + port + "/api/patients/import", HttpMethod.POST, requestEntity, BulkResult.class);

		// Then
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void shouldFailDuringImportPatientFileWhenCSVHasNotValidExtension() throws IOException {
		// Given
		final HttpHeaders headers = new HttpHeaders();
		headers.set(AUTHORIZATION, BEARER + token);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		final Path path = Paths.get("src", "test", "resources", "patients", "import", "patients_ko2.txt");
		final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("inputfile", TestUtils.getResource(path));

		final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		// When
		final ResponseEntity<BulkResult> response = restTemplate.exchange(
				"http://localhost:" + port + "/api/patients/import", HttpMethod.POST, requestEntity, BulkResult.class);

		// Then
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

}

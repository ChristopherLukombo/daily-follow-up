package fr.almavivahealth.config;

import static fr.almavivahealth.constants.Constants.AUTHORIZATION;
import static fr.almavivahealth.constants.Constants.BEARER;
import static fr.almavivahealth.constants.Constants.HEADER;
import static fr.almavivahealth.constants.Constants.STRING;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import fr.almavivahealth.security.jwt.TokenProvider;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private final TokenProvider tokenProvider;

    @Autowired
    public SwaggerConfig(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    @Profile("dev")
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.almavivahealth.web.rest"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping(StringUtils.EMPTY)
                .globalOperationParameters(getParameters())
                .apiInfo(apiInfo());
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Daily follow up API")
				.description("This API allows to manage Daily follow up.")
				.build();
	}

    private List<Parameter> getParameters() {
         return Stream.of(new ParameterBuilder()
                .name(AUTHORIZATION)
                .modelRef(new ModelRef(STRING))
                .parameterType(HEADER)
                .defaultValue(BEARER + tokenProvider.createToken())
                .required(true)
                .build())
        		.collect(Collectors.toList());
    }

}

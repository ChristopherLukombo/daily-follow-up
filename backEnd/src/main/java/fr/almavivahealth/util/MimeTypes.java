package fr.almavivahealth.util;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * The Class MimeTypes.
 */
public final class MimeTypes {

	public static final String MIME_APPLICATION_VND_MSEXCEL = "application/vnd.ms-excel";
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String MIME_TEXT_CSV = "text/csv";

	// Private constructor to prevent instantiation.
	private MimeTypes() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if is matching mime types.
	 *
	 * @param mimeTypes the mime types
	 * @param multipartFile the multipart file
	 * @return true, if is matching mime types
	 */
	public static boolean isMatchingMimeTypes(final List<String> mimeTypes, final MultipartFile multipartFile) {
		if (mimeTypes == null) {
			return false;
		}

		final String contentType = findContentType(multipartFile);
		if (contentType.isEmpty()) {
			return false;
		}

		return mimeTypes.stream()
				.anyMatch(contentType::equalsIgnoreCase);
	}

	private static String findContentType(final MultipartFile multipartFile) {
		return Optional.ofNullable(multipartFile)
				.map(MultipartFile::getContentType)
				.orElse(StringUtils.EMPTY);
	}


}

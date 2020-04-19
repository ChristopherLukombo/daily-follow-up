package fr.almavivahealth.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class MimeTypes.
 */
public final class MimeTypes {

	public static final String MIME_APPLICATION_VND_MSEXCEL = "application/vnd.ms-excel";
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String MIME_TEXT_CSV = "text/csv";
	
	/**
	 * Checks if is matching mime types.
	 *
	 * @param mimeTypes the mime types
	 * @param multipartFile the multipart file
	 * @return true, if is matching mime types
	 */
	public static boolean isMatchingMimeTypes(final List<String> mimeTypes, final MultipartFile multipartFile) {
		return mimeTypes.stream()
				.anyMatch(multipartFile.getContentType()::equalsIgnoreCase);
	}
	
	private MimeTypes() {
		// private constructor to prevent instanciation of class.
	}
}

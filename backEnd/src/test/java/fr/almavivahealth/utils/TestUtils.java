package fr.almavivahealth.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public final class TestUtils {

	public static Resource getResource(final Path path) throws IOException {
		final File file = path.toFile();
		return new FileSystemResource(file);
	}

}

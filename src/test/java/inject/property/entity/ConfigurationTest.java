package inject.property.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void shouldHaveEmptyFilesList() {

		// Given
		Configuration config = new Configuration();

		// When
		// Then
		assertNotNull(config.getFiles());
	}

	@Test
	public void shouldHaveOneFileInTheList() {

		// Given
		Configuration config = new Configuration();

		// When
		config.addFile("emails.properties");

		// Then
		assertNotNull(config.getFiles());
		assertEquals(1, config.getFiles().size());
	}

	@Test
	public void shouldHaveTwoFilesInTheList() {

		// Given
		String[] files = new String[] { "emails.properties",
				"endpoints.properties" };
		Configuration config = new Configuration();

		// When
		config.addFiles(files);

		// Then
		assertNotNull(config.getFiles());
		assertEquals(files.length, config.getFiles().size());
	}
}

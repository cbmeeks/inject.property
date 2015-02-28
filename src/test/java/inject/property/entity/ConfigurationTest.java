package inject.property.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
	public void shouldContainFourFilesInTheList() {

		// Given
		String[] files = new String[] { "emails.properties",
				"endpoints.properties", "phones.properties",
				"admins.properties" };
		Configuration config = new Configuration();

		// When
		config.addFiles(files);

		// Then
		assertNotNull(config.getFiles());
		for (String file : files) {
			assertTrue(config.getFiles().contains(file));
		}
		assertEquals(files.length, config.getFiles().size());
	}
}

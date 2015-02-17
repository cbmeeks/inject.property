package inject.property.readers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import inject.property.annotations.PropertiesFiles;
import inject.property.config.ConfigFile;

import java.io.ByteArrayInputStream;

import org.junit.Test;

public class AnnotationConfigurationReaderTest {

	@Test
	public void shouldReturnAListOfTwoFilesGivenInTheAnnotation() {

		// Given
		AnnotationConfigurationReader reader = new AnnotationConfigurationReader();

		@PropertiesFiles({ "emails.properties", "endpoints.xml" })
		class AnnotatedClass {

		}
		String annotatedClassName = AnnotatedClass.class.getName();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				annotatedClassName.getBytes());

		// When
		ConfigFile configFile = reader.read(inputStream);

		// Then
		assertNotNull(configFile);
		assertNotNull(configFile.getPropertyFiles());
		assertEquals(2, configFile.getPropertyFiles().size());
		assertEquals("emails.properties", configFile.getPropertyFiles().get(0));
		assertEquals("endpoints.xml", configFile.getPropertyFiles().get(1));
	}

	@Test
	public void shouldReturnANullConfigFile() {

		// Given
		AnnotationConfigurationReader reader = new AnnotationConfigurationReader();

		class NotAnnotatedClass {
		}
		String annotatedClassName = NotAnnotatedClass.class.getName();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				annotatedClassName.getBytes());

		// When
		ConfigFile configFile = reader.read(inputStream);

		// Then
		assertNull(configFile);
	}
}

package inject.property.readers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import inject.property.annotations.PropertiesFiles;
import inject.property.config.ConfigFile;
import inject.property.providers.FileProvider;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationReaderTest {

	@Mock
	private FileProvider provider;

	@InjectMocks
	private ConfigurationReader reader;

	@Test
	public void shouldReturnAListOfTwoPropertiesFiles() {

		// Given
		final String xmlConfigFile = "<propertiesfiles><propertiesfile>wsendpoints.properties</propertiesfile><propertiesfile>emails.properties</propertiesfile></propertiesfiles>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(
				inputStream);

		// When
		ConfigFile configFile = reader.read("inject.property.xml");

		// Then
		assertNotNull(configFile);
		assertNotNull(configFile.getPropertiesFile());
		assertEquals(2, configFile.getPropertiesFile().size());
	}

	@Test
	public void shouldReturnNullBecauseInvalidXMLFile() {

		// Given
		final String xmlConfigFile = "<dummy></dummy>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(
				inputStream);

		// When
		ConfigFile configFile = reader.read("dummy.xml");

		// Then
		assertNull(configFile);
	}

	@Test
	public void shouldReturnNullBecauseNotXMLFile() {

		// Given
		final String xmlConfigFile = "This file is not XML";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(
				inputStream);

		// When
		ConfigFile configFile = reader.read("inject.property.txt");

		// Then
		assertNull(configFile);
	}

	@Test
	public void shouldReturnAConfigFileObjectWithNoProperties() {

		// Given
		final String xmlConfigFile = "<propertiesfiles></propertiesfiles>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(
				inputStream);

		// When
		ConfigFile configFile = reader.read("inject.property.xml");

		// Then
		assertNotNull(configFile);
		assertNull(configFile.getPropertiesFile());
	}

	@Test
	public void shouldReturnAListOfOneEmptyElement() {

		// Given
		final String xmlConfigFile = "<propertiesfiles><propertiesfile></propertiesfile></propertiesfiles>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(
				inputStream);

		// When
		ConfigFile configFile = reader.read("inject.property.xml");

		// Then
		assertNotNull(configFile);
		assertNotNull(configFile.getPropertiesFile());
		assertEquals(1, configFile.getPropertiesFile().size());
		assertEquals("", configFile.getPropertiesFile().get(0));
	}

	@Test
	public void shouldReturnAListOfTwoFilesGivenInTheAnnotation() {

		// Given
		@PropertiesFiles({ "emails.properties", "endpoints.xml" })
		class AnnotatedClass {

		}

		// When
		ConfigFile configFile = reader.read(AnnotatedClass.class);

		// Then
		assertNotNull(configFile);
		assertNotNull(configFile.getPropertiesFile());
		assertEquals(2, configFile.getPropertiesFile().size());
		assertEquals("emails.properties", configFile.getPropertiesFile().get(0));
		assertEquals("endpoints.xml", configFile.getPropertiesFile().get(1));
	}

	@Test
	public void shouldReturnANullConfigFile() {

		// Given
		class NonAnnotatedClass {
		}

		// When
		ConfigFile configFile = reader.read(NonAnnotatedClass.class);

		// Then
		assertNull(configFile);
	}
}

package inject.property.readers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import inject.property.config.ConfigFile;
import inject.property.readers.XMLConfigurationReader;

import java.io.ByteArrayInputStream;

import org.junit.Test;

public class XMLConfigurationReaderTest {

	@Test
	public void shouldReturnAListOfTwoPropertiesFiles() {

		// Given
		final XMLConfigurationReader mapper = new XMLConfigurationReader();
		final String xmlConfigFile = "<propertiesfiles><propertiesfile>wsendpoints.properties</propertiesfile><propertiesfile>emails.properties</propertiesfile></propertiesfiles>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());

		// When
		ConfigFile configFile = mapper.read(inputStream);

		// Then
		assertNotNull(configFile);
		assertNotNull(configFile.getPropertyFiles());
		assertEquals(2, configFile.getPropertyFiles().size());
	}

	@Test
	public void shouldReturnNullBecauseInvalidXMLFile() {

		// Given
		final XMLConfigurationReader mapper = new XMLConfigurationReader();
		final String xmlConfigFile = "<dummy></dummy>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());

		// When
		ConfigFile configFile = mapper.read(inputStream);

		// Then
		assertNull(configFile);
	}

	@Test
	public void shouldReturnNullBecauseNotXMLFile() {

		// Given
		final XMLConfigurationReader mapper = new XMLConfigurationReader();
		final String xmlConfigFile = "This file is not XML";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());

		// When
		ConfigFile configFile = mapper.read(inputStream);

		// Then
		assertNull(configFile);
	}

	@Test
	public void shouldReturnAConfigFileObjectWithNoProperties() {

		// Given
		final XMLConfigurationReader mapper = new XMLConfigurationReader();
		final String xmlConfigFile = "<propertiesfiles></propertiesfiles>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());

		// When
		ConfigFile configFile = mapper.read(inputStream);

		// Then
		assertNotNull(configFile);
		assertNull(configFile.getPropertyFiles());
	}

	@Test
	public void shouldReturnAListOfOneEmptyElement() {

		// Given
		final XMLConfigurationReader mapper = new XMLConfigurationReader();
		final String xmlConfigFile = "<propertiesfiles><propertiesfile></propertiesfile></propertiesfiles>";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(
				xmlConfigFile.getBytes());

		// When
		ConfigFile configFile = mapper.read(inputStream);

		// Then
		assertNotNull(configFile);
		assertNotNull(configFile.getPropertyFiles());
		assertEquals(1, configFile.getPropertyFiles().size());
		assertEquals("", configFile.getPropertyFiles().get(0));
	}
}

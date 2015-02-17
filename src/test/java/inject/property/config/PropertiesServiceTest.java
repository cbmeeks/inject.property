package inject.property.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import inject.property.providers.FileProvider;
import inject.property.readers.ConfigurationReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesServiceTest {

	private static final String TWO_EMAILS = "admin=admin@company.org\nuser1=user1@company.org";
	private static final String THREE_ENDPOINTS = "billing=http:\\\\billing.company.org\npayrolls=http:\\\\payrolls.company.org\nhr=http:\\\\hr.company.org";

	@Mock
	private FileProvider fileProvider;

	@Mock
	private ConfigurationReader reader;

	@InjectMocks
	PropertiesService service;

	@Test
	public void shouldReturnFivePropertiesFromTwoDifferentFiles() {

		// Given

		// Mock file 1
		String propertiesFile1 = "emails.properties";
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				TWO_EMAILS.getBytes());
		when(fileProvider.asInputStream(propertiesFile1)).thenReturn(
				inputStream);

		// Mock file 2
		String propertiesFile2 = "endpoints.properties";
		inputStream = new ByteArrayInputStream(THREE_ENDPOINTS.getBytes());
		when(fileProvider.asInputStream(propertiesFile2)).thenReturn(
				inputStream);

		final List<String> propertiesFiles = new ArrayList<>();
		propertiesFiles.add(propertiesFile1);
		propertiesFiles.add(propertiesFile2);

		final ConfigFile configFile = new ConfigFile();
		configFile.setPropertiesFile(propertiesFiles);
		when(reader.read(anyString())).thenReturn(configFile);

		// When
		Properties properties = service
				.loadPropertiesFromFile("inject.propertie.xml");

		// Then
		assertNotNull(properties);
		assertEquals(5, properties.size());
		assertEquals("admin@company.org", properties.getProperty("admin"));
		assertEquals("user1@company.org", properties.getProperty("user1"));
		assertEquals("http:\\billing.company.org",
				properties.getProperty("billing"));
		assertEquals("http:\\payrolls.company.org",
				properties.getProperty("payrolls"));
		assertEquals("http:\\hr.company.org", properties.getProperty("hr"));
	}

	@Test
	public void shouldReturnAnEmptyPropertiesObject() {

		// Given
		List<String> propertiesFiles = new ArrayList<>();

		final ConfigFile configFile = new ConfigFile();
		configFile.setPropertiesFile(propertiesFiles);
		when(reader.read(anyString())).thenReturn(configFile);

		// When
		Properties properties = service
				.loadPropertiesFromFile("inject.properties.xml");

		// Then
		assertNotNull(properties);
		assertEquals(0, properties.size());
	}

	@Test
	public void shouldReturnThreePropertiesFromTwoFilesOneOfThemEmpty() {

		// Given

		// Mock file 1
		String propertiesFile1 = "emails.properties";
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				TWO_EMAILS.getBytes());
		when(fileProvider.asInputStream(propertiesFile1)).thenReturn(
				inputStream);

		// Mock file 2
		String propertiesFile2 = "endpoints.properties";
		inputStream = new ByteArrayInputStream("".getBytes());
		when(fileProvider.asInputStream(propertiesFile2)).thenReturn(
				inputStream);

		List<String> propertiesFiles = new ArrayList<>();
		propertiesFiles.add(propertiesFile1);
		propertiesFiles.add(propertiesFile2);

		final ConfigFile configFile = new ConfigFile();
		configFile.setPropertiesFile(propertiesFiles);
		when(reader.read(anyString())).thenReturn(configFile);

		// When
		Properties properties = service
				.loadPropertiesFromFile("inject.property.xml");

		// Then
		assertNotNull(properties);
		assertEquals(2, properties.size());
		assertEquals("admin@company.org", properties.getProperty("admin"));
		assertEquals("user1@company.org", properties.getProperty("user1"));
	}
	
	// TODO Add tests for loadPropertiesFromAnnotation
}

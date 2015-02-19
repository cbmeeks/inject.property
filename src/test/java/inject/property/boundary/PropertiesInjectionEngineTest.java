package inject.property.boundary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import inject.property.control.FileProvider;
import inject.property.entity.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesInjectionEngineTest {

	private static final String EMAILS = "emails.properties";
	private static final String EMAILS_FILE = "admin=admin@company.com\nuser1=user1@company.com";
	private static final String ENDPOINTS = "endpoints.properties";
	private static final String ENDPOINTS_FILE = "billing=http:////billing.company.com\nivr=http:////ivr.company.com";

	@Mock
	private FileProvider fileProvider;

	@InjectMocks
	private PropertiesInjectionEngine engine;

	@Test
	public void shouldReturnFourProperties() throws IOException {

		// Given

		final Configuration configFile = new Configuration();
		configFile.addFile(EMAILS);
		configFile.addFile(ENDPOINTS);

		when(fileProvider.asInputStream(EMAILS)).thenReturn(
				new ByteArrayInputStream(EMAILS_FILE.getBytes()));
		when(fileProvider.asInputStream(ENDPOINTS)).thenReturn(
				new ByteArrayInputStream(ENDPOINTS_FILE.getBytes()));

		// When
		final Properties props = engine.loadFiles(configFile);

		// Then
		assertEquals("Should return properties", 4, props.size());
		verify(fileProvider, times(2)).asInputStream(anyString());
	}

	@Test
	public void shouldNotReturnAnyPropertyBecauseNoPropertiesFilesSpecified()
			throws IOException {

		// Given
		final Configuration configFile = new Configuration();

		// When
		final Properties props = engine.loadFiles(configFile);

		// Then
		assertEquals("Should not return any property", 0, props.size());
	}

	@Test
	public void shouldNotReturnAnyPropertyBecauseEmptyPropertiesFile()
			throws IOException {

		// Given
		final Configuration configFile = new Configuration();
		configFile.addFile(EMAILS);

		when(fileProvider.asInputStream(EMAILS)).thenReturn(
				new ByteArrayInputStream("".getBytes()));

		// When
		final Properties props = engine.loadFiles(configFile);

		// Then
		assertEquals("Should not return any property", 0, props.size());
		verify(fileProvider, times(1)).asInputStream(anyString());
	}

}

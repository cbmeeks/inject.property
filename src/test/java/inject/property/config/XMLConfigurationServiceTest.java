package inject.property.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import inject.property.providers.FileProvider;
import inject.property.readers.XMLConfigurationReader;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XMLConfigurationServiceTest {

	@Mock
	FileProvider provider;

	@Mock
	XMLConfigurationReader configReader;

	@Mock
	InputStream inputStream;

	@Test
	public void shouldReturnConfigFile() {

		// Given
		when(provider.asInputStream(anyString())).thenReturn(inputStream);
		when(configReader.read(any(InputStream.class))).thenReturn(
				new ConfigFile());
		ConfigurationService service = new XMLConfigurationService(provider,
				configReader);

		// When
		ConfigFile configFile = service.getConfiguration("inject.property.xml");

		// Then
		assertNotNull(configFile);
	}

	@Test
	public void shouldReturnNullBecauseErrorWhenOpeningTheStream() {

		// Given
		when(provider.asInputStream(anyString())).thenReturn(null);
		ConfigurationService service = new XMLConfigurationService(provider,
				configReader);

		// When
		ConfigFile configFile = service.getConfiguration("inject.property.xml");

		// Then
		assertNull(configFile);
	}

	@Test
	public void shouldReturnNullBecauseErrorWhenReadingTheStream() {

		// Given
		when(provider.asInputStream(anyString())).thenReturn(inputStream);
		when(configReader.read(any(InputStream.class))).thenReturn(null);
		ConfigurationService service = new XMLConfigurationService(provider,
				configReader);

		// When
		ConfigFile configFile = service.getConfiguration("inject.property.xml");

		// Then
		assertNull(configFile);
	}
}

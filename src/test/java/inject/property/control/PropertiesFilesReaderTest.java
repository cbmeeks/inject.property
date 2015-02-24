package inject.property.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import inject.property.entity.Configuration;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesFilesReaderTest {

	private static final String EMAILS = "emails.properties";
	private static final String ENDPOINTS = "endpoints.properties";

	@Mock
	private ConfigurationReader configReader;

	@Mock
	private FileProvider provider;

	@InjectMocks
	private PropertiesFilesReader propsReader;

	@Test
	public void shouldReturnFourPropertiesFromTwoFiles() {

		// Given
		final Configuration config = new Configuration();
		config.addFile(EMAILS);
		config.addFile(ENDPOINTS);
		when(configReader.getConfiguration()).thenReturn(config);

		String emails = "admin=admin@company.com\nuser1=user1@company.com";
		when(provider.asInputStreamFromClassPath(EMAILS)).thenReturn(
				new ByteArrayInputStream(emails.getBytes()));

		String endpoints = "billing=billig.company.com\nivr=ivr.company.com";
		when(provider.asInputStreamFromClassPath(ENDPOINTS)).thenReturn(
				new ByteArrayInputStream(endpoints.getBytes()));

		// When
		Properties props = propsReader.getProperties();

		// Then
		assertEquals(4, props.size());
		verify(provider, times(2)).asInputStreamFromClassPath(anyString());
	}

	@Test
	public void shouldReturnTwoPropertiesFromTwoFilesOneOfThemIsEmpty() {

		// Given
		final Configuration config = new Configuration();
		config.addFile(EMAILS);
		config.addFile(ENDPOINTS);
		when(configReader.getConfiguration()).thenReturn(config);

		String emails = "admin=admin@company.com\nuser1=user1@company.com";
		when(provider.asInputStreamFromClassPath(EMAILS)).thenReturn(
				new ByteArrayInputStream(emails.getBytes()));

		String endpoints = "";
		when(provider.asInputStreamFromClassPath(ENDPOINTS)).thenReturn(
				new ByteArrayInputStream(endpoints.getBytes()));

		// When
		Properties props = propsReader.getProperties();

		// Then
		assertEquals(2, props.size());
		verify(provider, times(2)).asInputStreamFromClassPath(anyString());
	}

	@Test
	public void shouldReturnNoPropertiesBecauseFilesDontExist() {

		// Given
		final Configuration config = new Configuration();
		config.addFile(EMAILS);
		config.addFile(ENDPOINTS);
		when(configReader.getConfiguration()).thenReturn(config);

		when(provider.asInputStreamFromClassPath(EMAILS)).thenReturn(null);
		when(provider.asInputStreamFromClassPath(ENDPOINTS)).thenReturn(null);

		// When
		Properties props = propsReader.getProperties();

		// Then
		assertEquals(0, props.size());
		verify(provider, times(2)).asInputStreamFromClassPath(anyString());
	}

	@Test
	public void shouldReturnThreeProperties() {

		// Given
		final Configuration config = new Configuration();
		config.addFile(EMAILS);
		config.addFile(ENDPOINTS);
		when(configReader.getConfiguration()).thenReturn(config);

		String emails = "admin=admin@company.com\nuser1=user1@company.com";
		when(provider.asInputStreamFromClassPath(EMAILS)).thenReturn(
				new ByteArrayInputStream(emails.getBytes()));

		String endpoints = "some dummy text";
		when(provider.asInputStreamFromClassPath(ENDPOINTS)).thenReturn(
				new ByteArrayInputStream(endpoints.getBytes()));

		// When
		Properties props = propsReader.getProperties();

		// Then
		assertEquals(3, props.size());
		verify(provider, times(2)).asInputStreamFromClassPath(anyString());
	}

	@Test
	public void shouldReturnNoPropertiesBecauseNoFilesProvided() {

		// Given
		final Configuration config = new Configuration();
		when(configReader.getConfiguration()).thenReturn(config);

		// When
		Properties props = propsReader.getProperties();

		// Then
		assertEquals(0, props.size());
	}
}

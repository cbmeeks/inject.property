package inject.property.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
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
	private FileProvider fileProvider;

	@Mock
	private FileConfigurationReader fileConfigReader;

	@Mock
	private AnnotationConfigurationReader annotationConfigReader;

	@InjectMocks
	private PropertiesFilesReader propsReader;

	@Mock
	private ClassLoader cl;

	@Test
	public void shouldReturnFourPropertiesFromTwoFiles() {

		// Given
		final Configuration configuration = new Configuration();
		configuration.addFiles(new String[] { EMAILS, ENDPOINTS });
		when(fileConfigReader.getConfiguration(any(ClassLoader.class)))
				.thenReturn(configuration);

		final String emails = "admin=admin@company.com\nuser1=user1@company.com";
		when(
				fileProvider.asInputStreamFromClassPath(eq(EMAILS),
						any(ClassLoader.class))).thenReturn(
				new ByteArrayInputStream(emails.getBytes()));

		final String endpoints = "billing=billig.company.com\nivr=ivr.company.com";
		when(
				fileProvider.asInputStreamFromClassPath(eq(ENDPOINTS),
						any(ClassLoader.class))).thenReturn(
				new ByteArrayInputStream(endpoints.getBytes()));

		// When
		Properties props = propsReader.getPropertiesFromClassPath(cl);

		// Then
		assertEquals(4, props.size());
		verify(fileConfigReader, times(1)).getConfiguration(
				any(ClassLoader.class));
		verify(fileProvider, times(2)).asInputStreamFromClassPath(anyString(),
				any(ClassLoader.class));
	}

	@Test
	public void shouldReturnTwoPropertiesFromTwoFilesOneOfThemIsEmpty() {

		// Given
		final Configuration configuration = new Configuration();
		configuration.addFiles(new String[] { EMAILS, ENDPOINTS });
		when(fileConfigReader.getConfiguration(any(ClassLoader.class)))
				.thenReturn(configuration);

		final String emails = "admin=admin@company.com\nuser1=user1@company.com";
		when(
				fileProvider.asInputStreamFromClassPath(eq(EMAILS),
						any(ClassLoader.class))).thenReturn(
				new ByteArrayInputStream(emails.getBytes()));

		final String endpoints = "";
		when(
				fileProvider.asInputStreamFromClassPath(eq(ENDPOINTS),
						any(ClassLoader.class))).thenReturn(
				new ByteArrayInputStream(endpoints.getBytes()));

		// When
		Properties props = propsReader.getPropertiesFromClassPath(cl);

		// Then
		assertEquals(2, props.size());
		verify(fileConfigReader, times(1)).getConfiguration(
				any(ClassLoader.class));
		verify(fileProvider, times(2)).asInputStreamFromClassPath(anyString(),
				any(ClassLoader.class));
	}

	@Test
	public void shouldReturnNoPropertiesBecauseFilesDontExist() {

		// Given
		final Configuration configuration = new Configuration();
		configuration.addFiles(new String[] { EMAILS, ENDPOINTS });
		when(fileConfigReader.getConfiguration(any(ClassLoader.class)))
				.thenReturn(configuration);

		when(
				fileProvider.asInputStreamFromClassPath(eq(EMAILS),
						any(ClassLoader.class))).thenReturn(null);
		when(
				fileProvider.asInputStreamFromClassPath(eq(ENDPOINTS),
						any(ClassLoader.class))).thenReturn(null);

		// When
		Properties props = propsReader.getPropertiesFromClassPath(cl);

		// Then
		assertEquals(0, props.size());
		verify(fileProvider, times(2)).asInputStreamFromClassPath(anyString(),
				any(ClassLoader.class));
	}

	@Test
	public void shouldReturnThreeProperties() {

		// Given
		final Configuration configuration = new Configuration();
		configuration.addFiles(new String[] { EMAILS, ENDPOINTS });
		when(fileConfigReader.getConfiguration(any(ClassLoader.class)))
				.thenReturn(configuration);

		final String emails = "admin=admin@company.com\nuser1=user1@company.com";
		when(
				fileProvider.asInputStreamFromClassPath(eq(EMAILS),
						any(ClassLoader.class))).thenReturn(
				new ByteArrayInputStream(emails.getBytes()));

		final String endpoints = "some dummy text";
		when(
				fileProvider.asInputStreamFromClassPath(eq(ENDPOINTS),
						any(ClassLoader.class))).thenReturn(
				new ByteArrayInputStream(endpoints.getBytes()));

		// When
		Properties props = propsReader.getPropertiesFromClassPath(cl);

		// Then
		assertEquals(3, props.size());
		verify(fileProvider, times(2)).asInputStreamFromClassPath(anyString(),
				any(ClassLoader.class));
	}

	@Test
	public void shouldReturnNoPropertiesBecauseNoFilesProvided() {

		// Given
		final Configuration configuration = new Configuration();
		when(fileConfigReader.getConfiguration(any(ClassLoader.class)))
				.thenReturn(configuration);

		// When
		Properties props = propsReader.getPropertiesFromClassPath(cl);

		// Then
		assertEquals(0, props.size());
	}
}

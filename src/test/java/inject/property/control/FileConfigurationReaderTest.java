package inject.property.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import inject.property.entity.Configuration;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationReaderTest {

	@Mock
	private FileProvider provider;

	@InjectMocks
	private FileConfigurationReader reader;

	@Test
	public void shouldReturnTwoFilesInTheList() {

		// Given
		final String configFile = "<propertiesfiles><file>wsendpoints.properties</file><file>emails.properties</file></propertiesfiles>";
		final ByteArrayInputStream is = new ByteArrayInputStream(
				configFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(is);

		// When
		Configuration config = reader.getConfiguration();

		// Then
		assertNotNull(config);
		assertNotNull(config.getFiles());
		assertEquals(2, config.getFiles().size());
		verify(provider).asInputStream(anyString());
	}

	@Test
	public void shouldReturnAnEmptyListBecauseUnknownTag() {

		// Given
		final String configFile = "<dummytag><file>wsendpoints.properties</file><file>emails.properties</file></dummytag>";
		final ByteArrayInputStream is = new ByteArrayInputStream(
				configFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(is);

		// When
		Configuration config = reader.getConfiguration();

		// Then
		assertNotNull(config);
		assertNotNull(config.getFiles());
		assertEquals(0, config.getFiles().size());
		verify(provider).asInputStream(anyString());
	}

	@Test
	public void shouldReturnAnEmptyListBecauseNotAnXMLConfigurationFile() {

		// Given
		final String configFile = "Some dummy text file";
		final ByteArrayInputStream is = new ByteArrayInputStream(
				configFile.getBytes());
		when(provider.asInputStream(anyString())).thenReturn(is);

		// When
		Configuration config = reader.getConfiguration();

		// Then
		assertNotNull(config);
		assertNotNull(config.getFiles());
		assertEquals(0, config.getFiles().size());
		verify(provider).asInputStream(anyString());
	}
}

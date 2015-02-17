package inject.property.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import inject.property.readers.AnnotationConfigurationReader;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationConfigurationServiceTest {

	@Mock
	AnnotationConfigurationReader configReader;

	@Mock
	InputStream inputStream;

	@Test
	public void shouldReturnConfigFile() {

		// Given
		when(configReader.read(any(InputStream.class))).thenReturn(
				new ConfigFile());
		ConfigurationService service = new AnnotationConfigurationService(
				configReader);

		// When
		ConfigFile configFile = service
				.getConfiguration("package1.package2.AnnotatedClass");

		// Then
		assertNotNull(configFile);
	}

	@Test
	public void shouldReturnNullBecauseClassDoesentExist() {

		// Given
		when(configReader.read(any(InputStream.class))).thenReturn(null);
		ConfigurationService service = new AnnotationConfigurationService(
				configReader);

		// When
		ConfigFile configFile = service
				.getConfiguration("package1.package2.ThisClassDoesentExist");

		// Then
		assertNull(configFile);
	}

}

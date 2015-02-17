package inject.property.providers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClassPathFileProviderTest {

	@Mock
	InputStream inputStream;

	@Mock
	ClassLoader cl;

	@InjectMocks
	ClassPathFileProvider provider;

	@Test
	public void shouldReturnAnInputStreamBecauseTheFileIsAccessibleFromClassPath() {

		// Given
		when(cl.getResourceAsStream(anyString())).thenReturn(inputStream);

		// When
		InputStream is = provider.asInputStream("accessible-file.properties");

		// Then
		assertNotNull(is);
		verify(cl, times(1)).getResourceAsStream(anyString());
	}

	@Test
	public void shouldReturnNullBecauseTheFileIsNotAccessibleFromClassPath() {

		// Given
		when(cl.getResourceAsStream(anyString())).thenReturn(null);

		// When
		InputStream is = provider
				.asInputStream("not-accessible-file.properties");

		// Then
		assertNull(is);
		verify(cl, times(1)).getResourceAsStream(anyString());
	}
}

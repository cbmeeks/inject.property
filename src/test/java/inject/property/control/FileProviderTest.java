package inject.property.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileProviderTest {

	@Mock
	private ClassLoader cl;

	@Mock
	private InputStream is;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnAnInputStream() {

		// Given
		FileProvider provider = new FileProvider();
		when(cl.getResourceAsStream(anyString())).thenReturn(is);

		// When
		final InputStream returnedStream = provider.asInputStreamFromClassPath(
				"input.property.xml", cl);

		// Then
		assertNotNull(returnedStream);
		assertEquals(is, returnedStream);
		verify(cl, times(1)).getResourceAsStream(anyString());
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNullPointerException() {

		// Given
		FileProvider provider = new FileProvider();

		// When
		provider.asInputStreamFromClassPath("inject.property.xml", null);

		// Then
		fail("Should throw Exception");
	}
}

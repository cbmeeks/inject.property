package inject.property.config;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ConfigFileReaderTest {

	private static final String TST_DIR = "test/";
	private static final String CONFIG_FILE_NAME = TST_DIR
			+ ConfigFileReader.CONFIG_FILE_NAME;
	private static final String DUMMY_CONFIG_FILE = "dummy.xml";
	private static final String INVALID_CONFIG_FILE = "test/inject.property-invalid.xml";
	private static final String INCOMPLETE_CONFIG_FILE = "test/inject.property-incomplete.xml";
	private static final String INCOMPLETE_CONFIG_FILE2 = "test/inject.property-incomplete2.xml";
	private static final String EMAILS_PROPERTIES_FILE = "emails.properties";
	private static final String ENPOINTS_PROPERTIES_FILE = "wsendpoints.properties";

	@Test
	public void shouldSuccessfulyReturnTheListOfPropertiesFilesListedInConfigFile()
			throws ConfigFileNotFoundException {

		// Given
		ClassLoader cl = getClass().getClassLoader();

		// When
		List<String> files = ConfigFileReader.getListOfPropertyFiles(cl,
				CONFIG_FILE_NAME);

		// Then
		Assert.assertEquals(files.size(), 2);
		Assert.assertTrue(files.contains(EMAILS_PROPERTIES_FILE));
		Assert.assertTrue(files.contains(ENPOINTS_PROPERTIES_FILE));
	}

	@Test(expected = RuntimeException.class)
	public void shouldFailBecauseConfigFileIsNotValid()
			throws ConfigFileNotFoundException {

		// Given
		ClassLoader cl = getClass().getClassLoader();

		// When
		ConfigFileReader.getListOfPropertyFiles(cl, INVALID_CONFIG_FILE);

		// Then
		Assert.fail("Must throw exception");
	}

	@Test(expected = RuntimeException.class)
	public void shouldFailBecauseConfigFileIsIncomplete()
			throws ConfigFileNotFoundException {

		// Given
		ClassLoader cl = getClass().getClassLoader();

		// When
		ConfigFileReader.getListOfPropertyFiles(cl, INCOMPLETE_CONFIG_FILE);

		// Then
		Assert.fail("Must throw exception");
	}

	@Test(expected = RuntimeException.class)
	public void shouldFailBecauseConfigFileIsIncomplete2()
			throws ConfigFileNotFoundException {

		// Given
		ClassLoader cl = getClass().getClassLoader();

		// When
		ConfigFileReader.getListOfPropertyFiles(cl, INCOMPLETE_CONFIG_FILE2);

		// Then
		Assert.fail("Must throw exception");
	}

	@Test(expected = ConfigFileNotFoundException.class)
	public void shouldFailBecauseConfigFileDoesNotExist()
			throws ConfigFileNotFoundException {

		// Given
		ClassLoader cl = getClass().getClassLoader();

		// When
		ConfigFileReader.getListOfPropertyFiles(cl, DUMMY_CONFIG_FILE);

		// Then
		Assert.fail("Must throw exception");
	}

}

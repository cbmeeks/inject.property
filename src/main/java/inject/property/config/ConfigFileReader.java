package inject.property.config;

import java.net.URL;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ConfigFileReader {

	public static final String CONFIG_FILE_NAME = "inject.property.xml";

	private static final String FILE_NOT_FOUND = "Configuration file %s not found, we will lokk for properties files in the classpath (first level only)";
	private static final String FILE_NOT_VALID = "The file %s is not a valid configuration file";
	private static final String FILE_INCOMPLETE = "The configuration file %s is incomplete";

	/**
	 * Unmarshall the configuration file and return the list of the property
	 * files. If the configuration file is not found an exception is thrown
	 *
	 * @param cl
	 *            The classloader used to locate the configuration file
	 * @param file
	 *            The name of the file only, no path
	 * @return The list of the property files
	 * @throws ConfigFileNotFoundException Is thrown if the configuration file is not found
	 */
	public static List<String> getListOfPropertyFiles(final ClassLoader cl,
			final String file) throws ConfigFileNotFoundException {

		final URL url = getFileURLFromClasspath(cl, file);

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(ConfigFile.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			ConfigFile configFile = (ConfigFile) unmarshaller.unmarshal(url);

			List<String> listOfFiles = configFile.getPropertyFiles();
			validateListOfFiles(listOfFiles, file);

			return listOfFiles;
		} catch (JAXBException e) {
			throw new RuntimeException(String.format(FILE_NOT_VALID, file));
		}
	}

	/**
	 * Returns the URL of the specified file.
	 * <p>This method looks for the file in
	 * the classpath therefore the name must not contain any path, only the file
	 * name.
	 * <p>If the file is found then a URL object containing the name and the
	 * path is returned, otherwise null is returned
	 *
	 * @param cl
	 *            The classloader used to locate the file
	 * @param file
	 *            The name of the file only, no path
	 * @return A URL object pointing to the file given as a parameter if the
	 *         file exists, otherwise null
	 */
	private static URL getFileURLFromClasspath(final ClassLoader cl, final String file) throws ConfigFileNotFoundException {

		final URL url = cl.getResource(file);
		if (url == null) {
			throw new ConfigFileNotFoundException(String.format(FILE_NOT_FOUND, file));
		}

		return url;
	}

	/**
	 * Validates the list of property files in the following order
	 * <ul>
	 * <li>A RuntimeException is thrown if the list is null</li>
	 * <li>Each null or empty element of the list is removed</li>
	 * <li>A RuntimeException is thrown if the list is empty</li>
	 * </ul>
	 *
	 * @param files
	 *            The list of property files
	 */
	private static void validateListOfFiles(List<String> files,
			String configFile) {

		if (files == null) {
			throw new RuntimeException(String.format(FILE_INCOMPLETE,
					configFile));
		}

		ListIterator<String> it = files.listIterator();
		while (it.hasNext()) {

			String fileName = it.next();

			if (fileName == null || fileName.trim().isEmpty()) {
				it.remove();
				continue;
			}
		}

		if (files.size() == 0) {
			throw new RuntimeException(String.format(FILE_INCOMPLETE,
					configFile));
		}
	}

}

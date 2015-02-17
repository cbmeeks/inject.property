package inject.property.readers;

import inject.property.annotations.PropertiesFiles;
import inject.property.config.ConfigFile;
import inject.property.providers.FileProvider;

import java.io.InputStream;
import java.util.Arrays;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationReader {

	private static final String ERROR_MSG = "Error reading the configuration from the file";

	private static final Logger LOG = LoggerFactory
			.getLogger(ConfigurationReader.class);

	@Inject
	private FileProvider provider;

	public ConfigurationReader() {
	}

	/**
	 * Read configuration from m a file
	 * 
	 * @param inputStream
	 * @return
	 */
	public ConfigFile read(String fileName) {

		ConfigFile configFile = new ConfigFile();

		InputStream inputStream = provider.asInputStream(fileName);

		try {
			JAXBContext ctx = JAXBContext.newInstance(ConfigFile.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			configFile = (ConfigFile) unmarshaller.unmarshal(inputStream);

		} catch (JAXBException e) {
			LOG.error(ERROR_MSG, e);
		}

		return configFile;
	}

	/**
	 * Read configuration from an annotation
	 * 
	 * @param annotatedClass
	 * @return
	 */
	public ConfigFile read(Class<?> annotatedClass) {

		ConfigFile configFile = null;

		PropertiesFiles annotation = annotatedClass
				.getAnnotation(PropertiesFiles.class);

		if (annotation != null) {
			configFile = new ConfigFile();
			String[] filesList = annotation.value();
			configFile.setPropertiesFile(Arrays.asList(filesList));
		}

		return configFile;
	}

}

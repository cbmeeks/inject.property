package inject.property.config;

import inject.property.providers.FileProvider;
import inject.property.readers.ConfigurationReader;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesService {

	private static final String ERROR_MSG = "Error loading properties files";

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesService.class);

	@Inject
	private FileProvider fileProvider;
	
	@Inject
	private ConfigurationReader reader;

	public PropertiesService() {
	}

	public Properties loadPropertiesFromFile(String fileName) {

		final ConfigFile configFile = reader.read(fileName);

		return loadProperties(configFile.getPropertiesFile());
	}

	public Properties loadPropertiesFromAnnotation(Class<?> annotatedClass) {

		final ConfigFile configFile = reader.read(annotatedClass);

		return loadProperties(configFile.getPropertiesFile());
	}

	private Properties loadProperties(List<String> propertiesFiles) {

		final Properties properties = new Properties();

		final Properties tmp = new Properties();
		try {
			for (String propertiesFile : propertiesFiles) {
				tmp.load(fileProvider.asInputStream(propertiesFile));
				copyProperties(tmp, properties);
			}
		} catch (IOException e) {
			LOG.error(ERROR_MSG, e);
		}

		return properties;
	}

	private void copyProperties(final Properties src, Properties dest) {

		for (String key : src.stringPropertyNames()) {
			dest.setProperty(key, src.getProperty(key));
		}
	}

}

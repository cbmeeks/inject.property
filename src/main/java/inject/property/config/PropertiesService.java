package inject.property.config;

import inject.property.providers.FileProvider;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesService {

	private static final String ERROR_MSG = "Error loading properties files";

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesService.class);

	private FileProvider fileProvider;

	public PropertiesService(final FileProvider fileProvider) {
		this.fileProvider = fileProvider;
	}

	public Properties getProperties(final List<String> propertiesFiles) {

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

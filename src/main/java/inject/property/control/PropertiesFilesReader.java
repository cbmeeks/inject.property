package inject.property.control;

import inject.property.entity.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFilesReader {

	private static final String FILE_NOT_FOUND = "File not found %s";
	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesFilesReader.class);

	private ConfigurationReader configReader;
	private FileProvider fileProvider;

	public PropertiesFilesReader(ConfigurationReader configReader,
			FileProvider fileProvider) {

		this.configReader = configReader;
		this.fileProvider = fileProvider;
	}

	public Properties getProperties() {

		final Configuration configuration = configReader.getConfiguration();
		final Properties props = new Properties();

		if (configuration == null) {
			return props;
		}

		Properties tmpProps;
		for (String fileName : configuration.getFiles()) {
			tmpProps = loadSingleFile(fileName);
			copyProperties(tmpProps, props);
		}

		return props;
	}

	private Properties loadSingleFile(String fileName) {

		final Properties tmpProps = new Properties();

		try (InputStream inStream = fileProvider.asInputStream(fileName)) {

			if (inStream == null) {
				LOG.error(String.format(FILE_NOT_FOUND, fileName));
				return tmpProps;
			}

			tmpProps.load(inStream);

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

		return tmpProps;
	}

	private void copyProperties(final Properties src, Properties dest) {
		for (String key : src.stringPropertyNames()) {
			dest.setProperty(key, src.getProperty(key));
		}
	}
}

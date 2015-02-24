package inject.property.control;

import inject.property.entity.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFilesReader {

	private static final String FILE_NOT_FOUND = "File not found %s";
	private static final Logger LOG = LoggerFactory
			.getLogger(PropertiesFilesReader.class);

	@Inject
	private FileProvider fileProvider;

	@Inject
	private FileConfigurationReader fileConfigReader;

	@Inject
	private AnnotationConfigurationReader annotationConfigReader;

	public PropertiesFilesReader() {
	}

	public Properties getPropertiesFromAnnotation(Class<?> clazz, ClassLoader cl) {

		final Configuration configuration = annotationConfigReader
				.getConfiguration(clazz);
		final Properties props = new Properties();

		if (configuration == null) {
			return props;
		}

		Properties tmpProps;
		for (String fileName : configuration.getFiles()) {
			tmpProps = loadSingleFile(fileName, cl);
			copyProperties(tmpProps, props);
		}

		return props;
	}

	public Properties getPropertiesFromClassPath(ClassLoader cl) {

		final Configuration configuration = fileConfigReader
				.getConfiguration(cl);
		final Properties props = new Properties();

		if (configuration == null) {
			return props;
		}

		Properties tmpProps;
		for (String fileName : configuration.getFiles()) {
			tmpProps = loadSingleFile(fileName, cl);
			copyProperties(tmpProps, props);
		}

		return props;
	}

	private Properties loadSingleFile(String fileName, ClassLoader cl) {

		final Properties tmpProps = new Properties();

		try (InputStream inStream = fileProvider.asInputStreamFromClassPath(
				fileName, cl)) {

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

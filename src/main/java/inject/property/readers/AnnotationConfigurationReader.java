package inject.property.readers;

import inject.property.annotations.PropertiesFiles;
import inject.property.config.ConfigFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationConfigurationReader implements ConfigurationReader {

	private static final String ERROR_MSG = "Error reading the configuration from the annotation @PropertiesFiles";

	private static final Logger LOG = LoggerFactory
			.getLogger(AnnotationConfigurationReader.class);

	public AnnotationConfigurationReader() {
	}

	@Override
	public ConfigFile read(InputStream inputStream) {

		ConfigFile configFile = null;

		try (InputStreamReader isr = new InputStreamReader(inputStream);
				BufferedReader br = new BufferedReader(isr)) {

			String annotatedClassName = br.readLine();
			Class<?> annotatedClass = Class.forName(annotatedClassName);
			PropertiesFiles propertiesFiles = annotatedClass
					.getAnnotation(PropertiesFiles.class);

			if (propertiesFiles != null) {
				configFile = new ConfigFile();
				String[] filesList = propertiesFiles.value();
				configFile.setPropertyFiles(Arrays.asList(filesList));
			}

		} catch (IOException | ClassNotFoundException e) {
			LOG.error(ERROR_MSG, e);
		}

		return configFile;
	}

}

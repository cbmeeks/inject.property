package inject.property.config;

import inject.property.readers.AnnotationConfigurationReader;

import java.io.ByteArrayInputStream;

public class AnnotationConfigurationService implements ConfigurationService {

	private AnnotationConfigurationReader configReader;

	public AnnotationConfigurationService(
			AnnotationConfigurationReader configReader) {
		this.configReader = configReader;
	}

	@Override
	public ConfigFile getConfiguration(String annotatedClassName) {

		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				annotatedClassName.getBytes());

		return configReader.read(inputStream);
	}

}

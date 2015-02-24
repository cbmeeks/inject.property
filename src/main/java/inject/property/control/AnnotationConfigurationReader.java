package inject.property.control;

import inject.property.annotation.PropertiesFiles;
import inject.property.entity.Configuration;

public class AnnotationConfigurationReader {

	public AnnotationConfigurationReader() {
	}

	public Configuration getConfiguration(Class<?> clazz) {

		Configuration configuration = new Configuration();

		PropertiesFiles annotation = clazz.getAnnotation(PropertiesFiles.class);

		if (annotation != null) {
			configuration = new Configuration();
			String[] files = annotation.value();
			configuration.addFiles(files);
		}

		return configuration;
	}

}

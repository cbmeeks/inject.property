package inject.property.control;

import inject.property.annotation.PropertiesFiles;
import inject.property.entity.Configuration;

public class AnnotationConfigurationReader implements ConfigurationReader {

	private Class<?> annotatedClass;

	public AnnotationConfigurationReader(Class<?> annotatedClass) {
		this.annotatedClass = annotatedClass;
	}

	@Override
	public Configuration getConfiguration() {

		Configuration configuration = null;

		PropertiesFiles annotation = annotatedClass
				.getAnnotation(PropertiesFiles.class);

		if (annotation != null) {
			configuration = new Configuration();
			String[] files = annotation.value();
			configuration.addFiles(files);
		}

		return configuration;
	}

}

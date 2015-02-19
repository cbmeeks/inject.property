package inject.property.boundary;

import inject.property.annotation.PropertiesFiles;
import inject.property.control.AnnotationConfigurationReader;
import inject.property.control.ClassPathFileProvider;
import inject.property.control.ConfigurationReader;
import inject.property.control.FileConfigurationReader;
import inject.property.control.FileProvider;
import inject.property.control.PropertiesFilesReader;

import java.util.Properties;

public class PropertiesInjectionEngine {

	private Class<?> clazz;
	private boolean isAnnotated = false;
	
	public PropertiesInjectionEngine(Class<?> clazz) {
		
		this.clazz = clazz;
		if (clazz.getAnnotation(PropertiesFiles.class) != null) {
			isAnnotated = true;
		}
	}

	public String getProperty(String key) {

		String value = null;

		if (isAnnotated) {
			value = property(key, clazz);
		}

		if (value == null) {
			value = property(key);
		}
		
		return value;
	}

	private String property(String key) {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		FileProvider fileProvider = new ClassPathFileProvider(classLoader);
		ConfigurationReader configReader = new FileConfigurationReader(
				fileProvider);
		PropertiesFilesReader reader = new PropertiesFilesReader(configReader,
				fileProvider);
		Properties props = reader.getProperties();

		return props.getProperty(key);
	}

	private String property(String key, Class<?> annotatedClass) {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		FileProvider fileProvider = new ClassPathFileProvider(classLoader);
		ConfigurationReader configReader = new AnnotationConfigurationReader(
				annotatedClass);
		PropertiesFilesReader reader = new PropertiesFilesReader(configReader,
				fileProvider);
		Properties props = reader.getProperties();

		return props.getProperty(key);
	}
}

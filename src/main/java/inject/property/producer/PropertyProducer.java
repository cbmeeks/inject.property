package inject.property.producer;

import inject.property.annotations.Property;
import inject.property.config.AnnotationConfigurationService;
import inject.property.config.ConfigFile;
import inject.property.config.ConfigurationService;
import inject.property.config.PropertiesService;
import inject.property.config.XMLConfigurationService;
import inject.property.providers.ClassPathFileProvider;
import inject.property.providers.FileProvider;
import inject.property.readers.AnnotationConfigurationReader;
import inject.property.readers.XMLConfigurationReader;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PropertyProducer {

	private static final Logger LOG = LoggerFactory
			.getLogger(PropertyProducer.class);

	private Properties props = new Properties();

	/**
	 * Load the properties from the configuration file and store them for
	 * further use
	 */
	@PostConstruct
	public void loadPropertiesFiles() {

		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		final FileProvider provider = new ClassPathFileProvider(classLoader);
		final XMLConfigurationReader reader = new XMLConfigurationReader();

		final ConfigurationService configService = new XMLConfigurationService(
				provider, reader);
		final PropertiesService propsService = new PropertiesService(provider);

		ConfigFile configFile = configService
				.getConfiguration("property.input.xml");
		if (configFile != null) {
			props = propsService.getProperties(configFile.getPropertyFiles());
		}

	}

	@Produces
	@Property
	public String getPropertyValue(InjectionPoint ip) {

		Property annotation = ip.getAnnotated().getAnnotation(Property.class);
		if (annotation == null || annotation.value().isEmpty()) {
			return null;
		}
		String className = ip.getMember().getDeclaringClass().getName();

		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		final FileProvider provider = new ClassPathFileProvider(classLoader);
		final AnnotationConfigurationReader reader = new AnnotationConfigurationReader();
		final ConfigurationService configService = new AnnotationConfigurationService(
				reader);
		final PropertiesService propsService = new PropertiesService(provider);

		final ConfigFile configFile = configService.getConfiguration(className);
		Properties props2 = new Properties();
		if (configFile != null) {
			props2 = propsService.getProperties(configFile.getPropertyFiles());
		}

		// Look for the property in the annotation's properties first
		String key = annotation.value();
		String value = props2.getProperty(key);

		// If not found fall back to configuration file properties
		if (value == null) {
			value = props.getProperty(key);
		}

		return value;
	}

}

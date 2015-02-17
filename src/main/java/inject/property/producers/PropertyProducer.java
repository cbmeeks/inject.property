package inject.property.producers;

import inject.property.annotations.Property;
import inject.property.config.PropertiesService;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

public class PropertyProducer {

	public static final String DEFAULT_CONFIG_FILE = "inject.property.xml";

	@Inject
	private PropertiesService propsService;

	private Properties props = new Properties();

	/**
	 * Load the properties from the configuration file and store them for
	 * further use
	 */
	@PostConstruct
	public void loadPropertiesFiles() {
		props = propsService.loadPropertiesFromFile(DEFAULT_CONFIG_FILE);
	}

	@Produces
	@Property
	public String getPropertyValue(InjectionPoint ip) {

		Properties props2 = propsService.loadPropertiesFromAnnotation(ip
				.getMember().getDeclaringClass());

		// Look for the property in the annotation's properties first
		String key = ip.getAnnotated().getAnnotation(Property.class).value();
		String value = props2.getProperty(key);

		// If not found fall back to configuration file properties
		if (value == null) {
			value = props.getProperty(key);
		}

		return value;
	}

}

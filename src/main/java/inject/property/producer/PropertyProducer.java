package inject.property.producer;

import inject.property.annotation.Property;
import inject.property.boundary.PropertiesInjectionEngine;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class PropertyProducer {

	public static final String DEFAULT_CONFIG_FILE = "inject.property.xml";

	@Produces
	@Property
	public String getPropertyValue(InjectionPoint ip) {

		Class<?> clazz = ip.getMember().getDeclaringClass();
		final PropertiesInjectionEngine engine = new PropertiesInjectionEngine(
				clazz);

		String key = ip.getAnnotated().getAnnotation(Property.class).value();
		String value = engine.getProperty(key);

		return value;
	}

}

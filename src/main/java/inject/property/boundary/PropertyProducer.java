package inject.property.boundary;

import inject.property.annotation.PropertiesFiles;
import inject.property.annotation.Property;
import inject.property.control.PropertiesFilesReader;

import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

public class PropertyProducer {

	public static final String DEFAULT_CONFIG_FILE = "inject.property.xml";

	@Inject
	private PropertiesFilesReader reader;
	
	@Produces
	@Property
	public String getPropertyValue(InjectionPoint ip) {

		final Class<?> clazz = ip.getMember().getDeclaringClass();
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final String key = ip.getAnnotated().getAnnotation(Property.class).value();
		final Properties properties;
		
		if (clazz.getAnnotation(PropertiesFiles.class) != null) {
			properties = reader.getPropertiesFromAnnotation(clazz, cl);
		} else {
			properties = reader.getPropertiesFromClassPath(cl);
		}
		
		return properties.getProperty(key);
	}

}

package inject.property.producer;

import inject.property.annotations.Property;
import inject.property.config.ConfigFileNotFoundException;
import inject.property.config.ConfigFileReader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PropertyProducer {

	private static final String FILE_NOT_FOUND = "File %s not found";
	private static final String CAN_NOT_READ = "Can not read file %s";
	private static final String MUST_ANNOTATE = "The field %s must be of type String, annotated with @Property and the annotation must have a value";
	private static final String LOADED_FILES = "%d properties files will be loaded";
	private static final String LOADED_PROPERTIES = "%d properties ready to be injected";
	private static final String INJECTION_FAILED = "Injection of attribute %s#%s failed, no value was found for the key %s in the properties files";
	private static final Logger LOG = LoggerFactory
			.getLogger(PropertyProducer.class);

	private Properties props = new Properties();

	/**
	 * Loads in one Properties object all the property files listed in the
	 * configuration file
	 */
	@PostConstruct
	public void loadPropertyFiles() {

		// Read the configuration file and return the list of properties files
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		List<String> files = null;
		try {
			files = ConfigFileReader.getListOfPropertyFiles(cl,
					ConfigFileReader.CONFIG_FILE_NAME);
		} catch (ConfigFileNotFoundException e) {
			LOG.debug(e.getMessage());
		}

		// If the configuration file is not found then search for properties
		// files in the classpath
		if (files == null) {

			files = new ArrayList<>();

			try {
				Enumeration<URL> res = cl.getResources("/");
				while (res.hasMoreElements()) {
					URL url = res.nextElement();

					if (!"file".equals(url.getProtocol())) {
						continue;
					}

					Path dirPath = Paths.get(url.toURI());
					if (Files.isDirectory(dirPath)) {

						/* Filter *.properties files */
						File[] filez = dirPath.toFile().listFiles(
								new FilenameFilter() {

									@Override
									public boolean accept(File dir, String name) {
										return name.endsWith(".properties") ? true
												: false;
									}
								});

						for (File file : filez) {
							files.add(file.getName());
							LOG.debug(file.getName());
						}
					}
				}
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		LOG.info(String.format(LOADED_FILES, files.size()));

		// Load all the files in the attribute props
		Properties tmpProps = new Properties();
		for (String propertyFile : files) {

			try (final InputStream is = cl.getResourceAsStream(propertyFile)) {

				if (is == null) {
					throw new RuntimeException(String.format(FILE_NOT_FOUND,
							propertyFile));
				}

				tmpProps.load(is);
				copyProperties(tmpProps, props);

				LOG.debug(String.format("Reading file %s...done", propertyFile));

			} catch (IOException e) {
				throw new RuntimeException(String.format(CAN_NOT_READ,
						propertyFile), e);
			}
		}

		if (tmpProps.size() > 0) {
			LOG.debug(String.format(LOADED_PROPERTIES, tmpProps.size()));
		}

	}

	@Produces
	@Property
	public String getPropertyValue(InjectionPoint ip) {

		Property annotation = ip.getAnnotated().getAnnotation(Property.class);
		if (annotation == null || annotation.value().isEmpty()) {

			throw new RuntimeException(String.format(MUST_ANNOTATE, ip
					.getMember().getName()));
		}

		String key = annotation.value();
		String value = props.getProperty(key);

		if (value == null) {
			LOG.debug(String.format(INJECTION_FAILED, ip.getMember()
					.getDeclaringClass().getName(), ip.getMember().getName(),
					key));
		}

		return value;
	}

	/**
	 * Copy the properties of source into destination
	 *
	 * @param src
	 * @param dest
	 */
	private void copyProperties(final Properties src, Properties dest) {

		for (String key : src.stringPropertyNames()) {
			dest.setProperty(key, src.getProperty(key));
		}
	}
}

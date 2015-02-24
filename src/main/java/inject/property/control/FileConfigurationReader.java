package inject.property.control;

import inject.property.entity.Configuration;

import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileConfigurationReader {

	public static final String DEFAULT_CONFIG_FILE = "inject.property.xml";
	private static final String ERROR_MSG = "Error reading configuration file %s";

	private static final Logger LOG = LoggerFactory
			.getLogger(FileConfigurationReader.class);

	@Inject
	private FileProvider fileProvider;

	public FileConfigurationReader() {
	}

	public Configuration getConfiguration(ClassLoader cl) {

		Configuration configuration = new Configuration();

		InputStream inputStream = fileProvider.asInputStreamFromClassPath(
				DEFAULT_CONFIG_FILE, cl);

		try {
			JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			configuration = (Configuration) unmarshaller.unmarshal(inputStream);

		} catch (JAXBException e) {
			LOG.error(String.format(ERROR_MSG, DEFAULT_CONFIG_FILE), e);
		}

		return configuration;
	}

}

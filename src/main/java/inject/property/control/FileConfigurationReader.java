package inject.property.control;

import inject.property.entity.Configuration;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileConfigurationReader implements ConfigurationReader {

	public static final String DEFAULT_CONFIG_FILE = "inject.property.xml";
	private static final String ERROR_MSG = "Error reading the configuration from the file";

	private static final Logger LOG = LoggerFactory
			.getLogger(FileConfigurationReader.class);

	private FileProvider fileProvider;

	public FileConfigurationReader(FileProvider fileProvider) {
		this.fileProvider = fileProvider;
	}

	@Override
	public Configuration getConfiguration() {

		Configuration configuration = new Configuration();

		InputStream inputStream = fileProvider
				.asInputStream(DEFAULT_CONFIG_FILE);

		try {
			JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			configuration = (Configuration) unmarshaller.unmarshal(inputStream);

		} catch (JAXBException e) {
			LOG.error(ERROR_MSG, e);
		}

		return configuration;
	}

}

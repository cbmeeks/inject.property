package inject.property.readers;

import inject.property.config.ConfigFile;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLConfigurationReader implements ConfigurationReader {

	private static final String ERROR_MSG = "Error reading the configuration from the file";

	private static final Logger LOG = LoggerFactory
			.getLogger(XMLConfigurationReader.class);

	public XMLConfigurationReader() {
	}

	@Override
	public ConfigFile read(InputStream inputStream) {

		ConfigFile configFile = null;

		try {
			JAXBContext ctx = JAXBContext.newInstance(ConfigFile.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			configFile = (ConfigFile) unmarshaller.unmarshal(inputStream);

		} catch (JAXBException e) {
			LOG.error(ERROR_MSG, e);
		}

		return configFile;
	}

}

package inject.property.config;

import inject.property.providers.FileProvider;
import inject.property.readers.XMLConfigurationReader;

import java.io.InputStream;

public class XMLConfigurationService implements ConfigurationService {

	private FileProvider provider;
	private XMLConfigurationReader configReader;

	public XMLConfigurationService(FileProvider provider,
			XMLConfigurationReader configReader) {

		this.provider = provider;
		this.configReader = configReader;
	}

	@Override
	public ConfigFile getConfiguration(String fileName) {

		ConfigFile configFile = null;

		InputStream inputStream = provider.asInputStream(fileName);
		if (inputStream != null) {
			configFile = configReader.read(inputStream);
		}
		return configFile;
	}
}

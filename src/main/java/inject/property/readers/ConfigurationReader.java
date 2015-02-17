package inject.property.readers;

import inject.property.config.ConfigFile;

import java.io.InputStream;

public interface ConfigurationReader {

	ConfigFile read(InputStream inputStream);
}

package inject.property.control;

import java.io.InputStream;

public class FileProvider {

	public FileProvider() {
	}

	public InputStream asInputStreamFromClassPath(String file,
			ClassLoader classLoader) {
		return classLoader.getResourceAsStream(file);
	}

}

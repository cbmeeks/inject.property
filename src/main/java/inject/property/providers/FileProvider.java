package inject.property.providers;

import java.io.InputStream;

public interface FileProvider {
	InputStream asInputStream(String file);
}

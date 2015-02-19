package inject.property.control;

import java.io.InputStream;

public interface FileProvider {
	InputStream asInputStream(String file);
}

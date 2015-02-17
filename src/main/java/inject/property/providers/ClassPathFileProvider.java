package inject.property.providers;

import java.io.InputStream;

import javax.inject.Inject;

public class ClassPathFileProvider implements FileProvider {

	@Inject
	private ClassLoader classLoader;

	public ClassPathFileProvider() {
	}

	@Override
	public InputStream asInputStream(String file) {
		return classLoader.getResourceAsStream(file);
	}

}

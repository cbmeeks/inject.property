package inject.property.control;

import java.io.InputStream;

public class ClassPathFileProvider implements FileProvider {

	private ClassLoader classLoader;

	public ClassPathFileProvider(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public InputStream asInputStream(String file) {
		return classLoader.getResourceAsStream(file);
	}

}

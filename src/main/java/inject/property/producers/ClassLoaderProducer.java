package inject.property.producers;

import javax.enterprise.inject.Produces;

public class ClassLoaderProducer {

	@Produces
	public ClassLoader classLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}

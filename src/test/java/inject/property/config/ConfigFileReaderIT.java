package inject.property.config;

import inject.property.annotations.Property;
import inject.property.producers.PropertyProducer;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class ConfigFileReaderIT {

	private static final String LOGGING_LEVEL_PROPERTY = "org.slf4j.simpleLogger.log.inject.property";
	private static final String LOGGING_LEVEL_DEBUG = "debug";
	private static final String BILLING_WS_ENDPOINT = "http://localhost:8080/billing";

	private static final Logger LOG;

	static {
		// Set the logging level to DEBUG for package inject.property
		System.setProperty(LOGGING_LEVEL_PROPERTY, LOGGING_LEVEL_DEBUG);
		LOG = LoggerFactory.getLogger(ConfigFileReaderIT.class);
	}

	@Deployment
	public static JavaArchive createDeployment() {

		JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
				.addClasses(PropertyProducer.class)
				.addAsResource("it/inject.property.xml", "/inject.property.xml")
				.addAsResource("it/wsendpoints.properties", "/wsendpoints.properties")
				.addAsResource("it/emails.properties", "/emails.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		LOG.debug(jar.toString(true));
		return jar;
	}

	@Inject
	@Property("billing.endpoint")
	private String billingEndPoint;

	@Inject
	@Property("dummyKey")
	private String dummyProperty;

	@Test
	public void propertyShouldBeInjectedProperly() {

		// Given
		// When
		// Then
		Assert.assertNotNull(billingEndPoint);
		Assert.assertEquals(BILLING_WS_ENDPOINT, billingEndPoint);
	}

	@Test
	public void propertyShouldBeNull() {

		// Given
		// When
		// Then
		Assert.assertNull(dummyProperty);
	}
}

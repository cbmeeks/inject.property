package inject.property.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import inject.property.annotation.PropertiesFiles;
import inject.property.annotation.Property;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@PropertiesFiles({ "emails.properties", "wsendpoints.properties" })
public class AnnotatedClassWithMultipleFilesIT {

	private static final Logger LOG = LoggerFactory
			.getLogger(AnnotatedClassInjectionIT.class);

	@Inject
	@Property("admin")
	private String adminEmail;

	@Inject
	@Property("billing.endpoint")
	private String billingEP;

	@Inject
	@Property("jdoe")
	private String jdoeEmail;

	@Inject
	@Property
	private String dummyEmail;

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
				.addPackage("inject.property.boundary")
				.addPackage("inject.property.control")
				.addPackage("inject.property.entity")
				.addPackage("inject.property.annotation")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		LOG.info(archive.toString(true));
		return archive;
	}

	@Test
	public void shouldInjectAdminEmailAndBillingEndPointBecauseInPropertiesFiles() {

		// Given
		// When
		// Then
		assertNotNull(adminEmail);
		assertEquals("admin@company.com", adminEmail);
		assertNotNull(billingEP);
		assertEquals("http://localhost:8080/billing", billingEP);
	}

	@Test
	public void shouldNotInjectJohnDoeEmailBecauseNotInPropertiesFiles() {

		// Given
		// When
		// Then
		assertNull(jdoeEmail);
	}

	@Test
	public void shouldNotInjectDummyEmailBecauseNoPropertySpecifiedInTheAnnotation() {

		// Given
		// When
		// Then
		assertNull(jdoeEmail);
	}
}

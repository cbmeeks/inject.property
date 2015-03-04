package inject.property.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import inject.property.annotation.Property;

import javax.inject.Inject;

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
public class ConfigurationFileFieldInjectionIT {

	private static final Logger LOG = LoggerFactory
			.getLogger(AnnotatedClassInjectionIT.class);

	private String adminEmail;

	@Inject
	public void setAdminEmail(@Property("admin") String adminEmail) {
		this.adminEmail = adminEmail;
	}

	private String billingEP;

	@Inject
	public void setBillingEP(@Property("billing.endpoint") String billingEP) {
		this.billingEP = billingEP;
	}

	private String jdoeEmail;

	@Inject
	public void setJdoeEmail(@Property("jdoe") String jdoeEmail) {
		this.jdoeEmail = jdoeEmail;
	}

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

}

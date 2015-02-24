package inject.property.control;

import inject.property.annotation.PropertiesFiles;
import inject.property.entity.Configuration;

import org.junit.Assert;
import org.junit.Test;

public class AnnotationConfigurationReaderTest {

	@Test
	public void shouldReturnTwoFilesInTheList() {

		// Given
		@PropertiesFiles({ "emails.properties", "endpoints.properties" })
		class AnnotatedClass {
		}
		AnnotationConfigurationReader reader = new AnnotationConfigurationReader();

		// When
		Configuration config = reader.getConfiguration(AnnotatedClass.class);

		// Then
		Assert.assertNotNull(config);
		Assert.assertNotNull(config.getFiles());
		Assert.assertEquals(2, config.getFiles().size());
	}

	@Test
	public void shouldReturnEmptyListBecauseNoFilesInAnnotation() {

		// Given
		@PropertiesFiles({})
		class AnnotatedClass {
		}
		AnnotationConfigurationReader reader = new AnnotationConfigurationReader();

		// When
		Configuration config = reader.getConfiguration(AnnotatedClass.class);

		// Then
		Assert.assertNotNull(config);
		Assert.assertNotNull(config.getFiles());
		Assert.assertEquals(0, config.getFiles().size());
	}

	@Test
	public void shouldReturnEmptyListBecauseClassNotAnnotated() {

		// Given
		class NonAnnotatedClass {
		}
		AnnotationConfigurationReader reader = new AnnotationConfigurationReader();

		// When
		Configuration config = reader.getConfiguration(NonAnnotatedClass.class);

		// Then
		Assert.assertNotNull(config);
		Assert.assertNotNull(config.getFiles());
		Assert.assertEquals(0, config.getFiles().size());
	}
}

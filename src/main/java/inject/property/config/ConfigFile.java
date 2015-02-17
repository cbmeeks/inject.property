package inject.property.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "propertiesfiles")
public class ConfigFile {

	@XmlElement(name = "propertiesfile", required = true, nillable = false)
	private List<String> propertiesFile;

	public List<String> getPropertyFiles() {
		return propertiesFile;
	}

	public void setPropertyFiles(List<String> propertyFiles) {
		this.propertiesFile = propertyFiles;
	}

}

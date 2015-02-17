package inject.property.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "propertiesfiles")
public class ConfigFile {

	@XmlElement(name = "propertyfile", required = true, nillable = false)
	private List<String> propertiesFile;

	public List<String> getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(List<String> propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

}

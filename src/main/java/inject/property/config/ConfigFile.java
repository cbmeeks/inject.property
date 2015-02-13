package inject.property.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "propertyfiles")
public class ConfigFile {

	@XmlElement(name = "propertyfile", required = true, nillable = false)
	private List<String> propertyFiles;

	public List<String> getPropertyFiles() {
		return propertyFiles;
	}

}

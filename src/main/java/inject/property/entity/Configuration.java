package inject.property.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "propertiesfiles")
public class Configuration {

	@XmlElement(name = "file", required = true, nillable = false)
	private List<String> files = new ArrayList<>();

	public void addFile(String fileName) {
		files.add(fileName);
	}

	public void addFiles(String[] filesList) {
		files.addAll(Arrays.asList(filesList));
	}

	public List<String> getFiles() {
		return files;
	}

}

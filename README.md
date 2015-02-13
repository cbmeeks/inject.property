# inject.property

inject.property is a java ee 6 annotation that can be used to simply inject properties (from .properties files) in String attributes

Ex.
`@Property("webservice.endpoint")`
`private String wsEndpint;`

IMPORTANT
CDI injection must be enabled (beans.xml file in META-INF or WEB-INF)

## Building
`mvn clean install`

## Usage
1. Add the generated jar to your project dependencies
2. Create a file named inject.property.xml in src/main/resource or any othe directory available to the classpath
3. The file will list all the propety files used by the application and must have the following structure
  <propertyfiles>
	  <propertyfile>file1.properties</propertyfile>
	  <propertyfile>file2.properties</propertyfile>
  </propertyfiles>
  file1.properties and file2.properties are located in src/main/resource or any other directory available to the classpath, note that only names are specified not the paths
4. In the java code create String attributes and annotate them with @Property("key") where key is the key of the property in one of the properties files. The value of the propery will be injected in attribute.

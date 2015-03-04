# Properties injection for Java EE 6 applications

A set of 2 annotations that allow to simply inject properties from properties files.

## Features:
* Inject a property by simply annotating a String attribute
* Annotate a class to specify the properties files to load
* Alternatively, properties files can be listed in a configuration file
* Supports field injection and method injection
* Uses CDI to perform the injection

## Technologies
* Java EE 6 with CDI 1.0 enabled

## To build the jar
mvn clean compile package

## To run integration tests
mvn verify

## To build the jar and run integration tests
mvn clean install

## Usage
* Build the jar and drop it with the dependencies
* Annotate the class where the injection will be performed with the @PropertiesFiles annotation, this annotation will list all the properties files that will be loaded

@PropertiesFiles({"file1.properties", "file2.properties"})
public MyClass {
    ....
}

* alternatively you can list the files in a configuration file named inject.property.xml, this file must be available in the classpath

<propertiesfiles>
	<file>file1.properties</file>
	<file>file2.properties</file>
	....
	<file>fileN.properties</file>
</propertiesfiles>

* Annotate a String attribute (the type string is mandatory as of current version) that will receive the value of the property with the annotations @Inject and @Property("${property.key}") where ${property.key} is the key of the property in the properties file

@PropertiesFiles({"emails.properties"})
public MyClass {

    @Inject
    @Property("admin.email")
    private String adminEmail;
    ....
}

* Method injection can also be used

@PropertiesFiles({"emails.properties"})
public MyClass {

    private String adminEmail;

    @Inject
    public void setAdminEmail(@Property("admin.email") String adminEmail) {
        this.adminEmail = adminEmail;
    }
    ....
}

### How it works
* The files listed in the annotation @PropertiesFiles are loaded and used for the injection, the files listed in the configuration file inject.property.xml are ignored
* If @PropertiesFiles is not present then inject.property.xml is used



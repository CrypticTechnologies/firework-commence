package com.cryptic.propertyreader;

/*
 * A template for a POJO manifesting as a container for the Database properties. This template can be 
 * adopted by other POJOs that represent property files. Need to declare the constants for the properties.
 * The property file can be located either in the file system path or classpath. In the former case, you need to 
 * create an system environment variable that holds the path to the property file.
 *   
 */

public class UserPropertyFileReader extends PropertyFileReader {

	 
	
	private final String username = "hotel.username";

	private final String password = "hotel.password";


	/*
	 *  
	 * 
	 */
	public UserPropertyFileReader(int containerType, String source)
			throws PropertyFileLocationException {
		super(containerType, source);
	}
	

	public String getUsername() {
		return super.getValue(username);
	}

	public String getPassword() {
		return super.getValue(password);
	}
	
}

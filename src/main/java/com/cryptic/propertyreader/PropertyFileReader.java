package com.cryptic.propertyreader;

import java.net.URL;

import org.apache.commons.configuration.PropertiesConfiguration;

/*
 * The Property File Reader reads the properties stored in a single property file.
 * The property file can be either located in the classpath or 
 * file system path as mentioned in a custom environment variable.
 *  
 */
public abstract class PropertyFileReader extends PropertiesConfiguration {

	public static final int PROPERTY_FILE_PATH_CLASSPATH = 0;
	public static final int PROPERTY_FILE_PATH_ENV = 1;

	private String propertySource;
	private int type;

	/*
	 * Initializes the Property file Reader. It reads all the properties from
	 * the property file.
	 * 
	 * @param containerType 0 : property file located in the classpath 1 :
	 * property file located in the file system path
	 * 
	 * @param source property file name , if property file is located in the
	 * classpath system environment variable name, if property file is located
	 * in the file system path.
	 * 
	 * @throws PropertyFileLocationException thrown due to improper property
	 * file location.
	 */
	public PropertyFileReader(int containerType, String source)
			throws PropertyFileLocationException {

		type = containerType;
		propertySource = source;

		try {
			if (type == PROPERTY_FILE_PATH_CLASSPATH) {
				URL settingsFileUrl = Thread.currentThread()
						.getContextClassLoader().getResource(propertySource);
				setURL(settingsFileUrl);
				load();
			} else if (containerType == PROPERTY_FILE_PATH_ENV) {
				String location = System.getenv(propertySource);
				setPath(location);
			}
			load();
		} catch (Exception e) {
			throw new PropertyFileLocationException();
		}

	}

	public String getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(String propertySource) {
		this.propertySource = propertySource;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected String getValue(String key) {
		String value = super.getString(key);
		return value;
	}

}

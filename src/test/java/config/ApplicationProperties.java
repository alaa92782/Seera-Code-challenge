package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

	private Properties properties = new Properties();

	public ApplicationProperties() {
		loadApplicationProperties();
	}
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}

	private void loadApplicationProperties() {
		try {
			InputStream stream = this.getClass().getResourceAsStream("/application.properties");
			properties.load(stream);
		} catch (IOException e) {
			logger.error("Error on trying to read properties");
		}
	}
}
package multiportal.firewall.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ApplicationConfig {
	
public ApplicationConfig() {
	// TODO Auto-generated constructor stub
}
	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./application.properties");
		props.load(file);
		return props;

	}
	
}

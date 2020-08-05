package utils;

import java.io.InputStream;
import java.util.Properties;

public class Property {
    Properties properties;

    public Property() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream file = classLoader.getResourceAsStream("environment.properties");
            properties = new Properties();
            properties.load(file);
            file.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("There is no environment properties\n" + e);
        }
    }

    public String getProperty(String name) {
        String systemProperty = System.getProperty(name);
        return (systemProperty != null) ? systemProperty : properties.getProperty(name);
    }

    public String getGorestBaseUrl() {
        return getProperty("gorestBaseUrl");
    }

    public String getAccessToken() {
        return getProperty("accessToken");
    }

}

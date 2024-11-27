package nvyas.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Config file '" + CONFIG_FILE +"' is not found in the directory 'src/main/resources'");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading configuration file", e);
        }
    }

    private static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Properties getDbProperties() {
        Properties dbProperties = new Properties();
        dbProperties.put("url", properties.getProperty("db.url"));
        dbProperties.put("user", properties.getProperty("db.user"));
        dbProperties.put("password", properties.getProperty("db.password"));
        return dbProperties;
    }
}
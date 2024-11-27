package nvyas.db.util;


import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getConnection() {
        Properties dbProperties = ConfigLoader.getDbProperties();
        String url = dbProperties.getProperty("url");
        String user = dbProperties.getProperty("user");
        String password = dbProperties.getProperty("password");
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to " + url);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found");
        }
    }


    public static SessionFactory getSessionFactory() {
        return SessionFactoryLoader.getSessionFactory();
    }
}

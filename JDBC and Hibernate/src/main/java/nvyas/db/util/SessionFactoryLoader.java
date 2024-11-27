package nvyas.db.util;

import nvyas.db.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactoryLoader {
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;
    private static final String PROPERTIES_FILE = "hibernate.properties";

    static {
        try {
            if (sessionFactory== null) {

                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
                standardServiceRegistryBuilder.loadProperties(PROPERTIES_FILE);

                standardServiceRegistry = standardServiceRegistryBuilder.build();

                MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
                metadataSources.addAnnotatedClass(User.class);

                Metadata metadata = metadataSources.getMetadataBuilder().build();

                sessionFactory=metadata.getSessionFactoryBuilder().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (standardServiceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}

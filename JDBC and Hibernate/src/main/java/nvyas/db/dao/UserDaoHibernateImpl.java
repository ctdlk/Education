package nvyas.db.dao;

import nvyas.db.entity.User;
import nvyas.db.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY, name VARCHAR(25), last_name VARCHAR(25), age INT2)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String CLEAR_TABLE_SQL = "TRUNCATE TABLE users";
    private static final String GET_ALL_USERS_HQL = "FROM User";
    private static final int COMMAND_INDEX = 0;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        executeDDL(CREATE_TABLE_SQL);
    }

    @Override
    public void dropUsersTable() {
        executeDDL(DROP_TABLE_SQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            System.err.println("Failed to save user");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (HibernateException e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            System.err.println("Failed to delete user with id = " + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
       Transaction transaction = null;
       List<User> users = new ArrayList<>();
       try(Session session = Util.getSessionFactory().openSession()){
           transaction = session.beginTransaction();
           users = session.createQuery(GET_ALL_USERS_HQL, User.class).list();
           transaction.commit();
           return users;
       } catch (HibernateException e){
           if (transaction != null && transaction.isActive()){
               transaction.rollback();
           }
           System.err.println("Failed to get users");
       }
       return users;
    }

    @Override
    public void cleanUsersTable() {
        executeDDL(CLEAR_TABLE_SQL);
    }

    private void executeDDL(String sql) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }

            String command = sql.split(" ")[COMMAND_INDEX].toUpperCase();
            String message = switch (command) {
                case "CREATE" -> "Failed to create table";
                case "DROP" -> "Failed to drop table";
                case "TRUNCATE" -> "Failed to truncate table";
                default ->
                        throw new RuntimeException("Invalid DDL command: " + command + ". Supported commands: CREATE, DROP, TRUNCATE.");
            };
            System.err.println(message);
        }
    }
}

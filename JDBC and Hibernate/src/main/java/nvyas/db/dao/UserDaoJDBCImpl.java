package nvyas.db.dao;

import nvyas.db.entity.User;
import nvyas.db.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY, name VARCHAR(25), last_name VARCHAR(25), age INT2)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String CLEAR_TABLE_SQL = "TRUNCATE TABLE users";
    private static final String INSERT_USER_SQL = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_SQL = "DELETE FROM users WHERE id = ?";
    private static final String GET_ALL_USERS_SQL = "SELECT * FROM users";

    private static List<User> users = new ArrayList<>();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Failed to create table");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Failed to drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            int insertedRowsAmount = preparedStatement.executeUpdate();
            switch (insertedRowsAmount) {
                case 0 -> throw new SQLException("Failed to add user to DB");
                case 1 -> {
                    ResultSet pk = preparedStatement.getGeneratedKeys(); // pk = primary key
                    if (pk.next()) {
                        User user = new User(name, lastName, age);
                        user.setId(pk.getLong(1));
                        users.add(user);
                    } else throw new SQLException("Failed to create user in app");
                    pk.close();
                }
                default ->
                        throw new SQLException("Unexpected behavior: more than one row inserted (" + insertedRowsAmount + ")");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_SQL)) {
            preparedStatement.setLong(1, id);
            int deletedRowsAmount = preparedStatement.executeUpdate();
            switch (deletedRowsAmount) {
                case 0 -> throw new SQLException("Failed to delete user from DB");
                case 1 -> {
                    Iterator<User> iterator = users.iterator();
                    boolean deleted = false;
                    while (iterator.hasNext() && !deleted) {
                        User user = iterator.next();
                        if (user.getId() == id) {
                            iterator.remove();
                            deleted = true;
                        }
                    }
                    if (!deleted) throw new SQLException("Failed to delete user from app");
                }
                default ->
                        throw new SQLException("Unexpected behavior: more than one row deleted (" + deletedRowsAmount + ")");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        users.clear();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL)) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get users");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAR_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Failed to truncate table");
        }
    }
}

package nvyas.db;

import nvyas.db.entity.User;
import nvyas.db.service.UserService;
import nvyas.db.service.UserServiceHibernateImpl;
import nvyas.db.service.UserServiceJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceHibernateImpl();
        User user1 = new User("Екатерина", "Погорельцева", (byte) 21);
        User user2 = new User("Виктор", "Тоноев", (byte) 31);
        User user3 = new User("Валентина", "Назарова", (byte) 70);
        User user4 = new User("Евгений", "Зайцев", (byte) 46);
        userService.createUsersTable();
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        userService.getAllUsers();
        userService.removeUserById(2);
        userService.removeUserById(4);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

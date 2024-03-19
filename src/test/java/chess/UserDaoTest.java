package chess;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    void addUser() {
        UserDao userDao = new UserDao(connection);
        User user = new User("testId", "testUser");

        int effectedRowNum = userDao.addUser(user);

        assertThat(effectedRowNum).isNotZero();
    }

    @Test
    void findByUserId() {
        UserDao userDao = new UserDao(connection);
        User user = new User("testId", "testUser");
        userDao.addUser(user);

        User foundUser = userDao.findByUserId(user.userId());

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void findAll() {
        UserDao userDao = new UserDao(connection);
        User user1 = new User("testId1", "testUser1");
        User user2 = new User("testId2", "testUser2");
        userDao.addUser(user1);
        userDao.addUser(user2);

        List<User> users = userDao.findAll();

        assertThat(users).contains(user1, user2);
    }

    @Test
    void updateUsername() {
        UserDao userDao = new UserDao(connection);
        String targetUserId = "testId1";
        String oldName = "testUser1";
        String newName = "testUser2";
        User user1 = new User(targetUserId, oldName);
        userDao.addUser(user1);

        String effectedId = userDao.updateUsername(targetUserId, newName);
        User foundUser = userDao.findByUserId(effectedId);

        assertThat(foundUser.name()).isEqualTo(newName);
    }

    @Test
    void delete() {
        UserDao userDao = new UserDao(connection);
        User user1 = new User("testUserId", "testUserName");
        userDao.addUser(user1);

        userDao.deleteById(user1.userId());

        assertThat(userDao.findByUserId(user1.userId())).isNull();
    }
}

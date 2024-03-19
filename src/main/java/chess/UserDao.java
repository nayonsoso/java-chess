package chess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public int addUser(final User user) {
        final var query = "INSERT INTO user VALUES(?, ?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.userId());
            preparedStatement.setString(2, user.name());
            return preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUserId(final String userId) {
        final var query = "SELECT * FROM user WHERE user_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("name")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<User> findAll() {
        final String query = "SELECT * FROM user";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();

            List<User> result = new LinkedList<>();
            while (resultSet.next()) {
                result.add(new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("name")
                ));
            }
            return result;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateUsername(String targetId, String newName) {
        final String query = "UPDATE user SET name = ? WHERE user_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, targetId);

            preparedStatement.executeUpdate();
            return targetId;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(String userId) {
        final String query = "DELETE FROM user WHERE user_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package chess.dao;

import chess.dao.dto.ResponseChessGameDto;

import java.sql.Connection;
import java.sql.SQLException;

public class ChessGameDao {

    private final Connection connection;

    public ChessGameDao(Connection connection) {
        this.connection = connection;
    }

    public void saveNewChessGame() {
        final var query = "INSERT INTO chess_game(status) VALUES(?)";
        String status = EnumMapper.mapToString(StatusType.CONTINUE);

        executeUpdateQuery(query, status);
    }

    public ResponseChessGameDto findRecentChessGame() {
        final var query = "SELECT * FROM chess_game ORDER BY id DESC LIMIT 1";

        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String status = resultSet.getString("status");
                return ResponseChessGameDto.of(id, status);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public int findRecentChessGameId() {
        final var query = "SELECT id FROM chess_game ORDER BY id DESC LIMIT 1";

        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                return Integer.parseInt(id);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    private int executeUpdateQuery(String query, String... arguments) {
        try (final var preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < arguments.length; i++) {
                preparedStatement.setString(i + 1, arguments[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

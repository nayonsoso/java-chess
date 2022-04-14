package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceFactory;
import chess.domain.position.Position;
import chess.exception.DataAccessException;

public class BoardDao {

    private final String URL = "jdbc:mysql://localhost:3306/chess";
    private final String USER = "user";
    private final String PASSWORD = "password";
    private final DBConnection dbConnection = new DBConnection(URL, USER, PASSWORD);

    public void deleteAll() {
        final String query = "delete from board";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("[DATABASE_ERROR] 체스 말 삭제 실패");
        }
    }

    public void insertBoardSquare(final String position, final String piece, final String color) {
        final String query = "INSERT INTO board(position, piece, color) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position);
            preparedStatement.setString(2, piece);
            preparedStatement.setString(3, color);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("[DATABASE_ERROR] 체스 말 삽입 실패");
        }
    }

    public void deleteBoardSquare(String position) {
        final String query = "delete from board where position = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("[DATABASE_ERROR] 체스 말 삭제 실패");
        }
    }

    public void updateBoardSquare(final String source, final String target, final String piece, final String color) {
        final String query = "update board set piece = ?, color = ?, position = ? where position = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, piece);
            preparedStatement.setString(2, color);
            preparedStatement.setString(3, target);
            preparedStatement.setString(4, source);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("[DATABASE_ERROR] 체스 말 업데이트 실패");
        }
    }

    public Map<Position, Piece> getBoardSquares() {
        final String query = "SELECT * FROM board";
        final Map<Position, Piece> squares = new HashMap<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            addSquares(squares, resultSet);
            return squares;
        } catch (SQLException e) {
            throw new DataAccessException("[DATABASE_ERROR] 보드 정보 가져오기 실패");
        }
    }

    private void addSquares(final Map<Position, Piece> squares, final ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Position position = Position.valueOf(resultSet.getString("position"));
            Piece piece = PieceFactory.create(
                resultSet.getString("piece"),
                resultSet.getString("color")
            );
            squares.put(position, piece);
        }
    }
}
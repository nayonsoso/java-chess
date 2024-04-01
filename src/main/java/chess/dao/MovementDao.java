package chess.dao;

import chess.dao.dto.RequestMovementDto;
import chess.dao.dto.ResponseMovementDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MovementDao {

    private final Connection connection;

    public MovementDao(Connection connection) {
        this.connection = connection;
    }

    public List<ResponseMovementDto> findByChessGameIdOrderedByAsc(int chessGameId) {
        final var query = "SELECT * FROM movement WHERE chess_game_id = ? ORDER BY id";
        LinkedList<ResponseMovementDto> requestMovementDtos = new LinkedList<>();

        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(chessGameId));
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String sourceFile = resultSet.getString("source_file");
                String sourceRank = resultSet.getString("source_rank");
                String targetFile = resultSet.getString("target_file");
                String targetRank = resultSet.getString("target_rank");
                String id = resultSet.getString("chess_game_id");
                ResponseMovementDto dto = ResponseMovementDto.of(sourceFile, sourceRank, targetFile, targetRank, id);
                requestMovementDtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return requestMovementDtos;
    }

    public void addMovement(final RequestMovementDto requestMovementDto) {
        final var query = "INSERT INTO movement (source_file, source_rank, target_file, target_rank, chess_game_id) VALUES(?, ?, ?, ?, ?)";
        String sourceFile = requestMovementDto.sourceFile();
        String sourceRank = requestMovementDto.sourceRank();
        String targetFile = requestMovementDto.targetFile();
        String targetRank = requestMovementDto.targetRank();
        String chessGameId = requestMovementDto.chessGameId();

        executeUpdateQuery(query, sourceFile, sourceRank, targetFile, targetRank, chessGameId);
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

package chess.dao;

import chess.dao.dto.RequestMovementDto;
import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatCode;

class MovementDaoTest {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        ChessDBConnectionManager connectionManager = new ChessDBConnectionManager();
        connection = connectionManager.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @DisplayName("게임 아이디에 해당하는 이동의 목록을 가져온다.")
    @Test
    void findByChessGameIdOrderedByAsc() {
        ChessGameDao chessGameDao = new ChessGameDao(connection);
        MovementDao movementDao = new MovementDao(connection);
        int recentChessGameId = chessGameDao.findRecentChessGameId();

        if (recentChessGameId == 0) {
            assertThatCode(() -> movementDao.findByChessGameIdOrderedByAsc(recentChessGameId))
                    .isInstanceOf(RuntimeException.class);
        }
        if (recentChessGameId != 0) {
            assertThatCode(() -> movementDao.findByChessGameIdOrderedByAsc(recentChessGameId))
                    .doesNotThrowAnyException();
        }
    }

    @DisplayName("이동을 기록한다.")
    @Test
    void addMovement() {
        MovementDao movementDao = new MovementDao(connection);
        Position source = Position.of(File.A, Rank.TWO);
        Position target = Position.of(File.A, Rank.FOUR);
        RequestMovementDto requestMovementDto = RequestMovementDto.of(source, target, 1);

        assertThatCode(() -> movementDao.addMovement(requestMovementDto))
                .doesNotThrowAnyException();
    }
}

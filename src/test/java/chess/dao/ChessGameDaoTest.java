package chess.dao;

import chess.dao.dto.ResponseChessGameDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ChessGameDaoTest {

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

    @DisplayName("새로운 게임을 저장한다.")
    @Test
    void saveNewChessGame() {
        ChessGameDao chessGameDao = new ChessGameDao(connection);

        chessGameDao.saveNewChessGame();

        ResponseChessGameDto after = chessGameDao.findRecentChessGame();
        assertThat(after.statusType()).isEqualTo(StatusType.CONTINUE);
    }

    @DisplayName("가장 최근의 게임 아이디를 가져온다.")
    @Test
    void findRecentChessGameId() {
        ChessGameDao chessGameDao = new ChessGameDao(connection);

        assertThatCode(chessGameDao::findRecentChessGameId)
                .doesNotThrowAnyException();
    }

    @DisplayName("게임의 상태를 변경한다.")
    @Test
    void updateStatusType() {
        ChessGameDao chessGameDao = new ChessGameDao(connection);
        int recentChessGameId = chessGameDao.findRecentChessGameId();

        chessGameDao.updateStatusType(recentChessGameId, StatusType.END);

        ResponseChessGameDto recentChessGame = chessGameDao.findRecentChessGame();
        assertThat(recentChessGame.statusType()).isEqualTo(StatusType.END);
    }
}

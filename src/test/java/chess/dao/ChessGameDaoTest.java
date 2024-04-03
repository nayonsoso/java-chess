package chess.dao;

import chess.dao.dto.ResponseChessGameDto;
import chess.domain.GameStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChessGameDaoTest {

    private final String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    @BeforeEach
    void setUp() throws SQLException {
        String createTable = "CREATE TABLE chess_game (id INT PRIMARY KEY AUTO_INCREMENT, status VARCHAR(50) NOT NULL);";
        String insertData = "INSERT INTO chess_game (status) VALUES ('NOT_END')";

        try (var connection = DriverManager.getConnection(url, "sa", "")) {
            try (var statement = connection.createStatement()) {
                statement.execute(createTable);
                statement.execute(insertData);
            }
        }
    }

    @AfterEach
    void cleanUp() throws SQLException {
        String dropTable = "DROP TABLE chess_game;";

        try (var connection = DriverManager.getConnection(url, "sa", "")) {
            try (var statement = connection.createStatement()) {
                statement.execute(dropTable);
            }
        }
    }

    @DisplayName("새로운 게임을 저장한다.")
    @Test
    void saveNewChessGame() throws SQLException {
        Connection connection = DriverManager.getConnection(url, "sa", "");
        ChessGameDao chessGameDao = new ChessGameDao(connection);
        ResponseChessGameDto before = chessGameDao.findRecentChessGame();

        chessGameDao.saveNewChessGame();

        ResponseChessGameDto after = chessGameDao.findRecentChessGame();
        assertAll(
                () -> assertThat(after.id()).isEqualTo(before.id() + 1),
                () -> assertThat(after.gameStatus()).isEqualTo(GameStatus.NOT_END)
        );
    }

    @DisplayName("가장 최근의 게임 아이디를 가져온다.")
    @Test
    void findRecentChessGameId() throws SQLException {
        Connection connection = DriverManager.getConnection(url, "sa", "");
        ChessGameDao chessGameDao = new ChessGameDao(connection);

        assertThatCode(chessGameDao::findRecentChessGameId)
                .doesNotThrowAnyException();
    }

    @DisplayName("게임의 상태를 변경한다.")
    @Test
    void updateGameStatus() throws SQLException {
        Connection connection = DriverManager.getConnection(url, "sa", "");
        ChessGameDao chessGameDao = new ChessGameDao(connection);
        int recentChessGameId = chessGameDao.findRecentChessGameId();

        chessGameDao.updateGameStatus(recentChessGameId, GameStatus.END);

        ResponseChessGameDto recentChessGame = chessGameDao.findRecentChessGame();
        assertThat(recentChessGame.gameStatus()).isEqualTo(GameStatus.END);
    }
}

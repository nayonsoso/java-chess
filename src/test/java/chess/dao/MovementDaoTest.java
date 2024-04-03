package chess.dao;

import chess.dao.dto.RequestMovementDto;
import chess.domain.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatCode;

class MovementDaoTest {

    private final String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    @BeforeEach
    void setUp() throws SQLException {
        String createTable1 = "CREATE TABLE chess_game (id INT PRIMARY KEY AUTO_INCREMENT, status VARCHAR(50) NOT NULL);";
        String insertData1 = "INSERT INTO chess_game (id, status) VALUES (1, 'NOT_END')";
        String createTable2 = "create table movement (\n" +
                "id int primary key auto_increment,\n" +
                "source_file varchar(50),\n" +
                "source_rank varchar(50),\n" +
                "target_file varchar(50),\n" +
                "target_rank varchar(50),\n" +
                "chess_game_id int)";
        String insertData2 = "INSERT INTO movement (source_file, source_rank, target_file, target_rank, chess_game_id) " +
                "VALUES ('A', 'TWO', 'A', 'FOUR', 1)";

        try (var connection = DriverManager.getConnection(url, "sa", "")) {
            try (var statement = connection.createStatement()) {
                statement.execute(createTable1);
                statement.execute(insertData1);
                statement.execute(createTable2);
                statement.execute(insertData2);
            }
        }
    }

    @AfterEach
    void cleanUp() throws SQLException {
        String dropTable1 = "DROP TABLE chess_game;";
        String dropTable2 = "DROP TABLE movement;";

        try (var connection = DriverManager.getConnection(url, "sa", "")) {
            try (var statement = connection.createStatement()) {
                statement.execute(dropTable1);
                statement.execute(dropTable2);
            }
        }
    }

    @DisplayName("게임 아이디에 해당하는 이동의 목록을 가져온다.")
    @Test
    void findByChessGameIdOrderedByAsc() throws SQLException {
        Connection connection = DriverManager.getConnection(url, "sa", "");
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
    void addMovement() throws SQLException {
        Connection connection = DriverManager.getConnection(url, "sa", "");
        MovementDao movementDao = new MovementDao(connection);
        Position source = Position.of("a1");
        Position target = Position.of("a4");
        RequestMovementDto requestMovementDto = RequestMovementDto.of(source, target, 1);

        assertThatCode(() -> movementDao.addMovement(requestMovementDto))
                .doesNotThrowAnyException();
    }
}

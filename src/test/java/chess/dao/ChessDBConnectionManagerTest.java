package chess.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ChessDBConnectionManagerTest {

    @DisplayName("DB 연결 테스트")
    @Test
    void connection() throws SQLException {
        ChessDBConnectionManager chessDBConnectionManager = new ChessDBConnectionManager();
        Connection connection = chessDBConnectionManager.getConnection();

        assertAll(
                () -> assertNotNull(connection),
                () -> assertFalse(connection.isClosed())
        );
        connection.close();
    }
}

package chess.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChessDBConnectionManagerTest {

    @DisplayName("DB 연결 테스트")
    @Test
    void connection() throws SQLException {
        ChessDBConnectionManager chessDBConnectionManager = new ChessDBConnectionManager();
        Connection connection = chessDBConnectionManager.getConnection();

        assertAll(
                () -> assertThat(connection).isNotNull(),
                () -> assertThat(connection.isClosed()).isFalse()
        );
        connection.close();
    }
}

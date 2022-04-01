package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.position.Direction;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BishopTest {

    @Test
    @DisplayName("비숍이 갈 수있는 방향으로 위치리스트를 반환한다.")
    void getMovablePositionsByRook() {
        Bishop bishop = new Bishop(Color.BLACK);
        Map<Direction, List<Position>> positions = bishop.getMovablePositions(Position.of("d4"));
        Map<Direction, List<Position>> expected = new HashMap<>(
                Map.ofEntries(
                        Map.entry(Direction.NORTH_EAST, List.of(
                                Position.of("e5"), Position.of("f6"),
                                Position.of("g7"), Position.of("h8"))),
                        Map.entry(Direction.NORTH_WEST, List.of(
                                Position.of("c5"), Position.of("b6"),
                                Position.of("a7"))),
                        Map.entry(Direction.SOUTH_EAST, List.of(
                                Position.of("e3"), Position.of("f2"),
                                Position.of("g1"))),
                        Map.entry(Direction.SOUTH_WEST, List.of(
                                Position.of("c3"), Position.of("b2"),
                                Position.of("a1")))
                )
        );
        assertThat(positions).isEqualTo(expected);
    }

    @Test
    @DisplayName("비숍은 3점이다.")
    void getPoint() {
        Piece bishop = new Bishop(Color.BLACK);
        assertThat(bishop.getPoint()).isEqualTo(3);
    }
}

package chess.domain;

import chess.domain.piece.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static chess.domain.piece.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @ParameterizedTest
    @MethodSource("getMovementResult")
    @DisplayName("방향에 맞게 이동한다.")
    void moveTowardDirection(Position sourcePosition, Direction direction, Position expectedTargetPosition) {
        Position actualTargetPosition = sourcePosition.moveTowardDirection(direction);

        assertThat(actualTargetPosition).isEqualTo(expectedTargetPosition);
    }

    static Stream<Arguments> getMovementResult() {
        return Stream.of(
                Arguments.of(Position.of("d5"), UP, Position.of("d6")),
                Arguments.of(Position.of("d5"), DOWN, Position.of("d4")),
                Arguments.of(Position.of("d5"), LEFT, Position.of("c5")),
                Arguments.of(Position.of("d5"), RIGHT, Position.of("e5")),
                Arguments.of(Position.of("d5"), LEFT_UP, Position.of("c6")),
                Arguments.of(Position.of("d5"), LEFT_DOWN, Position.of("c4")),
                Arguments.of(Position.of("d5"), RIGHT_UP, Position.of("e6")),
                Arguments.of(Position.of("d5"), RIGHT_DOWN, Position.of("e4")),
                Arguments.of(Position.of("d5"), LEFT_LEFT_UP, Position.of("b6")),
                Arguments.of(Position.of("d5"), LEFT_LEFT_DOWN, Position.of("b4")),
                Arguments.of(Position.of("d5"), RIGHT_RIGHT_UP, Position.of("f6")),
                Arguments.of(Position.of("d5"), RIGHT_RIGHT_DOWN, Position.of("f4")),
                Arguments.of(Position.of("d5"), LEFT_UP_UP, Position.of("c7")),
                Arguments.of(Position.of("d5"), RIGHT_UP_UP, Position.of("e7")),
                Arguments.of(Position.of("d5"), LEFT_DOWN_DOWN, Position.of("c3")),
                Arguments.of(Position.of("d5"), RIGHT_DOWN_DOWN, Position.of("e3"))
        );
    }
}

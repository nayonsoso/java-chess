package chess.domain.piece.multistep;

import chess.domain.Position;
import chess.fixture.SourceTargetPositions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static chess.domain.piece.multistep.Rook.WHITE_ROOK;
import static org.assertj.core.api.Assertions.assertThat;

class RookTest {

    @Nested
    @DisplayName("주어진 위치로 이동할 수 있는지 반환한다.")
    class canMove {

        @ParameterizedTest
        @MethodSource("makeMovablePositions")
        @DisplayName("이동할 수 있는 위치이면 true를 반환한다.")
        void canMove(Position sourcePosition, Position targetPosition) {
            boolean result = WHITE_ROOK.canMove(sourcePosition, targetPosition);

            assertThat(result).isTrue();
        }

        @ParameterizedTest
        @MethodSource("makeNotMovablePositions")
        @DisplayName("이동할 수 없는 위치이면 false를 반환한다.")
        void canNotMove(Position sourcePosition, Position targetPosition) {
            boolean result = WHITE_ROOK.canMove(sourcePosition, targetPosition);

            assertThat(result).isFalse();
        }

        static Stream<Arguments> makeMovablePositions() {
            return SourceTargetPositions.verticalAndHorizon();
        }

        static Stream<Arguments> makeNotMovablePositions() {
            return Stream.concat(
                    SourceTargetPositions.diagonal(),
                    SourceTargetPositions.lShape()
            );
        }
    }
}

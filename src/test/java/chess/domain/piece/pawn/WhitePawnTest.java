package chess.domain.piece.pawn;

import chess.domain.Position;
import chess.fixture.SourceTargetPositions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static chess.domain.piece.pawn.WhitePawn.WHITE_PAWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class WhitePawnTest {

    @Nested
    @DisplayName("주어진 위치로 이동할 수 있는지 반환한다.")
    class canMove {

        @Test
        @DisplayName("시작 위치에서 이동할 수 있는 위치이면 true를 반환한다.")
        void canMoveFromInitial() {
            Position sourcePosition = Position.of("a2");
            Position targetPosition = Position.of("a4");

            boolean result = WHITE_PAWN.canMove(sourcePosition, targetPosition);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("이동할 수 있는 위치이면 true를 반환한다.")
        void canMove() {
            Position sourcePosition = Position.of("a3");
            Position targetPosition = Position.of("a4");

            boolean result = WHITE_PAWN.canMove(sourcePosition, targetPosition);

            assertThat(result).isTrue();
        }

        @ParameterizedTest
        @MethodSource("makeNotMovablePositions")
        @DisplayName("이동할 수 없는 위치이면 false를 반환한다.")
        void canNotMove(Position sourcePosition, Position targetPosition) {
            boolean result = WHITE_PAWN.canMove(sourcePosition, targetPosition);

            assertThat(result).isFalse();
        }

        static Stream<Arguments> makeNotMovablePositions() {
            Stream<Arguments> verticalAndHorizonExceptUp = Stream.of(
                    Arguments.of(Position.of("d4"), Position.of("d3")),
                    Arguments.of(Position.of("d4"), Position.of("c4")),
                    Arguments.of(Position.of("d4"), Position.of("e4"))
            );

            return Stream.of(
                    verticalAndHorizonExceptUp,
                    SourceTargetPositions.lShape(),
                    SourceTargetPositions.diagonal()
            ).flatMap(Function.identity());
        }
    }

    @Nested
    @DisplayName("주어진 위치로 공격할 수 있는지 반환한다.")
    class canAttack {

        @Test
        @DisplayName("공격할 수 있는 위치이면 true를 반환한다.")
        void canAttack() {
            Position sourcePosition = Position.of("d4");
            Position targetPosition1 = Position.of("c5");
            Position targetPosition2 = Position.of("e5");

            boolean result1 = WHITE_PAWN.canAttack(sourcePosition, targetPosition1);
            boolean result2 = WHITE_PAWN.canAttack(sourcePosition, targetPosition2);

            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue()
            );
        }

        @ParameterizedTest
        @MethodSource("makeNotAttackablePositions")
        @DisplayName("공격할 수 없는 위치이면 false를 반환한다.")
        void canNotAttack(Position sourcePosition, Position targetPosition) {
            boolean result = WHITE_PAWN.canAttack(sourcePosition, targetPosition);

            assertThat(result).isFalse();
        }

        static Stream<Arguments> makeNotAttackablePositions() {
            Stream<Arguments> diagonalExceptUp = Stream.of(
                    Arguments.of(Position.of("d4"), Position.of("c3")),
                    Arguments.of(Position.of("d4"), Position.of("e3"))
            );

            return Stream.of(
                    diagonalExceptUp,
                    SourceTargetPositions.verticalAndHorizon(),
                    SourceTargetPositions.lShape()
            ).flatMap(Function.identity());
        }
    }

    @DisplayName("폰인지 반환한다.")
    @Test
    void isPawn() {
        boolean isPawn = WHITE_PAWN.isPawn();

        assertThat(isPawn).isTrue();
    }

    @DisplayName("점수를 반환한다.")
    @Test
    void getScore() {
        double actualScore = WHITE_PAWN.getScore();
        double expectedScore = 1;

        assertThat(actualScore).isEqualTo(expectedScore);
    }
}

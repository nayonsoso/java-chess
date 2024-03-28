package chess.domain.piece.pawn;

import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;
import chess.fixture.SourceTargetPositions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static chess.domain.piece.pawn.BlackPawn.BLACK_PAWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BlackPawnTest {

    @Nested
    @DisplayName("주어진 위치로 이동할 수 있는지 반환한다.")
    class canMove {

        @Test
        @DisplayName("시작 위치에서 이동할 수 있는 위치이면 true를 반환한다.")
        void canMoveFromInitial() {
            Position sourcePosition = Position.of(File.A, Rank.SEVEN);
            Position targetPosition = Position.of(File.A, Rank.FIVE);

            boolean result = BLACK_PAWN.canMove(sourcePosition, targetPosition);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("이동할 수 있는 위치이면 true를 반환한다.")
        void canMove() {
            Position sourcePosition = Position.of(File.A, Rank.FOUR);
            Position targetPosition = Position.of(File.A, Rank.THREE);

            boolean result = BLACK_PAWN.canMove(sourcePosition, targetPosition);

            assertThat(result).isTrue();
        }

        @ParameterizedTest
        @MethodSource("makeNotMovablePositions")
        @DisplayName("이동할 수 없는 위치이면 false를 반환한다.")
        void canNotMove(Position sourcePosition, Position targetPosition) {
            boolean result = BLACK_PAWN.canMove(sourcePosition, targetPosition);

            assertThat(result).isFalse();
        }

        static Stream<Arguments> makeNotMovablePositions() {
            Stream<Arguments> verticalAndHorizonExceptDown = Stream.of(
                    Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.D, Rank.FIVE)),
                    Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.FOUR)),
                    Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.FOUR))
            );

            return Stream.of(
                    verticalAndHorizonExceptDown,
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
            Position sourcePosition = Position.of(File.D, Rank.FOUR);
            Position targetPosition1 = Position.of(File.C, Rank.THREE);
            Position targetPosition2 = Position.of(File.E, Rank.THREE);

            boolean result1 = BLACK_PAWN.canAttack(sourcePosition, targetPosition1);
            boolean result2 = BLACK_PAWN.canAttack(sourcePosition, targetPosition2);

            assertAll(
                    () -> assertThat(result1).isTrue(),
                    () -> assertThat(result2).isTrue()
            );
        }

        @ParameterizedTest
        @MethodSource("makeNotAttackablePositions")
        @DisplayName("공격할 수 없는 위치이면 false를 반환한다.")
        void canNotAttack(Position sourcePosition, Position targetPosition) {
            boolean result = BLACK_PAWN.canAttack(sourcePosition, targetPosition);

            assertThat(result).isFalse();
        }

        static Stream<Arguments> makeNotAttackablePositions() {
            Stream<Arguments> diagonalExceptDown = Stream.of(
                    Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.FIVE)),
                    Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.FIVE))
            );

            return Stream.of(
                    diagonalExceptDown,
                    SourceTargetPositions.verticalAndHorizon(),
                    SourceTargetPositions.lShape()
            ).flatMap(Function.identity());
        }
    }

    @DisplayName("폰인지 반환한다.")
    @Test
    void isPawn() {
        boolean isPawn = BLACK_PAWN.isPawn();

        assertThat(isPawn).isTrue();
    }
}

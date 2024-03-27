package chess.domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static chess.domain.piece.Pawn.WHITE_PAWN;

class PawnTest {

    @Test
    @DisplayName("원하는 만큼 움직일 수 있는지 반환한다.")
    void canMoveMoreThenOnce() {
        Pawn pawn = WHITE_PAWN;

        boolean canMoveMoreThenOnce = pawn.canMoveMoreThenOnce();

        Assertions.assertThat(canMoveMoreThenOnce).isFalse();
    }

    @Nested
    @DisplayName("주어진 방향으로 이동할 수 있는지 반환한다.")
    class canMoveInTargetDirection {

        @ParameterizedTest
        @CsvSource(value = {
                "WHITE_PAWN,UP,true", "WHITE_PAWN,DOWN,false",
                "BLACK_PAWN,UP,false", "BLACK_PAWN,DOWN,true"
        })
        @DisplayName("색에 따라 위, 아래 이동이 가능해진다.")
        void canMove(Pawn pawn, Direction direction, boolean canMove) {
            boolean result = pawn.canMoveInTargetDirection(direction);

            Assertions.assertThat(result).isEqualTo(canMove);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "LEFT", "RIGHT",
                "LEFT_UP", "LEFT_DOWN",
                "RIGHT_UP", "RIGHT_DOWN",
                "KNIGHT_LEFT_UP", "KNIGHT_LEFT_DOWN",
                "KNIGHT_RIGHT_UP", "KNIGHT_RIGHT_DOWN",
                "KNIGHT_UP_LEFT", "KNIGHT_UP_RIGHT",
                "KNIGHT_DOWN_LEFT", "KNIGHT_DOWN_RIGHT"
        })
        @DisplayName("이동할 수 없는 방향이면 false를 반환한다.")
        void canNotMove(Direction direction) {
            Pawn pawn = WHITE_PAWN;

            boolean result = pawn.canMoveInTargetDirection(direction);

            Assertions.assertThat(result).isFalse();
        }
    }

    @DisplayName("폰인지를 반환한다.")
    @Test
    void isPawn() {
        Pawn pawn = WHITE_PAWN;

        boolean isPawn = pawn.isPawn();

        Assertions.assertThat(isPawn).isTrue();
    }
}

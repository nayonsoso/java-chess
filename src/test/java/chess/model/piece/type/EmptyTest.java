package chess.model.piece.type;

import static org.assertj.core.api.Assertions.assertThat;

import chess.model.piece.Piece;
import chess.model.position.Distance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyTest {

    private final Piece empty = Piece.EMPTY;

    @Test
    @DisplayName("pick()을 호출하면 자기 자신을 반환한다")
    void pick_whenCall_thenReturnThis() {
        // when
        final Piece actual = empty.pick();

        // then
        assertThat(actual).isSameAs(empty);
    }

    @Test
    @DisplayName("movable()을 호출하면 false를 반환한다")
    void movable_whenCall_thenReturnFalse() {
        // when
        final Distance distance = new Distance(1, 1);
        final boolean actual = empty.movable(distance, empty);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("isNotPassable()은 호출하면 false를 반환한다")
    void isNotPassable_whenCall_thenReturnFalse() {
        // when
        final boolean actual = empty.isNotPassable();

        // then
        assertThat(actual).isFalse();
    }
}
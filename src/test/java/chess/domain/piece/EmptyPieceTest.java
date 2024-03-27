package chess.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;
import static org.assertj.core.api.Assertions.assertThat;

class EmptyPieceTest {

    @Test
    @DisplayName("항상 이동할 수 없다.")
    void alwaysCanNotMove() {

        boolean result = EMPTY_PIECE.canMove(null, null);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("항상 공격할 수 없다.")
    void alwaysCanNotAttack() {
        boolean result = EMPTY_PIECE.canAttack(null, null);

        assertThat(result).isFalse();
    }
}

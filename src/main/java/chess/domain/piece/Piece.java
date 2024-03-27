package chess.domain.piece;

import chess.domain.Position;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    protected final Set<Direction> movementDirections = new HashSet<>();
    protected final Color color;

    protected Piece(final Color color) {
        this.color = color;
    }

    public abstract boolean canMove(final Position source, final Position target);

    public boolean canAttack(final Position source, final Position target) {
        return canMove(source, target);
    }

    protected final boolean canMoveInDirection(final Position source, final Position target) {
        Direction direction = source.calculateDirection(target);

        return this.movementDirections.contains(direction);
    }

    public final boolean isSameColor(Piece piece) {
        return this.color == piece.color;
    }

    public final boolean isOppositeColor(Piece piece) {
        if (this.color.isBlack()) {
            return piece.color.isWhite();
        }
        if (this.color.isWhite()) {
            return piece.color.isBlack();
        }
        return false;
    }
}

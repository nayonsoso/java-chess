package chess.domain.piece;

import chess.domain.Position;

import java.util.Set;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Color color;
    protected final MoveStrategy moveStrategy;

    protected Piece(final PieceType pieceType, final Color color, final MoveStrategy moveStrategy) {
        this.pieceType = pieceType;
        this.color = color;
        this.moveStrategy = moveStrategy;
    }

    public abstract Set<Direction> getMoveDirections();

    public abstract Set<Direction> getAttackDirections();

    public boolean canMove(final Position source, final Position target) {
        return moveStrategy.canMove(getMoveDirections(), source, target);
    }

    public boolean canAttack(final Position source, final Position target) {
        return moveStrategy.canAttack(getAttackDirections(), source, target);
    }

    public boolean isKing() {
        return false;
    }

    public boolean isPawn() {
        return false;
    }

    public double getScore() {
        return this.pieceType.getScore();
    }

    public Color getColor() {
        return this.color;
    }

    public final boolean isSameColor(final Piece piece) {
        return this.color == piece.color;
    }

    public final boolean isSameColor(final Color color) {
        return this.color == color;
    }

    public final boolean isOppositeColor(final Piece piece) {
        if (this.color.isBlack()) {
            return piece.color.isWhite();
        }
        if (this.color.isWhite()) {
            return piece.color.isBlack();
        }
        return false;
    }

    public final boolean isOppositeColor(final Color color) {
        if (this.color.isBlack()) {
            return color.isWhite();
        }
        if (this.color.isWhite()) {
            return color.isBlack();
        }
        return false;
    }
}

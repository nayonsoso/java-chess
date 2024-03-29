package chess.domain.piece;

import chess.domain.Position;

import java.util.Set;

public class EmptyPiece extends Piece {

    public static final EmptyPiece EMPTY_PIECE = new EmptyPiece();

    private EmptyPiece() {
        super(null, Color.EMPTY, null);
    }

    @Override
    public boolean canMove(final Position source, final Position target) {
        return false;
    }

    @Override
    public boolean canAttack(final Position source, final Position target) {
        return false;
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return Set.of();
    }

    @Override
    public Set<Direction> getAttackDirections() {
        return Set.of();
    }

    @Override
    public double getScore() {
        return 0;
    }
}

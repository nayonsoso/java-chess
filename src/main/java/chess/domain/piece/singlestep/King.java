package chess.domain.piece.singlestep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;

import java.util.Set;

public class King extends SingleStepPiece {

    public static final King BLACK_KING = new King(Color.BLACK);
    public static final King WHITE_KING = new King(Color.WHITE);

    private King(final Color color) {
        super(color);
    }

    @Override
    protected void initMovementDirections() {
        Set<Direction> horizonAndVerticals = Direction.getHorizonAndVerticals();
        Set<Direction> diagonals = Direction.getDiagonals();
        this.movementDirections.addAll(horizonAndVerticals);
        this.movementDirections.addAll(diagonals);
    }

    @Override
    public boolean isKing() {
        return true;
    }
}

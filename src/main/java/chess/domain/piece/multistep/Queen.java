package chess.domain.piece.multistep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;

import java.util.Set;

public class Queen extends MultiStepPiece {

    public static final Queen BLACK_QUEEN = new Queen(Color.BLACK);
    public static final Queen WHITE_QUEEN = new Queen(Color.WHITE);

    private Queen(final Color color) {
        super(color);
        initMovementDirections();
    }

    @Override
    protected void initMovementDirections() {
        Set<Direction> horizonAndVerticals = Direction.getHorizonAndVerticals();
        Set<Direction> diagonals = Direction.getDiagonals();
        this.movementDirections.addAll(horizonAndVerticals);
        this.movementDirections.addAll(diagonals);
    }
}

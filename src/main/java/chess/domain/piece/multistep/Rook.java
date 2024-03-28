package chess.domain.piece.multistep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;

import java.util.Set;

public class Rook extends MultiStepPiece {

    public static final Rook BLACK_ROOK = new Rook(Color.BLACK);
    public static final Rook WHITE_ROOK = new Rook(Color.WHITE);

    private Rook(final Color color) {
        super(color);
        initMovementDirections();
    }

    @Override
    protected void initMovementDirections() {
        Set<Direction> horizonAndVerticals = Direction.getHorizonAndVerticals();
        this.movementDirections.addAll(horizonAndVerticals);
    }

    @Override
    protected void initScore() {
        this.score = 5;
    }
}

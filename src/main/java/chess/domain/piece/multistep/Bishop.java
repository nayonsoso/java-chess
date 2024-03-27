package chess.domain.piece.multistep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;

import java.util.Set;

public class Bishop extends MultiStepPiece {

    public static final Bishop BLACK_BISHOP = new Bishop(Color.BLACK);
    public static final Bishop WHITE_BISHOP = new Bishop(Color.WHITE);

    private Bishop(final Color color) {
        super(color);
        initMovementDirections();
    }

    @Override
    protected void initMovementDirections() {
        Set<Direction> diagonals = Direction.getDiagonals();
        this.movementDirections.addAll(diagonals);
    }
}

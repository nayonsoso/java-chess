package chess.domain.piece.singlestep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;

public class Knight extends SingleStepPiece {

    public static final Knight BLACK_KNIGHT = new Knight(Color.BLACK);
    public static final Knight WHITE_KNIGHT = new Knight(Color.WHITE);

    private Knight(final Color color) {
        super(color);
    }

    @Override
    protected void initMovementDirections() {
        this.movementDirections.add(Direction.LEFT_LEFT_UP);
        this.movementDirections.add(Direction.LEFT_LEFT_DOWN);
        this.movementDirections.add(Direction.RIGHT_RIGHT_UP);
        this.movementDirections.add(Direction.RIGHT_RIGHT_DOWN);
        this.movementDirections.add(Direction.LEFT_UP_UP);
        this.movementDirections.add(Direction.RIGHT_UP_UP);
        this.movementDirections.add(Direction.LEFT_DOWN_DOWN);
        this.movementDirections.add(Direction.RIGHT_DOWN_DOWN);
    }

    @Override
    protected void initScore() {
        this.score = 2.5;
    }
}

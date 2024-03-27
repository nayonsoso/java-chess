package chess.domain.piece;

public class Queen extends Piece {

    public static final Queen BLACK_QUEEN = new Queen(Color.BLACK);
    public static final Queen WHITE_QUEEN = new Queen(Color.WHITE);

    private Queen(final Color color) {
        super(color);
        initDirections();
    }

    private void initDirections() {
        this.directions.add(Direction.LEFT);
        this.directions.add(Direction.RIGHT);
        this.directions.add(Direction.UP);
        this.directions.add(Direction.DOWN);
        this.directions.add(Direction.LEFT_UP);
        this.directions.add(Direction.LEFT_DOWN);
        this.directions.add(Direction.RIGHT_UP);
        this.directions.add(Direction.RIGHT_DOWN);
    }

    @Override
    public boolean canMoveMoreThenOnce() {
        return true;
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}

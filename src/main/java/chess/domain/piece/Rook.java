package chess.domain.piece;

public class Rook extends Piece {

    public static final Rook BLACK_ROOK = new Rook(Color.BLACK);
    public static final Rook WHITE_ROOK = new Rook(Color.WHITE);

    private Rook(final Color color) {
        super(color);
        initDirections();
    }

    private void initDirections() {
        this.directions.add(Direction.LEFT);
        this.directions.add(Direction.RIGHT);
        this.directions.add(Direction.UP);
        this.directions.add(Direction.DOWN);
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

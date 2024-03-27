package chess.domain.piece;

public class Pawn extends Piece {

    public static final Pawn BLACK_PAWN = new Pawn(Color.BLACK);
    public static final Pawn WHITE_PAWN = new Pawn(Color.WHITE);

    private Pawn(final Color color) {
        super(color);
        initDirections();
    }

    private void initDirections() {
        if (color == Color.BLACK) {
            this.directions.add(Direction.DOWN);
        }
        if (color == Color.WHITE) {
            this.directions.add(Direction.UP);
        }
    }

    @Override
    public boolean canMoveMoreThenOnce() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return true;
    }
}

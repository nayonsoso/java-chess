package chess.domain.piece;

public class EmptyPiece extends Piece {

    public static final EmptyPiece EMPTY_PIECE = new EmptyPiece();

    private EmptyPiece() {
        super(Color.EMPTY);
    }

    @Override
    public boolean canMoveMoreThenOnce() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}

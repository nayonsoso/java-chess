package chess.domain.piece;

import chess.domain.Position;

public class EmptyPiece extends Piece {

    public static final EmptyPiece EMPTY_PIECE = new EmptyPiece();

    private EmptyPiece() {
        super(Color.EMPTY);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }
}

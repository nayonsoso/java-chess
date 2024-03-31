package chess.domain.piece.multistep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class Rook extends Piece {

    public static final Rook BLACK_ROOK = new Rook(Color.BLACK);
    public static final Rook WHITE_ROOK = new Rook(Color.WHITE);
    private static final Set<Direction> MOVE_DIRECTIONS = Direction.HORIZON_VERTICAL;

    private Rook(final Color color) {
        super(PieceType.ROOK, color, new MultiStepMoveStrategy());
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return MOVE_DIRECTIONS;
    }
}

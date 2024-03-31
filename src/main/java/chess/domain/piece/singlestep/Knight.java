package chess.domain.piece.singlestep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class Knight extends Piece {

    public static final Knight BLACK_KNIGHT = new Knight(Color.BLACK);
    public static final Knight WHITE_KNIGHT = new Knight(Color.WHITE);
    private static final Set<Direction> MOVE_DIRECTIONS = Direction.L_SHAPE;

    private Knight(final Color color) {
        super(PieceType.KNIGHT, color, new SingleStepMoveStrategy());
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return MOVE_DIRECTIONS;
    }
}

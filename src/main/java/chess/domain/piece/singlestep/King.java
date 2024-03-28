package chess.domain.piece.singlestep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class King extends Piece {

    public static final King BLACK_KING = new King(Color.BLACK);
    public static final King WHITE_KING = new King(Color.WHITE);

    private final Set<Direction> moveDirections = Direction.getHorizonVerticalAndDiagonals();

    private King(final Color color) {
        super(PieceType.KING, color, new SingleStepMoveStrategy());
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return moveDirections;
    }

    @Override
    public Set<Direction> getAttackDirections() {
        return getMoveDirections();
    }

    @Override
    public boolean isKing() {
        return true;
    }
}

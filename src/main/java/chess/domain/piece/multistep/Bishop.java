package chess.domain.piece.multistep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class Bishop extends Piece {

    public static final Bishop BLACK_BISHOP = new Bishop(Color.BLACK);
    public static final Bishop WHITE_BISHOP = new Bishop(Color.WHITE);

    private final Set<Direction> moveDirections = Direction.getDiagonals();

    private Bishop(final Color color) {
        super(PieceType.BISHOP, color, new MultiStepMoveStrategy());
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return this.moveDirections;
    }

    @Override
    public Set<Direction> getAttackDirections() {
        return getMoveDirections();
    }
}

package chess.domain.piece.multistep;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class Queen extends Piece {

    public static final Queen BLACK_QUEEN = new Queen(Color.BLACK);
    public static final Queen WHITE_QUEEN = new Queen(Color.WHITE);

    private final Set<Direction> moveDirections = Direction.getHorizonVerticalAndDiagonals();

    private Queen(final Color color) {
        super(PieceType.QUEEN, color, new MultiStepMoveStrategy());
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

package chess.domain.piece.pawn;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class BlackPawn extends Piece {

    public static final BlackPawn BLACK_PAWN = new BlackPawn(Color.BLACK);

    public final Set<Direction> moveDirections = Set.of(Direction.DOWN);
    public final Set<Direction> attackDirections = Set.of(Direction.LEFT_DOWN, Direction.RIGHT_DOWN);

    private BlackPawn(final Color color) {
        super(PieceType.PAWN, color, new PawnMoveStrategy(color));
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return moveDirections;
    }

    @Override
    public Set<Direction> getAttackDirections() {
        return attackDirections;
    }

    @Override
    public boolean isPawn() {
        return true;
    }
}

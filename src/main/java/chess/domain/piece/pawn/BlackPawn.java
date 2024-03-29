package chess.domain.piece.pawn;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class BlackPawn extends Piece {

    public static final BlackPawn BLACK_PAWN = new BlackPawn(Color.BLACK);
    private static final Set<Direction> MOVE_DIRECTIONS = Set.of(Direction.DOWN);
    private static final Set<Direction> ATTACK_DIRECTIONS = Set.of(Direction.LEFT_DOWN, Direction.RIGHT_DOWN);

    private BlackPawn(final Color color) {
        super(PieceType.PAWN, color, new PawnMoveStrategy(color));
    }

    @Override
    public Set<Direction> getMoveDirections() {
        return MOVE_DIRECTIONS;
    }

    @Override
    public Set<Direction> getAttackDirections() {
        return ATTACK_DIRECTIONS;
    }

    @Override
    public boolean isPawn() {
        return true;
    }
}

package chess.domain.piece.pawn;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class WhitePawn extends Piece {

    public static final WhitePawn WHITE_PAWN = new WhitePawn(Color.WHITE);
    private static final Set<Direction> MOVE_DIRECTIONS = Set.of(Direction.UP);
    private static final Set<Direction> ATTACK_DIRECTIONS = Set.of(Direction.LEFT_UP, Direction.RIGHT_UP);


    private WhitePawn(final Color color) {
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

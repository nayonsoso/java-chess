package chess.domain.piece.pawn;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

import java.util.Set;

public class WhitePawn extends Piece {

    public static final WhitePawn WHITE_PAWN = new WhitePawn(Color.WHITE);

    public final Set<Direction> moveDirections = Set.of(Direction.UP);
    public final Set<Direction> attackDirections = Set.of(Direction.LEFT_UP, Direction.RIGHT_UP);


    private WhitePawn(final Color color) {
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

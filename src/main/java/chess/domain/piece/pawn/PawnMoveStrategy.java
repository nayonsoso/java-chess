package chess.domain.piece.pawn;

import chess.domain.board.Position;
import chess.domain.piece.property.Color;

import java.util.Set;

public interface PawnMoveStrategy {

    Set<Position> computePath(final Position source, final Position target);

    static PawnMoveStrategy from(Color color) {
        if (color == Color.BLACK) {
            return new BlackPawnMoveStrategy();
        }
        return new WhitePawnMoveStrategy();
    }
}
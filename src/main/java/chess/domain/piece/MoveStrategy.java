package chess.domain.piece;

import chess.domain.Position;

import java.util.Set;

public interface MoveStrategy {

    boolean canMove(final Set<Direction> movementDirections,
                    final Position sourcePosition,
                    final Position targetPosition);

    boolean canAttack(final Set<Direction> movementDirections,
                      final Position sourcePosition,
                      final Position targetPosition);
}

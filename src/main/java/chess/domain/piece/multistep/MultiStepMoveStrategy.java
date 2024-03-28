package chess.domain.piece.multistep;

import chess.domain.Position;
import chess.domain.piece.Direction;
import chess.domain.piece.MoveStrategy;

import java.util.Set;

public class MultiStepMoveStrategy implements MoveStrategy {

    @Override
    public final boolean canMove(final Set<Direction> movementDirections,
                                 final Position sourcePosition,
                                 final Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);

        return movementDirections.contains(direction);
    }

    @Override
    public boolean canAttack(final Set<Direction> movementDirections,
                             final Position sourcePosition,
                             final Position targetPosition) {
        return canMove(movementDirections, sourcePosition, targetPosition);
    }
}

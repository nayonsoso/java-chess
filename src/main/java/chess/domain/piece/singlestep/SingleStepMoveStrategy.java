package chess.domain.piece.singlestep;

import chess.domain.Position;
import chess.domain.piece.Direction;
import chess.domain.piece.MoveStrategy;

import java.util.Set;

public class SingleStepMoveStrategy implements MoveStrategy {

    @Override
    public final boolean canMove(final Set<Direction> movementDirections,
                                 final Position sourcePosition,
                                 final Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        boolean canMoveInDirection = movementDirections.contains(direction);
        boolean canReachInSingleStep = canReachInSingleStep(sourcePosition, targetPosition);

        return canMoveInDirection && canReachInSingleStep;
    }

    @Override
    public boolean canAttack(final Set<Direction> movementDirections,
                             final Position sourcePosition,
                             final Position targetPosition) {
        return canMove(movementDirections, sourcePosition, targetPosition);
    }

    private boolean canReachInSingleStep(final Position sourcePosition, final Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        Position nextPosition = sourcePosition.moveTowardDirection(direction);

        return nextPosition.equals(targetPosition);
    }
}

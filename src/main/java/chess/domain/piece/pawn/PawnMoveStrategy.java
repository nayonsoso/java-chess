package chess.domain.piece.pawn;

import chess.domain.Position;
import chess.domain.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.MoveStrategy;

import java.util.Set;

public class PawnMoveStrategy implements MoveStrategy {

    private final Color color;

    public PawnMoveStrategy(final Color color) {
        this.color = color;
    }

    @Override
    public final boolean canMove(final Set<Direction> movementDirections,
                                 final Position sourcePosition,
                                 final Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        boolean canMoveInDirection = movementDirections.contains(direction);
        boolean canReachInSingleStep = canReachInSingleStep(sourcePosition, targetPosition);

        boolean canReachInTwoStep = false;
        Rank initialRank = getInitialRank(this.color);
        if (sourcePosition.isAtRank(initialRank)) {
            canReachInTwoStep = canReachInTwoStep(sourcePosition, targetPosition);
        }

        return canMoveInDirection && (canReachInSingleStep || canReachInTwoStep);
    }

    @Override
    public boolean canAttack(final Set<Direction> attackDirections,
                             final Position sourcePosition,
                             final Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        boolean canAttackToward = attackDirections.contains(direction);
        boolean canReachInSingleStep = canReachInSingleStep(sourcePosition, targetPosition);

        return canAttackToward && canReachInSingleStep;
    }

    private boolean canReachInSingleStep(final Position source, final Position target) {
        Direction direction = source.calculateDirection(target);
        Position positionAfterSingleStep = source.moveTowardDirection(direction);

        return positionAfterSingleStep.equals(target);
    }

    private Rank getInitialRank(final Color color) {
        if (color.isWhite()) {
            return Rank.TWO;
        }

        return Rank.SEVEN;
    }

    private boolean canReachInTwoStep(final Position source, final Position target) {
        Direction direction = source.calculateDirection(target);
        Position positionAfterSingleStep = source.moveTowardDirection(direction);
        if (positionAfterSingleStep.equals(target)) {
            return true;
        }

        return canReachInSingleStep(positionAfterSingleStep, target);
    }
}

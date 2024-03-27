package chess.domain.piece.pawn;

import chess.domain.Position;
import chess.domain.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;

import java.util.HashSet;
import java.util.Set;

public abstract class Pawn extends Piece {

    protected final Set<Direction> attackDirections = new HashSet<>();
    protected Rank initialRank;

    protected Pawn(final Color color) {
        super(color);
        initMovementDirections();
        initAttackDirections();
        initInitialRank();
    }

    protected abstract void initMovementDirections();

    protected abstract void initAttackDirections();

    protected abstract void initInitialRank();

    protected final boolean canReachInSingleStep(Position source, Position target) {
        Direction direction = source.calculateDirection(target);
        Position positionAfterSingleStep = source.moveTowardDirection(direction);

        return positionAfterSingleStep.equals(target);
    }

    protected final boolean canReachInTwoStep(Position source, Position target) {
        Direction direction = source.calculateDirection(target);
        Position positionAfterSingleStep = source.moveTowardDirection(direction);
        if (positionAfterSingleStep.equals(target)) {
            return true;
        }

        return canReachInSingleStep(positionAfterSingleStep, target);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        boolean canMoveInDirection = canMoveInDirection(source, target);
        boolean canReachInSingleStep = canReachInSingleStep(source, target);
        boolean canReachInTwoStep = false;
        if (source.isAtRank(initialRank)) {
            canReachInTwoStep = canReachInTwoStep(source, target);
        }

        return canMoveInDirection && (canReachInSingleStep || canReachInTwoStep);
    }

    @Override
    public boolean canAttack(final Position source, final Position target) {
        Direction direction = source.calculateDirection(target);
        boolean canAttackToward = attackDirections.contains(direction);
        boolean canReachInSingleStep = canReachInSingleStep(source, target);

        return canAttackToward && canReachInSingleStep;
    }
}

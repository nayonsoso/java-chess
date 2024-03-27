package chess.domain.piece.singlestep;

import chess.domain.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;

public abstract class SingleStepPiece extends Piece {

    protected SingleStepPiece(Color color) {
        super(color);
        initMovementDirections();
    }

    protected abstract void initMovementDirections();

    @Override
    public final boolean canMove(final Position sourcePosition, final Position targetPosition) {
        boolean canMoveInDirection = canMoveInDirection(sourcePosition, targetPosition);
        boolean canReachInSingleStep = canReachInSingleStep(sourcePosition, targetPosition);

        return canMoveInDirection && canReachInSingleStep;
    }

    private boolean canReachInSingleStep(final Position sourcePosition, final Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        Position nextPosition = sourcePosition.moveTowardDirection(direction);

        return nextPosition.equals(targetPosition);
    }
}

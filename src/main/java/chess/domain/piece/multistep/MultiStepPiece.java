package chess.domain.piece.multistep;

import chess.domain.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public abstract class MultiStepPiece extends Piece {

    protected MultiStepPiece(Color color) {
        super(color);
        initMovementDirections();
        initScore();
    }

    protected abstract void initMovementDirections();

    protected abstract void initScore();

    @Override
    public final boolean canMove(final Position sourcePosition, final Position targetPosition) {
        return canMoveInDirection(sourcePosition, targetPosition);
    }
}

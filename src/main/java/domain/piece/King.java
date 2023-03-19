package domain.piece;

import domain.coordinate.Position;
import domain.coordinate.Route;
import domain.squarestatus.Piece;
import domain.type.PieceType;

import java.util.Collections;

public final class King extends Piece {

    public King(final Color color) {
        super(color, PieceType.KING);
    }

    @Override
    public Route findRoute(final Position source, final Position target) {
        validateMovable(source, target);
        return new Route(Collections.emptyList());
    }

    @Override
    protected boolean isMovable(final Position source, final Position target) {
        int diffY = Math.abs(target.diffY(source));
        int diffX = Math.abs(target.diffX(source));

        return (diffY != 0 || diffX != 0) && (diffX < 2 && diffY < 2);
    }

}

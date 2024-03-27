package chess.domain.piece.pawn;

import chess.domain.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.Direction;

import java.util.Set;

public class WhitePawn extends Pawn {

    public static final WhitePawn WHITE_PAWN = new WhitePawn(Color.WHITE);

    private WhitePawn(final Color color) {
        super(color);
    }

    @Override
    protected void initMovementDirections() {
        this.movementDirections.add(Direction.UP);
    }

    @Override
    protected void initAttackDirections() {
        this.attackDirections.addAll(Set.of(Direction.LEFT_UP, Direction.RIGHT_UP));
    }

    @Override
    protected void initInitialRank() {
        this.initialRank = Rank.TWO;
    }
}

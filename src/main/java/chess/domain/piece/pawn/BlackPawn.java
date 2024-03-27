package chess.domain.piece.pawn;

import chess.domain.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.Direction;

import java.util.Set;

public class BlackPawn extends Pawn {

    public static final BlackPawn BLACK_PAWN = new BlackPawn(Color.BLACK);

    private BlackPawn(final Color color) {
        super(color);
        initMovementDirections();
    }

    @Override
    protected void initMovementDirections() {
        this.movementDirections.add(Direction.DOWN);
    }

    @Override
    protected void initAttackDirections() {
        this.attackDirections.addAll(Set.of(Direction.LEFT_DOWN, Direction.RIGHT_DOWN));
    }

    @Override
    protected void initInitialRank() {
        this.initialRank = Rank.SEVEN;
    }
}

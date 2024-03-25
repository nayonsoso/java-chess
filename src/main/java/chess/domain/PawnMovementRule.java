package chess.domain;

import chess.domain.piece.Direction;
import chess.domain.piece.Piece;

import java.util.List;

public class PawnMovementRule {

    private final List<Position> pawnInitialPositions = List.of(
            Position.of(File.A, Rank.TWO), Position.of(File.B, Rank.TWO), Position.of(File.C, Rank.TWO), Position.of(File.D, Rank.TWO),
            Position.of(File.E, Rank.TWO), Position.of(File.F, Rank.TWO), Position.of(File.G, Rank.TWO), Position.of(File.H, Rank.TWO),
            Position.of(File.A, Rank.SEVEN), Position.of(File.B, Rank.SEVEN), Position.of(File.C, Rank.SEVEN), Position.of(File.D, Rank.SEVEN),
            Position.of(File.E, Rank.SEVEN), Position.of(File.F, Rank.SEVEN), Position.of(File.G, Rank.SEVEN), Position.of(File.H, Rank.SEVEN)
    );
    private final List<Direction> pawnAttackDirections = List.of(
            Direction.LEFT_UP, Direction.LEFT_DOWN, Direction.RIGHT_UP, Direction.RIGHT_DOWN
    );

    public boolean canReachTargetFromInitialPosition(Position sourcePosition, Position targetPosition, Direction direction) {
        Position nextPosition = sourcePosition.moveTowardDirection(direction);
        Position nextNextPosition = nextPosition.moveTowardDirection(direction);

        return pawnInitialPositions.contains(sourcePosition) &&
                (nextPosition.equals(targetPosition) || nextNextPosition.equals(targetPosition));
    }

    public boolean canMoveTowardDiagonal(Piece sourcePiece, Piece targetPiece, Direction direction) {
        return pawnAttackDirections.contains(direction) && targetPiece.isEnemy(sourcePiece);
    }
}
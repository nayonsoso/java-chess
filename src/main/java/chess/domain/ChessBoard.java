package chess.domain;

import chess.domain.piece.Direction;
import chess.domain.piece.Piece;

import java.util.Map;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;

public class ChessBoard {

    private final Map<Position, Piece> chessBoard;

    public ChessBoard(final Map<Position, Piece> chessBoard) {
        this.chessBoard = chessBoard;
    }

    public void move(final Position sourcePosition, final Position targetPosition) {
        validateCommonRule(sourcePosition, targetPosition);
        validateCanMove(sourcePosition, targetPosition);
        validateReachedTarget(sourcePosition, targetPosition);

        Piece sourcePiece = chessBoard.get(sourcePosition);
        chessBoard.put(targetPosition, sourcePiece);
        chessBoard.put(sourcePosition, EMPTY_PIECE);
    }

    private void validateCommonRule(Position sourcePosition, Position targetPosition) {
        validateNotSourceItSelf(sourcePosition, targetPosition);
        validateSourcePositionNotEmpty(sourcePosition);
        validateTargetNotAlly(sourcePosition, targetPosition);
    }

    private void validateNotSourceItSelf(Position sourcePosition, Position targetPosition) {
        if (sourcePosition.equals(targetPosition)) {
            throw new IllegalArgumentException("제자리로 움직일 수 없습니다.");
        }
    }

    private void validateTargetNotAlly(Position source, Position target) {
        if (chessBoard.get(source).isSameColor(chessBoard.get(target))) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validateSourcePositionNotEmpty(Position position) {
        if (chessBoard.get(position).equals(EMPTY_PIECE)) {
            throw new IllegalArgumentException("비어있는 곳에서는 기물을 움직일 수 없습니다.");
        }
    }

    private void validateCanMove(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = chessBoard.get(sourcePosition);
        Piece targetPiece = chessBoard.get(targetPosition);
        if (sourcePiece.isOppositeColor(targetPiece)) {
            validateCanAttack(sourcePosition, targetPosition);
            return;
        }

        boolean canMove = sourcePiece.canMove(sourcePosition, targetPosition);
        if (!canMove) {
            throw new IllegalArgumentException("선택한 위치로 이동할 수 없습니다.");
        }
    }

    private void validateCanAttack(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = chessBoard.get(sourcePosition);
        boolean canAttack = sourcePiece.canAttack(sourcePosition, targetPosition);

        if (!canAttack) {
            throw new IllegalArgumentException("선택한 위치로 이동할 수 없습니다.");
        }
    }

    private void validateReachedTarget(Position sourcePosition, Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        Position reachedPosition = moveUntilTargetOrMeetSomeThing(sourcePosition, targetPosition, direction);

        if (!reachedPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("선택한 기물은 해당 위치에 도달할 수 없습니다.");
        }
    }

    private Position moveUntilTargetOrMeetSomeThing(Position sourcePosition, Position targetPosition, Direction direction) {
        Position nextPosition = sourcePosition.moveTowardDirection(direction);
        while (!nextPosition.equals(targetPosition) &&
                chessBoard.get(nextPosition) == EMPTY_PIECE) {
            nextPosition = nextPosition.moveTowardDirection(direction);
        }

        return nextPosition;
    }

    public Piece findPieceByPosition(final Position position) {
        return chessBoard.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return this.chessBoard;
    }
}

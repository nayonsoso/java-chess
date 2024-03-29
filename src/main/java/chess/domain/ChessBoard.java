package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Direction;
import chess.domain.piece.Piece;

import java.util.List;
import java.util.Map;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;
import static chess.domain.piece.PieceType.REPEATED_PAWN;

public class ChessBoard {

    private final Map<Position, Piece> chessBoard;

    public ChessBoard(final Map<Position, Piece> chessBoard) {
        this.chessBoard = chessBoard;
    }

    public MoveResult move(final Position sourcePosition, final Position targetPosition) {
        validateMove(sourcePosition, targetPosition);
        Piece sourcePiece = chessBoard.get(sourcePosition);
        Piece targetPiece = chessBoard.get(targetPosition);
        chessBoard.put(targetPosition, sourcePiece);
        chessBoard.put(sourcePosition, EMPTY_PIECE);

        if (targetPiece.isKing()) {
            return MoveResult.GAME_END;
        }
        return MoveResult.GAME_NOT_END;
    }

    private void validateMove(final Position sourcePosition, final Position targetPosition) {
        validateCommonRule(sourcePosition, targetPosition);
        validateMoveOrAttack(sourcePosition, targetPosition);
        validateNoObstacleInRoute(sourcePosition, targetPosition);
    }

    private void validateCommonRule(Position sourcePosition, Position targetPosition) {
        validateNotSourceItSelf(sourcePosition, targetPosition);
        validateSourcePositionNotEmpty(sourcePosition);
        validateTargetNotSameColor(sourcePosition, targetPosition);
    }

    private void validateNotSourceItSelf(Position sourcePosition, Position targetPosition) {
        if (sourcePosition.equals(targetPosition)) {
            throw new IllegalArgumentException("제자리로 움직일 수 없습니다.");
        }
    }

    private void validateTargetNotSameColor(Position source, Position target) {
        if (chessBoard.get(source).isSameColor(chessBoard.get(target))) {
            throw new IllegalArgumentException("이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    private void validateSourcePositionNotEmpty(Position position) {
        if (chessBoard.get(position).equals(EMPTY_PIECE)) {
            throw new IllegalArgumentException("비어있는 곳에서는 기물을 움직일 수 없습니다.");
        }
    }

    private void validateMoveOrAttack(Position sourcePosition, Position targetPosition) {
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

    private void validateNoObstacleInRoute(Position sourcePosition, Position targetPosition) {
        Direction direction = sourcePosition.calculateDirection(targetPosition);
        Position reachedPosition = moveUntilTargetOrMeetSomeThing(sourcePosition, targetPosition, direction);

        if (!reachedPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("이동 경로에 다른 기물이 존재합니다.");
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

    public double calculateScoreByColor(Color color) {
        double totalScore = 0;
        for (File file : File.values()) {
            double fileScore = calculateScoreForFileAndColor(file, color);
            double deduction = calculateDeductionForFileAndColor(file, color);
            totalScore = totalScore + fileScore + deduction;
        }

        return totalScore;
    }

    private double calculateDeductionForFileAndColor(File file, Color color) {
        List<Piece> piecesFoundByFileAndColor = findPiecesByFileAndColor(file, color);
        int pawnCount = (int) piecesFoundByFileAndColor.stream()
                .filter(Piece::isPawn)
                .count();
        if (pawnCount > 1) {
            return pawnCount * REPEATED_PAWN.getScore();
        }

        return 0;
    }

    private double calculateScoreForFileAndColor(File file, Color color) {
        List<Piece> piecesFoundByFileAndColor = findPiecesByFileAndColor(file, color);

        return piecesFoundByFileAndColor.stream()
                .map(Piece::getScore)
                .mapToDouble(Double::valueOf)
                .sum();
    }

    private List<Piece> findPiecesByFileAndColor(File file, Color color) {
        return chessBoard.entrySet().stream()
                .filter(entry -> entry.getKey().isAtFile(file) && entry.getValue().isSameColor(color))
                .map(Map.Entry::getValue)
                .toList();
    }
}

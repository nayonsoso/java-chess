package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class ChessGame {

    private final ChessBoard chessBoard;
    private final GameStatus gameStatus;
    private final Color currentRoundColor;

    public ChessGame(final ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.currentRoundColor = Color.WHITE;
        this.gameStatus = GameStatus.NOT_END;
    }

    public ChessGame(final ChessBoard chessBoard, final Color color, final GameStatus gameStatus) {
        this.chessBoard = chessBoard;
        this.currentRoundColor = color;
        this.gameStatus = gameStatus;
    }

    public ChessGame executeRound(final Position sourcePosition, final Position targetPosition) {
        validateRoundColor(sourcePosition);
        GameStatus gameStatusAfterMove = chessBoard.move(sourcePosition, targetPosition);
        Color nextRoundColor = currentRoundColor.getOppositeColor();

        return new ChessGame(this.chessBoard, nextRoundColor, gameStatusAfterMove);
    }

    private void validateRoundColor(final Position sourcePosition) {
        Piece sourcePiece = chessBoard.findPieceByPosition(sourcePosition);
        if (sourcePiece.isOppositeColor(this.currentRoundColor)) {
            throw new IllegalArgumentException("차례가 아닙니다.");
        }
    }

    public double calculateScore(final Color color) {
        return this.chessBoard.calculateScoreByColor(color);
    }

    public boolean isEnd() {
        return gameStatus.isEnd();
    }

    public ChessBoard getChessBoard() {
        return this.chessBoard;
    }

    public Color getCurrentRoundColor() {
        return currentRoundColor;
    }
}

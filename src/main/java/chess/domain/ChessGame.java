package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class ChessGame {

    private final ChessBoard chessBoard;
    private Color currentRoundColor = Color.WHITE;

    public ChessGame(final ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public ChessGame(final ChessBoard chessBoard, final Color color) {
        this.chessBoard = chessBoard;
        this.currentRoundColor = color;
    }

    public GameStatus executeRound(final Position sourcePosition, final Position targetPosition) {
        validateRoundColor(sourcePosition);
        GameStatus gameStatus = chessBoard.move(sourcePosition, targetPosition);

        if (!gameStatus.isEnd()) {
            currentRoundColor = currentRoundColor.getOppositeColor();
        }
        return gameStatus;
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

    public ChessBoard getChessBoard() {
        return this.chessBoard;
    }

    public Color getCurrentRoundColor() {
        return currentRoundColor;
    }
}

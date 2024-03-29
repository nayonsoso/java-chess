package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class ChessGame {

    private final ChessBoard chessBoard;
    private Color currentRoundColor = Color.WHITE;

    public ChessGame(final ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public MoveResult executeRound(final Position sourcePosition, final Position targetPosition) {
        validateRoundColor(sourcePosition);
        MoveResult moveResult = chessBoard.move(sourcePosition, targetPosition);

        if (!moveResult.isEnd()) {
            currentRoundColor = currentRoundColor.getOppositeColor();
        }
        return moveResult;
    }

    private void validateRoundColor(final Position sourcePosition) {
        Piece sourcePiece = chessBoard.findPieceByPosition(sourcePosition);
        chessBoard.findPieceByPosition(sourcePosition);
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

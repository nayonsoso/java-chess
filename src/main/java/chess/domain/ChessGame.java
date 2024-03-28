package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class ChessGame {

    private final ChessBoard chessBoard;
    private Color currentRoundColor = Color.WHITE;

    public ChessGame(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public GameStatus executeRound(Position sourcePosition, Position targetPosition) {
        validateRoundColor(sourcePosition);
        GameStatus gameStatus = chessBoard.move(sourcePosition, targetPosition);

        if (!gameStatus.isEnd()) {
            currentRoundColor = currentRoundColor.getOppositeColor();
        }
        return gameStatus;
    }

    private void validateRoundColor(Position sourcePosition) {
        Piece sourcePiece = chessBoard.findPieceByPosition(sourcePosition);
        chessBoard.findPieceByPosition(sourcePosition);
        if (sourcePiece.isOppositeColor(this.currentRoundColor)) {
            throw new IllegalArgumentException("차례가 아닙니다.");
        }
    }
}

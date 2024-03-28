package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class ChessGame {

    private final ChessBoard chessBoard;
    private Color currentRoundColor = Color.WHITE;

    public ChessGame(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public MoveResult executeRound(Position sourcePosition, Position targetPosition) {
        validateRoundColor(sourcePosition);
        MoveResult moveResult = chessBoard.move(sourcePosition, targetPosition);

        if (!moveResult.isEnd()) {
            currentRoundColor = currentRoundColor.getOppositeColor();
        }
        return moveResult;
    }

    private void validateRoundColor(Position sourcePosition) {
        Piece sourcePiece = chessBoard.findPieceByPosition(sourcePosition);
        chessBoard.findPieceByPosition(sourcePosition);
        if (sourcePiece.isOppositeColor(this.currentRoundColor)) {
            throw new IllegalArgumentException("차례가 아닙니다.");
        }
    }

    public ChessBoard getChessBoard() {
        return this.chessBoard;
    }
}

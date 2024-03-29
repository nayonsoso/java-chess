package chess.view;

import chess.domain.ChessBoard;
import chess.domain.Position;
import chess.domain.piece.Piece;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;
import static chess.domain.piece.multistep.Bishop.BLACK_BISHOP;
import static chess.domain.piece.multistep.Bishop.WHITE_BISHOP;
import static chess.domain.piece.multistep.Queen.BLACK_QUEEN;
import static chess.domain.piece.multistep.Queen.WHITE_QUEEN;
import static chess.domain.piece.multistep.Rook.BLACK_ROOK;
import static chess.domain.piece.multistep.Rook.WHITE_ROOK;
import static chess.domain.piece.pawn.BlackPawn.BLACK_PAWN;
import static chess.domain.piece.pawn.WhitePawn.WHITE_PAWN;
import static chess.domain.piece.singlestep.King.BLACK_KING;
import static chess.domain.piece.singlestep.King.WHITE_KING;
import static chess.domain.piece.singlestep.Knight.BLACK_KNIGHT;
import static chess.domain.piece.singlestep.Knight.WHITE_KNIGHT;

public record ChessBoardDto(String chessBoardExpression) {

    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final Map<Piece, String> PIECE_EXPRESSION = Map.ofEntries(
            Map.entry(BLACK_KING, "K"),
            Map.entry(BLACK_QUEEN, "Q"),
            Map.entry(BLACK_ROOK, "R"),
            Map.entry(BLACK_BISHOP, "B"),
            Map.entry(BLACK_KNIGHT, "N"),
            Map.entry(BLACK_PAWN, "P"),
            Map.entry(WHITE_KING, "k"),
            Map.entry(WHITE_QUEEN, "q"),
            Map.entry(WHITE_ROOK, "r"),
            Map.entry(WHITE_BISHOP, "b"),
            Map.entry(WHITE_KNIGHT, "n"),
            Map.entry(WHITE_PAWN, "p"),
            Map.entry(EMPTY_PIECE, ".")
    );

    public static ChessBoardDto from(final ChessBoard chessBoard) {
        Map<Position, Piece> board = chessBoard.getBoard();
        List<String> pieceExpressions = board.values().stream()
                .map(ChessBoardDto::toPieceExpression)
                .toList();

        StringBuilder stringBuilder = new StringBuilder();
        for (int width = 0; width < WIDTH; width++) {
            stringBuilder.append(makeLineExpression(width, pieceExpressions));
        }

        return new ChessBoardDto(stringBuilder.toString());
    }

    private static String toPieceExpression(final Piece piece) {
        return PIECE_EXPRESSION.get(piece);
    }

    private static String makeLineExpression(final int lineNumber, final List<String> pieceExpressions) {
        int startIdx = lineNumber * WIDTH;
        int endIdx = lineNumber * WIDTH + HEIGHT;
        List<String> pieceExpressionsOnLine = pieceExpressions.subList(startIdx, endIdx);

        return pieceExpressionsOnLine.stream()
                .collect(Collectors.joining("", "", System.lineSeparator()));
    }
}

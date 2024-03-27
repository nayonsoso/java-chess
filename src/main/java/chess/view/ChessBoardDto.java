package chess.view;

import chess.domain.ChessBoard;
import chess.domain.Position;
import chess.domain.piece.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static chess.domain.piece.Color.BLACK;
import static chess.domain.piece.Color.WHITE;

public record ChessBoardDto(String chessBoardExpression) {

    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private static final Map<Piece, String> PIECE_EXPRESSION = Map.ofEntries(
            Map.entry(King.of(BLACK), "K"),
            Map.entry(Queen.of(BLACK), "Q"),
            Map.entry(Rook.of(BLACK), "R"),
            Map.entry(Bishop.of(BLACK), "B"),
            Map.entry(Knight.of(BLACK), "N"),
            Map.entry(Pawn.of(BLACK), "P"),
            Map.entry(King.of(WHITE), "k"),
            Map.entry(Queen.of(WHITE), "q"),
            Map.entry(Rook.of(WHITE), "r"),
            Map.entry(Bishop.of(WHITE), "b"),
            Map.entry(Knight.of(WHITE), "n"),
            Map.entry(Pawn.of(WHITE), "p"),
            Map.entry(EmptyPiece.of(), ".")
    );

    public static ChessBoardDto of(ChessBoard chessBoard) {
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

package chess.dto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import chess.board.ChessBoard;
import chess.board.File;
import chess.board.Position;
import chess.board.Rank;
import chess.piece.Piece;

public class ChessBoardDto {

    private final List<PieceDto> pieceDtos;

    public static ChessBoardDto toView(final ChessBoard board) {
        final List<PieceDto> pieces = pieceToView(board.getBoard());
        return new ChessBoardDto(pieces);
    }

    private ChessBoardDto(final List<PieceDto> pieceDtos) {
        this.pieceDtos = pieceDtos;
    }

    private static List<PieceDto> pieceToView(final Map<Position, Piece> piecePosition) {
        final List<PieceDto> pieceDtos = new LinkedList<>();

        Arrays.stream(Rank.values())
                .forEach(rank -> Arrays.stream(File.values())
                        .map(file -> piecePosition.get(new Position(file, rank)))
                        .map(PieceDto::new)
                        .forEach(pieceDtos::add));

        return pieceDtos;
    }

    public List<PieceDto> getPieceDtos() {
        return pieceDtos;
    }
}

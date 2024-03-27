package chess.domain;

import chess.domain.piece.EmptyPiece;
import chess.domain.piece.Piece;

import java.util.LinkedHashMap;
import java.util.Map;

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

public class ChessBoardFactory {

    private ChessBoardFactory() {
    }

    public static ChessBoard makeChessBoard() {
        Map<Position, Piece> chessBoard = new LinkedHashMap<>();
        chessBoard.putAll(makeBlackPiecesExceptPawn());
        chessBoard.putAll(makeBlackPawns());
        chessBoard.putAll(makeBlankPieces());
        chessBoard.putAll(makeWhitePawns());
        chessBoard.putAll(makeWhitePiecesExceptPawn());

        return new ChessBoard(chessBoard);
    }

    private static Map<Position, Piece> makeBlackPiecesExceptPawn() {
        Map<Position, Piece> chessBoard = new LinkedHashMap<>();
        chessBoard.put(Position.of(File.A, Rank.EIGHT), BLACK_ROOK);
        chessBoard.put(Position.of(File.B, Rank.EIGHT), BLACK_KNIGHT);
        chessBoard.put(Position.of(File.C, Rank.EIGHT), BLACK_BISHOP);
        chessBoard.put(Position.of(File.D, Rank.EIGHT), BLACK_QUEEN);
        chessBoard.put(Position.of(File.E, Rank.EIGHT), BLACK_KING);
        chessBoard.put(Position.of(File.F, Rank.EIGHT), BLACK_BISHOP);
        chessBoard.put(Position.of(File.G, Rank.EIGHT), BLACK_KNIGHT);
        chessBoard.put(Position.of(File.H, Rank.EIGHT), BLACK_ROOK);

        return chessBoard;
    }

    private static Map<Position, Piece> makeBlackPawns() {
        Map<Position, Piece> blackPawns = new LinkedHashMap<>();
        File[] files = File.values();
        for (File file : files) {
            blackPawns.put(Position.of(file, Rank.SEVEN), BLACK_PAWN);
        }

        return blackPawns;
    }

    private static Map<Position, Piece> makeBlankPieces() {
        Map<Position, Piece> blankPieces = new LinkedHashMap<>();
        blankPieces.putAll(makeBlankPiecesForRank(Rank.SIX));
        blankPieces.putAll(makeBlankPiecesForRank(Rank.FIVE));
        blankPieces.putAll(makeBlankPiecesForRank(Rank.FOUR));
        blankPieces.putAll(makeBlankPiecesForRank(Rank.THREE));

        return blankPieces;
    }

    private static Map<Position, Piece> makeBlankPiecesForRank(Rank rank) {
        Map<Position, Piece> blankPieces = new LinkedHashMap<>();
        File[] files = File.values();
        for (File file : files) {
            blankPieces.put(Position.of(file, rank), EmptyPiece.EMPTY_PIECE);
        }

        return blankPieces;
    }

    private static Map<Position, Piece> makeWhitePawns() {
        Map<Position, Piece> whitePawns = new LinkedHashMap<>();
        File[] files = File.values();
        for (File file : files) {
            whitePawns.put(Position.of(file, Rank.TWO), WHITE_PAWN);
        }

        return whitePawns;
    }

    private static Map<Position, Piece> makeWhitePiecesExceptPawn() {
        Map<Position, Piece> chessBoard = new LinkedHashMap<>();
        chessBoard.put(Position.of(File.A, Rank.ONE), WHITE_ROOK);
        chessBoard.put(Position.of(File.B, Rank.ONE), WHITE_KNIGHT);
        chessBoard.put(Position.of(File.C, Rank.ONE), WHITE_BISHOP);
        chessBoard.put(Position.of(File.D, Rank.ONE), WHITE_QUEEN);
        chessBoard.put(Position.of(File.E, Rank.ONE), WHITE_KING);
        chessBoard.put(Position.of(File.F, Rank.ONE), WHITE_BISHOP);
        chessBoard.put(Position.of(File.G, Rank.ONE), WHITE_KNIGHT);
        chessBoard.put(Position.of(File.H, Rank.ONE), WHITE_ROOK);

        return chessBoard;
    }
}

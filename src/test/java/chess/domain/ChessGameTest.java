package chess.domain;

import chess.domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;
import static chess.domain.piece.multistep.Queen.WHITE_QUEEN;
import static chess.domain.piece.pawn.WhitePawn.WHITE_PAWN;
import static chess.domain.piece.singlestep.King.BLACK_KING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ChessGameTest {

    @Nested
    @DisplayName("라운드를 진행한다.")
    class executeRound {

        @DisplayName("결과가 종료인 라운드를 진행한다.")
        @Test
        void executeEndRound() {
            Position sourcePosition = Position.of(File.B, Rank.SEVEN);
            Position targetPosition = Position.of(File.B, Rank.EIGHT);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, WHITE_QUEEN);
            initialPositions.put(targetPosition, BLACK_KING);
            ChessBoard chessBoard = new ChessBoard(initialPositions);
            ChessGame chessGame = new ChessGame(chessBoard);

            MoveResult moveResult = chessGame.executeRound(sourcePosition, targetPosition);

            assertThat(moveResult).isEqualTo(MoveResult.GAME_END);
        }

        @DisplayName("결과가 종료가 아닌 라운드를 진행한다.")
        @Test
        void executeNotEndRound() {
            Position sourcePosition = Position.of(File.B, Rank.TWO);
            Position targetPosition = Position.of(File.B, Rank.THREE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, WHITE_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);
            ChessGame chessGame = new ChessGame(chessBoard);

            MoveResult moveResult = chessGame.executeRound(sourcePosition, targetPosition);

            assertThat(moveResult).isEqualTo(MoveResult.GAME_NOT_END);
        }
    }


    @Nested
    @DisplayName("라운드를 검증한다.")
    class validateRound {

        @DisplayName("최초의 차례는 흰색이다.")
        @Test
        void firstRoundIsForWhite() {
            ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
            ChessGame chessGame = new ChessGame(chessBoard);
            Position whiteSourcePosition = Position.of(File.A, Rank.TWO);
            Position whiteTargetPosition = Position.of(File.A, Rank.THREE);

            assertThatCode(() -> chessGame.executeRound(whiteSourcePosition, whiteTargetPosition))
                    .doesNotThrowAnyException();
        }

        @DisplayName("차례가 아닌 기물을 이동시키려 하면 예외가 발생한다.")
        @Test
        void canNotExecuteRoundForNotAllowedColor() {
            ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
            ChessGame chessGame = new ChessGame(chessBoard);
            Position blackSourcePosition = Position.of(File.A, Rank.SEVEN);
            Position blackTargetPosition = Position.of(File.A, Rank.SIX);

            assertThatCode(() -> chessGame.executeRound(blackSourcePosition, blackTargetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("차례가 아닙니다.");
        }

        @DisplayName("한번 라운드를 진행하면, 반대 차례의 기물을 움직일 수 있다.")
        @Test
        void colorForRoundChangesAfterRound() {
            ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
            ChessGame chessGame = new ChessGame(chessBoard);
            Position whiteSourcePosition = Position.of(File.A, Rank.TWO);
            Position whiteTargetPosition = Position.of(File.A, Rank.THREE);
            Position blackSourcePosition = Position.of(File.A, Rank.SEVEN);
            Position blackTargetPosition = Position.of(File.A, Rank.SIX);
            chessGame.executeRound(whiteSourcePosition, whiteTargetPosition);

            assertThatCode(() -> chessGame.executeRound(blackSourcePosition, blackTargetPosition))
                    .doesNotThrowAnyException();
        }
    }
}

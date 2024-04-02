package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;
import static chess.domain.piece.multistep.Bishop.BLACK_BISHOP;
import static chess.domain.piece.multistep.Queen.WHITE_QUEEN;
import static chess.domain.piece.multistep.Rook.BLACK_ROOK;
import static chess.domain.piece.pawn.WhitePawn.WHITE_PAWN;
import static chess.domain.piece.singlestep.King.BLACK_KING;
import static chess.domain.piece.singlestep.Knight.WHITE_KNIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ChessGameTest {

    @Nested
    @DisplayName("라운드를 진행한다.")
    class executeRound {

        @DisplayName("결과가 종료인 라운드를 진행한다.")
        @Test
        void executeEndRound() {
            Position sourcePosition = Position.of("b7");
            Position targetPosition = Position.of("b8");
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
            Position sourcePosition = Position.of("b2");
            Position targetPosition = Position.of("b3");
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
            Position whiteSourcePosition = Position.of("a2");
            Position whiteTargetPosition = Position.of("a3");

            assertThatCode(() -> chessGame.executeRound(whiteSourcePosition, whiteTargetPosition))
                    .doesNotThrowAnyException();
        }

        @DisplayName("차례가 아닌 기물을 이동시키려 하면 예외가 발생한다.")
        @Test
        void canNotExecuteRoundForNotAllowedColor() {
            ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
            ChessGame chessGame = new ChessGame(chessBoard);
            Position blackSourcePosition = Position.of("a7");
            Position blackTargetPosition = Position.of("a6");

            assertThatCode(() -> chessGame.executeRound(blackSourcePosition, blackTargetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("차례가 아닙니다.");
        }

        @DisplayName("한번 라운드를 진행하면, 반대 차례의 기물을 움직일 수 있다.")
        @Test
        void colorForRoundChangesAfterRound() {
            ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
            ChessGame chessGame = new ChessGame(chessBoard);
            Position whiteSourcePosition = Position.of("a2");
            Position whiteTargetPosition = Position.of("a3");
            Position blackSourcePosition = Position.of("a7");
            Position blackTargetPosition = Position.of("a6");
            chessGame.executeRound(whiteSourcePosition, whiteTargetPosition);

            assertThatCode(() -> chessGame.executeRound(blackSourcePosition, blackTargetPosition))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("점수를 계산한다.")
    class calculateScore {

        @Test
        @DisplayName("흰색 기물의 점수를 계산한다.")
        void calculateWhiteScore() {
            Position position1 = Position.of("a1");
            Position position2 = Position.of("b1");
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(position1, WHITE_PAWN);
            initialPositions.put(position2, WHITE_KNIGHT);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            double whiteScore = chessBoard.calculateScoreByColor(Color.WHITE);

            assertThat(whiteScore).isEqualTo(3.5);
        }

        @Test
        @DisplayName("검정 기물의 점수를 계산한다.")
        void calculateBlackScore() {
            Position position1 = Position.of("a1");
            Position position2 = Position.of("b1");
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(position1, BLACK_ROOK);
            initialPositions.put(position2, BLACK_BISHOP);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            double blackScore = chessBoard.calculateScoreByColor(Color.BLACK);

            assertThat(blackScore).isEqualTo(8);
        }

        @Test
        @DisplayName("폰이 세로 줄에 둘 이상 있는 경우의 점수를 계산한다.")
        void calculateScoreWhenPawnsAreInSameFile() {
            Position position1 = Position.of("a1");
            Position position2 = Position.of("a2");
            Position position3 = Position.of("a3");
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(position1, WHITE_PAWN);
            initialPositions.put(position2, WHITE_PAWN);
            initialPositions.put(position3, WHITE_PAWN);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            double blackScore = chessBoard.calculateScoreByColor(Color.WHITE);

            assertThat(blackScore).isEqualTo(1.5);
        }
    }
}

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
import static chess.domain.piece.multistep.Rook.BLACK_ROOK;
import static chess.domain.piece.multistep.Rook.WHITE_ROOK;
import static chess.domain.piece.pawn.BlackPawn.BLACK_PAWN;
import static chess.domain.piece.pawn.WhitePawn.WHITE_PAWN;
import static chess.domain.piece.singlestep.King.BLACK_KING;
import static chess.domain.piece.singlestep.Knight.BLACK_KNIGHT;
import static chess.domain.piece.singlestep.Knight.WHITE_KNIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ChessBoardTest {

    @Nested
    @DisplayName("기물을 타켓 위치로 옮긴다.")
    class moveSuccess {

        @Test
        @DisplayName("기물을 타켓 위치로 옮긴다.")
        void movePiece() {
            Position sourcePosition = Position.of(File.A, Rank.TWO);
            Position targetPosition = Position.of(File.A, Rank.THREE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, WHITE_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            chessBoard.move(sourcePosition, targetPosition);

            Piece pieceAfterMove = chessBoard.findPieceByPosition(targetPosition);
            assertThat(pieceAfterMove).isEqualTo(WHITE_PAWN);
        }

        @Test
        @DisplayName("기물이 이동하면 기존 위치에는 기물이 존재하지 않게 된다.")
        void noPieceOnSourcePositionWhenPieceMoves() {
            Position sourcePosition = Position.of(File.B, Rank.SEVEN);
            Position targetPosition = Position.of(File.B, Rank.SIX);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            chessBoard.move(sourcePosition, targetPosition);

            Piece pieceAtSourcePositionAfterMove = chessBoard.findPieceByPosition(sourcePosition);
            assertThat(pieceAtSourcePositionAfterMove).isEqualTo(EMPTY_PIECE);
        }

        @Test
        @DisplayName("타켓 위치에 적팀 기물이 존재하는 경우, 덮어씌운다")
        void kill() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position targetPosition = Position.of(File.A, Rank.TWO);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_ROOK);
            initialPositions.put(targetPosition, WHITE_ROOK);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            chessBoard.move(sourcePosition, targetPosition);

            Piece pieceAtTargetPosition = chessBoard.findPieceByPosition(targetPosition);
            assertThat(pieceAtTargetPosition).isEqualTo(BLACK_ROOK);
        }
    }

    @Nested
    @DisplayName("기본 이동 규칙을 검증한다.")
    class validateCommonRule {

        @DisplayName("제자리로 이동하려는 경우 예외가 발생한다.")
        @Test
        void validateNotSourceItSelf() {
            Position sourcePosition = Position.of(File.B, Rank.TWO);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_PAWN);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, sourcePosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("제자리로 움직일 수 없습니다.");
        }

        @DisplayName("비어있는 칸에서 이동하려는 경우 예외가 발생한다.")
        @Test
        void validateSourcePositionNotEmpty() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position targetPosition = Position.of(File.A, Rank.TWO);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, EMPTY_PIECE);
            initialPositions.put(targetPosition, BLACK_PAWN);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("비어있는 곳에서는 기물을 움직일 수 없습니다.");
        }

        @Test
        @DisplayName("이동하려는 지점에 같은 팀이 존재하는 경우 예외가 발생한다")
        void validateTargetNotSameColor() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position targetPosition = Position.of(File.A, Rank.TWO);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_KING);
            initialPositions.put(targetPosition, BLACK_PAWN);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동하려는 위치에 아군 기물이 존재합니다.");
        }
    }

    @Nested
    @DisplayName("선택한 기물이 타켓 위치로 이동할 수 있는지 검증한다.")
    class validateCanMove {

        @Test
        @DisplayName("선택한 기물이 이동할 수 없는 방향으로 이동인 경우 예외가 발생한다.")
        void invalidDirection() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position targetPosition = Position.of(File.C, Rank.THREE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_ROOK);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("선택한 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("기물이 타켓 위치까지 갈 수 없는 경우 예외가 발생한다.")
        void cannotReachTarget() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position targetPosition = Position.of(File.C, Rank.THREE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_KING);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("선택한 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("폰의 이동을 검증한다.")
    class validatePawnCanMove {

        @Test
        @DisplayName("폰은 최초의 이동에서 두칸을 전진할 수 있다.")
        void pawnCanMoveTwiceIfFirstMove() {
            Position sourcePosition = Position.of(File.A, Rank.SEVEN);
            Position middlePosition = Position.of(File.A, Rank.SIX);
            Position targetPosition = Position.of(File.A, Rank.FIVE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_PAWN);
            initialPositions.put(middlePosition, EMPTY_PIECE);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("폰은 적이 있다면, 대각선 앞으로 이동할 수 있다.")
        void pawnCanMoveDiagonalIfEnemyThere() {
            Position sourcePosition = Position.of(File.A, Rank.SEVEN);
            Position targetPosition = Position.of(File.B, Rank.SIX);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_PAWN);
            initialPositions.put(targetPosition, WHITE_PAWN);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .doesNotThrowAnyException();
        }

        @DisplayName("폰이 시작 위치에 있지 않은데 두 칸을 이동하려 하는 경우 예외가 발생한다.")
        @Test
        void pawnCanNotMoveTwiceIfItIsNotOnStartPositions() {
            Position sourcePosition = Position.of(File.A, Rank.THREE);
            Position targetPosition = Position.of(File.A, Rank.FIVE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, WHITE_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("선택한 위치로 이동할 수 없습니다.");
        }

        @DisplayName("폰의 대각선에 적이 없음에도 대각선으로 이동하려 하면, 예외가 발생한다.")
        @Test
        void pawnCanNotMoveDiagonalIfThereIsNoEnemy() {
            Position sourcePosition = Position.of(File.A, Rank.SEVEN);
            Position targetPosition = Position.of(File.B, Rank.SIX);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("선택한 위치로 이동할 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("이동 경로에 다른 기물이 존재하는지 검증한다.")
    class validateNoObstacleInRoute {

        @Test
        @DisplayName("이동 경로에 기물이 있는 경우 예외가 발생한다.")
        void obstacleExistOnRoute() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position obstaclePosition = Position.of(File.A, Rank.TWO);
            Position targetPosition = Position.of(File.A, Rank.THREE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_ROOK);
            initialPositions.put(obstaclePosition, BLACK_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동 경로에 다른 기물이 존재합니다.");
        }

        @Test
        @DisplayName("나이트는 기물을 넘어 이동할 수 있다.")
        void knightCanJumpOverPiece() {

            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position obstaclePosition = Position.of(File.A, Rank.TWO);
            Position targetPosition = Position.of(File.B, Rank.THREE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_KNIGHT);
            initialPositions.put(obstaclePosition, BLACK_PAWN);
            initialPositions.put(targetPosition, EMPTY_PIECE);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                    .doesNotThrowAnyException();
        }
    }


    @Nested
    @DisplayName("점수를 계산한다.")
    class calculateScore {

        @Test
        @DisplayName("흰색 기물의 점수를 계산한다.")
        void calculateWhiteScore() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position obstaclePosition = Position.of(File.B, Rank.ONE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, WHITE_PAWN);
            initialPositions.put(obstaclePosition, WHITE_KNIGHT);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            double whiteScore = chessBoard.calculateScore(Color.WHITE);

            assertThat(whiteScore).isEqualTo(3.5);
        }

        @Test
        @DisplayName("검정 기물의 점수를 계산한다.")
        void calculateBlackScore() {
            Position sourcePosition = Position.of(File.A, Rank.ONE);
            Position obstaclePosition = Position.of(File.B, Rank.ONE);
            Map<Position, Piece> initialPositions = new HashMap<>();
            initialPositions.put(sourcePosition, BLACK_ROOK);
            initialPositions.put(obstaclePosition, BLACK_BISHOP);
            ChessBoard chessBoard = new ChessBoard(initialPositions);

            double blackScore = chessBoard.calculateScore(Color.BLACK);

            assertThat(blackScore).isEqualTo(8);
        }
    }
}

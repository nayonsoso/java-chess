package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.piece.multistep.Rook;
import chess.domain.piece.singlestep.Knight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static chess.domain.piece.EmptyPiece.EMPTY_PIECE;
import static chess.domain.piece.multistep.Queen.BLACK_QUEEN;
import static chess.domain.piece.multistep.Rook.BLACK_ROOK;
import static chess.domain.piece.multistep.Rook.WHITE_ROOK;
import static chess.domain.piece.pawn.BlackPawn.BLACK_PAWN;
import static chess.domain.piece.pawn.WhitePawn.WHITE_PAWN;
import static chess.domain.piece.singlestep.King.BLACK_KING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ChessBoardTest {

    @Test
    @DisplayName("생성 테스트")
    void create() {
        assertThatCode(ChessBoardFactory::makeChessBoard)
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("대상 위치에 존재하는 기물을 타켓 위치로 옮긴다.")
    void movePiece() {
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        Position source = Position.of(File.B, Rank.TWO);
        Position target = Position.of(File.B, Rank.THREE);
        Piece pieceBeforeMove = chessBoard.findPieceByPosition(source);

        chessBoard.move(source, target);

        Piece pieceAfterMove = chessBoard.findPieceByPosition(target);
        assertThat(pieceBeforeMove).isEqualTo(pieceAfterMove);
    }

    @Test
    @DisplayName("기물이 이동하면 기존 위치에는 기물이 존재하지 않게 된다.")
    void noPieceOnSourcePositionWhenPieceMoves() {
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        Position source = Position.of(File.B, Rank.TWO);
        Position target = Position.of(File.B, Rank.THREE);

        chessBoard.move(source, target);

        Piece pieceAtSourcePositionAfterMove = chessBoard.findPieceByPosition(source);
        assertThat(pieceAtSourcePositionAfterMove).isEqualTo(EMPTY_PIECE);
    }

    @DisplayName("제자리로 이동하려는 경우 예외가 발생한다.")
    @Test
    void canNotMoveToSamePosition() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.B, Rank.TWO);
        positionPiece.put(sourcePosition, BLACK_PAWN);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, sourcePosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제자리로 움직일 수 없습니다.");
    }

    @DisplayName("비어있는 칸에서 이동하려는 경우 예외가 발생한다.")
    @Test
    void canNotMoveAtEmptyPosition() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        positionPiece.put(sourcePosition, EMPTY_PIECE);
        Position targetPosition = Position.of(File.A, Rank.TWO);
        positionPiece.put(targetPosition, BLACK_PAWN);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비어있는 곳에서는 기물을 움직일 수 없습니다.");
    }

    @Test
    @DisplayName("이동하려는 지점에 같은 팀이 존재하는 경우 예외가 발생한다")
    void cannotMoveIfAllyAlreadyExist() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        positionPiece.put(sourcePosition, BLACK_KING);
        Position targetPosition = Position.of(File.A, Rank.TWO);
        positionPiece.put(targetPosition, BLACK_PAWN);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 아군 기물이 존재합니다.");
    }

    @Test
    @DisplayName("기물이 이동했음에도 타겟 위치까지 도달하지 못하는 경우 예외가 발생한다.")
    void cannotReachTarget() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        positionPiece.put(sourcePosition, BLACK_KING);
        Position targetPosition = Position.of(File.A, Rank.THREE);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 기물이 있는 경우 움직일 수 없다.")
    void obstacleExistOnRoute() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        positionPiece.put(sourcePosition, BLACK_ROOK);
        Position obstaclePosition = Position.of(File.A, Rank.TWO);
        positionPiece.put(obstaclePosition, BLACK_PAWN);
        Position targetPosition = Position.of(File.A, Rank.THREE);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 기물은 해당 위치에 도달할 수 없습니다.");
    }

    @Test
    @DisplayName("나이트는 기물을 넘어 이동할 수 있다.")
    void knightCanJumpOverPiece() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        positionPiece.put(sourcePosition, Knight.BLACK_KNIGHT);
        Position obstaclePosition = Position.of(File.A, Rank.TWO);
        positionPiece.put(obstaclePosition, BLACK_PAWN);
        Position targetPosition = Position.of(File.B, Rank.THREE);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("선택한 기물이 이동할 수 없는 방향으로 이동을 요청하면 예외가 발생한다.")
    void invalidDirection() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        positionPiece.put(sourcePosition, BLACK_ROOK);
        Position targetPosition = Position.of(File.C, Rank.THREE);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("target 지점에 적팀 기물이 존재하는 경우, 덮어씌운다")
    void kill() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.ONE);
        Rook sourcePiece = BLACK_ROOK;
        positionPiece.put(sourcePosition, sourcePiece);
        Position targetPosition = Position.of(File.A, Rank.THREE);
        Rook targetPiece = WHITE_ROOK;
        positionPiece.put(targetPosition, targetPiece);
        positionPiece.put(Position.of(File.A, Rank.TWO), EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        chessBoard.move(sourcePosition, targetPosition);

        Piece pieceAtTargetPosition = chessBoard.findPieceByPosition(targetPosition);
        assertThat(pieceAtTargetPosition).isEqualTo(sourcePiece);
    }

    @Test
    @DisplayName("체스판의 모서리까지 이동할 수 있다.")
    void fromEdgeToEdge() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.SEVEN);
        positionPiece.put(sourcePosition, BLACK_QUEEN);
        Position targetPosition = Position.of(File.A, Rank.EIGHT);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("폰은 최초의 이동에서 두칸을 이동할 수 있다.")
    void pawnCanMoveTwiceIfFirstMove() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.SEVEN);
        positionPiece.put(sourcePosition, BLACK_PAWN);
        Position blankPosition = Position.of(File.A, Rank.SIX);
        positionPiece.put(blankPosition, EMPTY_PIECE);
        Position targetPosition = Position.of(File.A, Rank.FIVE);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("폰은 적이 있다면, 대각선 앞으로 이동할 수 있다.")
    void pawnCanMoveDiagonalIfEnemyThere() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.SEVEN);
        positionPiece.put(sourcePosition, BLACK_PAWN);
        Position targetPosition = Position.of(File.B, Rank.SIX);
        positionPiece.put(targetPosition, WHITE_PAWN);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("폰이 시작 위치에 있지 않은데 두 칸을 이동하려 하면, 예외가 발생한다.")
    @Test
    void pawnCanNotMoveTwiceIfItIsNotOnStartPositions() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.THREE);
        positionPiece.put(sourcePosition, WHITE_PAWN);
        Position targetPosition = Position.of(File.A, Rank.FIVE);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 위치로 이동할 수 없습니다.");
    }

    @DisplayName("폰의 대각선에 적이 없음에도 대각선으로 이동하려 하면, 예외가 발생한다.")
    @Test
    void pawnCanNotMoveDiagonalIfThereIsNoEnemy() {
        Map<Position, Piece> positionPiece = new LinkedHashMap<>();
        Position sourcePosition = Position.of(File.A, Rank.SEVEN);
        positionPiece.put(sourcePosition, BLACK_PAWN);
        Position targetPosition = Position.of(File.B, Rank.SIX);
        positionPiece.put(targetPosition, EMPTY_PIECE);
        ChessBoard chessBoard = new ChessBoard(positionPiece);

        assertThatCode(() -> chessBoard.move(sourcePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 위치로 이동할 수 없습니다.");
    }
}

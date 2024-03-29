package chess.view;

import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardDtoTest {

    @Test
    @DisplayName("현재 체스판의 상태를 문자열로 반환한다.")
    void toChessBoardExpression() {
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();

        ChessBoardDto chessBoardDto = ChessBoardDto.from(chessBoard);
        String actualExpression = chessBoardDto.chessBoardExpression();
        String expectedExpression =
                "RNBQKBNR" + System.lineSeparator()
                        + "PPPPPPPP" + System.lineSeparator()
                        + "........" + System.lineSeparator()
                        + "........" + System.lineSeparator()
                        + "........" + System.lineSeparator()
                        + "........" + System.lineSeparator()
                        + "pppppppp" + System.lineSeparator()
                        + "rnbqkbnr" + System.lineSeparator();

        Assertions.assertThat(actualExpression).isEqualTo(expectedExpression);
    }
}

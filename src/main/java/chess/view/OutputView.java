package chess.view;

import chess.domain.piece.Color;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR]";
    private static final String WHITE_WIN_EXPRESSION = "백팀";
    private static final String BLACK_WIN_EXPRESSION = "흑팀";

    public void printCommandInformation() {
        System.out.println("> 체스 게임을 시작합니다.\n"
                + "> 게임 시작 : start\n"
                + "> 게임 종료 : end\n"
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printChessBoard(final ChessBoardDto chessBoardDto) {
        String chessBoardExpression = chessBoardDto.chessBoardExpression();
        System.out.println("\n" + chessBoardExpression);
    }

    public void printErrorMessage(final String message) {
        System.out.printf("%s %s%n", ERROR_PREFIX, message);
    }

    public void printEachScore(final ScoreDto scoreDto) {
        System.out.printf("백팀의 점수 : %s, 흑팀의 점수 : %s%n", scoreDto.whiteScore(), scoreDto.blackScore());
        System.out.printf("앞서고 있는 팀 : %s%n", scoreDto.winningColor());
    }

    public void printGameEnd(Color currentRoundColor) {
        String winnerExpression = "";
        if (currentRoundColor.isWhite()) {
            winnerExpression = WHITE_WIN_EXPRESSION;
        }
        if (currentRoundColor.isBlack()) {
            winnerExpression = BLACK_WIN_EXPRESSION;
        }

        System.out.printf("게임이 종료되었습니다. %s이 이겼습니다.", winnerExpression);
    }
}

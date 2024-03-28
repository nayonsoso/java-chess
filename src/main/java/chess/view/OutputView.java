package chess.view;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR]";

    public void printCommandInformation() {
        System.out.println("> 체스 게임을 시작합니다.\n"
                + "> 게임 시작 : start\n"
                + "> 게임 종료 : end\n"
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printChessBoard(final ChessBoardDto chessBoardDto) {
        String chessBoardExpression = chessBoardDto.chessBoardExpression();
        System.out.println(chessBoardExpression);
    }

    public void printErrorMessage(final String message) {
        System.out.printf("%s %s%n", ERROR_PREFIX, message);
    }

    public void printEachScore(final ScoreDto scoreDto) {
        System.out.printf("백팀의 점수 : %s, 흑팀의 점수 : %s%n", scoreDto.whiteScore(), scoreDto.blackScore());
        System.out.printf("앞서고 있는 팀 : %s%n", scoreDto.winningColor());
    }
}

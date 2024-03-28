package chess.view;

public record ScoreDto(String whiteScore, String blackScore, String winningColor) {

    private static final String WHITE_WIN_EXPRESSION = "백팀";
    private static final String BLACK_WIN_EXPRESSION = "흑팀";
    private static final String TIE_EXPRESSION = "없음";

    public static ScoreDto of(double whiteScore, double blackScore) {
        String whiteScoreExpression = String.format("%.1f", whiteScore);
        String blackScoreExpression = String.format("%.1f", blackScore);
        String winningColor = getWinningColor(whiteScore, blackScore);

        return new ScoreDto(whiteScoreExpression, blackScoreExpression, winningColor);
    }

    private static String getWinningColor(double whiteScore, double blackScore) {
        if (whiteScore > blackScore) {
            return WHITE_WIN_EXPRESSION;
        }
        if (whiteScore < blackScore) {
            return BLACK_WIN_EXPRESSION;
        }

        return TIE_EXPRESSION;
    }
}

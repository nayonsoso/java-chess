package chess.domain;

import chess.domain.piece.Pieces;
import java.util.Objects;

public class Score {

    private final double value;

    private Score(final double value) {
        this.value = value;
    }

    public static Score from(final double value) {
        return new Score(value);
    }

    public static Score subtractTotalScoreFromPawnPerScore(final Pieces pieces) {
        long pawnSameFileCount = pieces.countPawnPerFile();

        double value = pawnSameFileCount * 0.5;
        double totalScore = pieces.getTotalScore();

        return new Score(totalScore - value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return value == score.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}

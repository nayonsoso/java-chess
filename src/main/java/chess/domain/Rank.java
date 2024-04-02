package chess.domain;

import java.util.Arrays;

public enum Rank {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    ;

    private final int value;

    Rank(final int value) {
        this.value = value;
    }

    public static Rank from(final int value) {
        return Arrays.stream(values())
                .filter(rank -> rank.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("체스판 세로 범위를 넘어갔습니다."));
    }

    public Rank moveByOffset(final int offset) {
        return from(this.value + offset);
    }

    public int calculateDifference(final Rank rank) {
        return this.value - rank.value;
    }
}

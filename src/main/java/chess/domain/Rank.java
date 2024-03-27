package chess.domain;

import java.util.Arrays;

public enum Rank {

    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6),
    EIGHT(7),
    ;

    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public static Rank findByValue(int value) {
        return Arrays.stream(values())
                .filter(rank -> rank.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("체스판 세로 범위를 넘어갔습니다."));
    }

    public Rank moveByOffset(int offset) {
        return findByValue(this.value + offset);
    }

    public int calculateDifference(Rank rank) {
        return this.ordinal() - rank.ordinal();
    }
}

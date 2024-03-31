package chess.domain;

import java.util.Arrays;

public enum File {

    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7),
    ;

    private final int value;

    File(final int value) {
        this.value = value;
    }

    public static File findByValue(final int value) {
        return Arrays.stream(values())
                .filter(file -> file.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("체스판 가로 범위를 넘어갔습니다."));
    }

    public File moveByOffset(final int offset) {
        return findByValue(this.value + offset);
    }

    public int calculateDifference(final File file) {
        return this.value - file.value;
    }
}

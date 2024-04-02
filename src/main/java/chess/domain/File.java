package chess.domain;

import java.util.Arrays;

public enum File {

    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    H('h'),
    ;

    private final char value;

    File(final char value) {
        this.value = value;
    }

    public static File from(final char value) {
        return Arrays.stream(values())
                .filter(file -> file.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("체스판 가로 범위를 넘어갔습니다."));
    }

    public File moveByOffset(final int offset) {
        char changedValue = (char) (this.value + offset);

        return from(changedValue);
    }

    public int calculateDifference(final File file) {
        return this.value - file.value;
    }
}

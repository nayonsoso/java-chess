package chess.domain;

public enum GameStatus {

    END,
    NOT_END,
    ;

    public boolean isEnd() {
        return this == END;
    }

    public boolean isNotEnd() {
        return !isEnd();
    }
}

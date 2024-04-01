package chess.dao;

public enum StatusType {

    END,
    CONTINUE,
    ;

    public boolean isEnd() {
        return this == END;
    }
}

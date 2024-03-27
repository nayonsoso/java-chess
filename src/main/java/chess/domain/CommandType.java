package chess.domain;

public enum CommandType {

    START(0),
    MOVE(2),
    END(0),
    ;

    private final int argumentCount;

    CommandType(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public boolean isArgumentCountSameAs(int argumentCount) {
        return this.argumentCount == argumentCount;
    }
}

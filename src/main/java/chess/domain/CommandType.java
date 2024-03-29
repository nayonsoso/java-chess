package chess.domain;

public enum CommandType {

    START(0),
    MOVE(2),
    END(0),
    STATUS(0),
    ;

    private final int argumentCount;

    CommandType(final int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public boolean isArgumentCountSameAs(final int argumentCount) {
        return this.argumentCount == argumentCount;
    }
}

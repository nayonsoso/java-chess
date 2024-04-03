package chess.domain;

import java.util.Arrays;

public enum CommandType {

    START("start", 0),
    MOVE("move", 2),
    END("end", 0),
    STATUS("status", 0),
    ;

    private final String expression;
    private final int argumentCount;

    CommandType(final String expression, final int argumentCount) {
        this.expression = expression;
        this.argumentCount = argumentCount;
    }

    public static CommandType findByExpression(String input) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.expression.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해석할 수 없는 명령어입니다."));
    }

    public boolean isArgumentCountSameAs(final int argumentCount) {
        return this.argumentCount == argumentCount;
    }
}

package chess.view;

import java.util.Arrays;

public enum Command {
    START,
    END,
    MOVE;

    public static Command from(final String input) {
        return Arrays.stream(Command.values())
                .filter(command -> command.name().equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 명령어입니다."));
    }
}
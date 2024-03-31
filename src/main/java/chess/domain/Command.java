package chess.domain;

import chess.view.CommandDto;

import java.util.List;

public class Command {

    public static final Command END_COMMAND = new Command(CommandType.END, null, null);

    private final CommandType commandType;
    private final Position sourcePosition;
    private final Position targetPosition;

    private Command(final CommandType commandType, final Position sourcePosition, final Position targetPosition) {
        this.commandType = commandType;
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    public static Command from(final CommandDto commandDto) {
        CommandType commandType = commandDto.commandType();
        List<Position> arguments = commandDto.arguments();
        validateCommandArgumentsSize(commandType, arguments);
        if (commandType == CommandType.MOVE) {
            Position sourcePosition = arguments.get(0);
            Position targetPosition = arguments.get(1);
            return new Command(commandType, sourcePosition, targetPosition);
        }

        return new Command(commandType, null, null);
    }

    private static void validateCommandArgumentsSize(final CommandType commandType, final List<Position> arguments) {
        if (!commandType.isArgumentCountSameAs(arguments.size())) {
            throw new IllegalArgumentException("명령어에 맞는 인자의 갯수가 아닙니다.");
        }
    }

    public boolean matchesType(CommandType commandType) {
        return this.commandType == commandType;
    }

    public Position getSourcePosition() {
        return this.sourcePosition;
    }

    public Position getTargetPosition() {
        return this.targetPosition;
    }
}

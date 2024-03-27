package chess.domain;

import chess.view.CommandDto;

import java.util.List;

public class Command {

    private final CommandType commandType;
    private final List<Position> positions;

    private Command(CommandType commandType, List<Position> positions) {
        this.commandType = commandType;
        this.positions = positions;
    }

    public static Command of(CommandDto commandDto) {
        CommandType commandType = commandDto.commandType();
        List<Position> arguments = commandDto.arguments();
        validateCommandArgumentsSize(commandType, arguments);

        return new Command(commandType, arguments);
    }

    private static void validateCommandArgumentsSize(CommandType commandType, List<Position> arguments) {
        if (!commandType.isArgumentCountSameAs(arguments.size())) {
            throw new IllegalArgumentException("명령어에 맞는 인자의 갯수가 아닙니다.");
        }
    }

    public boolean isStart() {
        return this.commandType == CommandType.START;
    }

    public boolean isMove() {
        return this.commandType == CommandType.MOVE;
    }

    public boolean isEnd() {
        return this.commandType == CommandType.END;
    }

    public Position getSourcePosition() {
        return this.positions.get(0);
    }

    public Position getTargetPosition() {
        return this.positions.get(1);
    }
}

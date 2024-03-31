package chess.view;

import chess.domain.CommandType;
import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CommandDto(CommandType commandType, List<Position> arguments) {

    private static final String START_EXPRESSION = "start";
    private static final String END_EXPRESSION = "end";
    private static final String MOVE_EXPRESSION = "move";
    private static final String STATUS_EXPRESSION = "status";
    private static final Pattern POSITION_PATTERN = Pattern.compile("^[a-h][1-8]$");
    public static final int COMMAND_INDEX = 0;

    public static CommandDto from(final String rawInput) {
        List<String> parsedInput = Arrays.stream(rawInput.split(" ")).toList();

        CommandType commandType = computeCommandType(parsedInput);
        List<Position> positions = computePositions(parsedInput);

        return new CommandDto(commandType, positions);
    }

    private static CommandType computeCommandType(final List<String> parsedInput) {
        String command = parsedInput.get(COMMAND_INDEX);
        validateCommandExpression(command);

        return parseToCommandType(command);
    }

    private static void validateCommandExpression(final String rawInput) {
        if (!(START_EXPRESSION.equals(rawInput) ||
                END_EXPRESSION.equals(rawInput) ||
                MOVE_EXPRESSION.equals(rawInput) ||
                STATUS_EXPRESSION.equals(rawInput))) {
            throw new IllegalArgumentException("start, move, status, end만 입력할 수 있습니다.");
        }
    }

    private static CommandType parseToCommandType(final String rawInput) {
        if (START_EXPRESSION.equals(rawInput)) {
            return CommandType.START;
        }
        if (MOVE_EXPRESSION.equals(rawInput)) {
            return CommandType.MOVE;
        }
        if (STATUS_EXPRESSION.equals(rawInput)) {
            return CommandType.STATUS;
        }
        return CommandType.END;
    }

    private static List<Position> computePositions(final List<String> parsedInput) {
        List<String> arguments = parsedInput.subList(1, parsedInput.size());
        arguments.forEach(CommandDto::validatePositionFormat);

        return arguments.stream()
                .map(CommandDto::parseToPosition)
                .toList();
    }

    private static void validatePositionFormat(final String positionInput) {
        Matcher matcher = POSITION_PATTERN.matcher(positionInput);
        boolean matches = matcher.matches();

        if (!matches) {
            throw new IllegalArgumentException("올바르지 않은 형식의 위치 입력입니다.");
        }
    }

    private static Position parseToPosition(final String rawInput) {
        int fileIdx = rawInput.substring(0, 1).charAt(0) - 'a';
        int rankIdx = Integer.parseInt(rawInput.substring(1, 2)) - 1;
        File file = File.findByValue(fileIdx);
        Rank rank = Rank.findByValue(rankIdx);

        return Position.of(file, rank);
    }
}

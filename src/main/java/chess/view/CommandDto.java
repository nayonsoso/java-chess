package chess.view;

import chess.domain.CommandType;
import chess.domain.Position;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CommandDto(CommandType commandType, List<Position> arguments) {

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

        return CommandType.findByExpression(command);
    }

    private static List<Position> computePositions(final List<String> parsedInput) {
        List<String> arguments = parsedInput.subList(1, parsedInput.size());
        arguments.forEach(CommandDto::validatePositionFormat);

        return arguments.stream()
                .map(Position::of)
                .toList();
    }

    private static void validatePositionFormat(final String positionInput) {
        Matcher matcher = POSITION_PATTERN.matcher(positionInput);
        boolean matches = matcher.matches();

        if (!matches) {
            throw new IllegalArgumentException("올바르지 않은 형식의 위치 입력입니다.");
        }
    }
}

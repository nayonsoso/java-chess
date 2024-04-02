package chess.view;

import chess.domain.Command;
import chess.domain.CommandType;
import chess.domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CommandTest {

    @ParameterizedTest
    @DisplayName("star, move, status, end 명령인지를 반환한다.")
    @MethodSource("makeCommand")
    void checkCommand(CommandDto commandDto, boolean expectedIsStart, boolean expectedIsMove,
                      boolean expectedIsStatus, boolean expectedIsEnd) {
        Command command = Command.from(commandDto);

        boolean actualIsStart = command.matchesType(CommandType.START);
        boolean actualIsMove = command.matchesType(CommandType.MOVE);
        boolean actualIsStatus = command.matchesType(CommandType.STATUS);
        boolean actualIsEnd = command.matchesType(CommandType.END);

        Assertions.assertAll(
                () -> assertThat(actualIsStart).isEqualTo(expectedIsStart),
                () -> assertThat(actualIsMove).isEqualTo(expectedIsMove),
                () -> assertThat(actualIsStatus).isEqualTo(expectedIsStatus),
                () -> assertThat(actualIsEnd).isEqualTo(expectedIsEnd)
        );
    }

    static Stream<Arguments> makeCommand() {
        return Stream.of(
                Arguments.of(CommandDto.from("start"), true, false, false, false),
                Arguments.of(CommandDto.from("move a1 a2"), false, true, false, false),
                Arguments.of(CommandDto.from("status"), false, false, true, false),
                Arguments.of(CommandDto.from("end"), false, false, false, true)
        );
    }

    @Test
    @DisplayName("이동 명령어의 경우, 시작 위치와 도착 위치를 반환한다.")
    void mapStart() {
        CommandDto commandDto = CommandDto.from("move a1 a2");
        Command command = Command.from(commandDto);

        Position actualSourcePosition = command.getSourcePosition();
        Position actualTargetPosition = command.getTargetPosition();
        Position expectedSourcePosition = Position.of("a1");
        Position expectedTargetPosition = Position.of("a2");

        Assertions.assertAll(
                () -> assertThat(actualSourcePosition).isEqualTo(expectedSourcePosition),
                () -> assertThat(actualTargetPosition).isEqualTo(expectedTargetPosition)
        );
    }

    @ParameterizedTest
    @DisplayName("명령어에 해당하는 인자의 갯수가 아니면 예외를 발생시킨다.")
    @MethodSource("makeWrongCommand")
    void validateCommandArgumentsSizeFail(CommandDto commandDto) {
        assertThatCode(() -> Command.from(commandDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("명령어에 맞는 인자의 갯수가 아닙니다.");
    }

    static Stream<CommandDto> makeWrongCommand() {
        return Stream.of(
                CommandDto.from("start a1"),
                CommandDto.from("end a2"),
                CommandDto.from("move a1"),
                CommandDto.from("move a1 a2 a3"),
                CommandDto.from("status a1")
        );
    }
}

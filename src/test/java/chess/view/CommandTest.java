package chess.view;

import chess.domain.Command;
import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;
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
    @DisplayName("star, move, end 명령인지를 반환한다.")
    @MethodSource("makeCommand")
    void checkCommand(CommandDto commandDto, boolean expectedIsStart, boolean expectedIsMove, boolean expectedIsEnd) {
        Command command = Command.of(commandDto);

        boolean actualIsStart = command.isStart();
        boolean actualIsMove = command.isMove();
        boolean actualIsEnd = command.isEnd();

        Assertions.assertAll(
                () -> assertThat(actualIsStart).isEqualTo(expectedIsStart),
                () -> assertThat(actualIsMove).isEqualTo(expectedIsMove),
                () -> assertThat(actualIsEnd).isEqualTo(expectedIsEnd)
        );
    }

    static Stream<Arguments> makeCommand() {
        return Stream.of(
                Arguments.of(CommandDto.of("start"), true, false, false),
                Arguments.of(CommandDto.of("move a1 a2"), false, true, false),
                Arguments.of(CommandDto.of("end"), false, false, true));
    }

    @Test
    @DisplayName("이동 명령어의 경우, 시작 위치와 도착 위치를 반환한다.")
    void mapStart() {
        CommandDto commandDto = CommandDto.of("move a1 a2");
        Command command = Command.of(commandDto);

        Position actualSourcePosition = command.getSourcePosition();
        Position actualTargetPosition = command.getTargetPosition();
        Position expectedSourcePosition = Position.of(File.A, Rank.ONE);
        Position expectedTargetPosition = Position.of(File.A, Rank.TWO);

        Assertions.assertAll(
                () -> assertThat(actualSourcePosition).isEqualTo(expectedSourcePosition),
                () -> assertThat(actualTargetPosition).isEqualTo(expectedTargetPosition)
        );
    }

    @ParameterizedTest
    @DisplayName("명령어에 해당하는 인자의 갯수가 아니면 예외를 발생시킨다.")
    @MethodSource("makeWrongCommand")
    void validateCommandArgumentsSizeFail(CommandDto commandDto) {
        assertThatCode(() -> Command.of(commandDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("명령어에 맞는 인자의 갯수가 아닙니다.");
    }

    static Stream<CommandDto> makeWrongCommand() {
        return Stream.of(
                CommandDto.of("start a1"),
                CommandDto.of("end a2"),
                CommandDto.of("move a1"),
                CommandDto.of("move a1 a2 a3")
        );
    }
}

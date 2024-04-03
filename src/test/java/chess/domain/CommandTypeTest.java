package chess.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTypeTest {

    @ParameterizedTest
    @DisplayName("허용하는 명령어의 형식인지 검증한다.")
    @CsvSource(value = {"start:START", "move:MOVE", "status:STATUS", "end:END"}, delimiter = ':')
    void validateCommandExpression(String command, CommandType expectedCommandType) {
        CommandType actualCommandType = CommandType.findByExpression(command);

        assertThat(actualCommandType).isEqualTo(expectedCommandType);
    }

    @Test
    @DisplayName("허용하는 명령어가 아니면 예외가 발생한다.")
    void validateCommandExpressionFail() {
        String input = "invalidCommand";

        assertThatThrownBy(() -> CommandType.findByExpression(input))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("해석할 수 없는 명령어입니다.");
    }
}

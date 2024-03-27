package chess.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandDtoTest {

    @ParameterizedTest
    @DisplayName("생성 테스트")
    @ValueSource(strings = {"start", "move a2 a3", "end"})
    void create(String command) {
        assertThatCode(() -> CommandDto.of(command))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("허용하는 명령어가 아니면 예외가 발생한다.")
    void validateCommandExpressionFail() {
        String input = "invalidCommand";

        assertThatThrownBy(() -> CommandDto.of(input))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("start, move, end만 입력할 수 있습니다.");
    }

    @ParameterizedTest
    @DisplayName("올바르지 않은 형식의 위치이면 예외를 발생시킨다.")
    @ValueSource(strings = {"move a1 a0", "move a1 a9", "move a1 i1", "move a1 i8"})
    void validatePositionFormatFail(String input) {
        assertThatCode(() -> CommandDto.of(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 형식의 위치 입력입니다.");
    }
}

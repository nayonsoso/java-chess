package chess.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;

class CommandDtoTest {

    @ParameterizedTest
    @DisplayName("올바르지 않은 형식의 위치이면 예외를 발생시킨다.")
    @ValueSource(strings = {"move a1 a0", "move a1 a9", "move a1 i1", "move a1 i8"})
    void validatePositionFormatFail(String input) {
        assertThatCode(() -> CommandDto.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 형식의 위치 입력입니다.");
    }
}

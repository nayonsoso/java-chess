package chess.dao;

import chess.domain.File;
import chess.domain.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class EnumMapperTest {

    @DisplayName("문자열을 열거형으로 매핑한다.")
    @Test
    void mapToEnum() {
        File file = EnumMapper.mapToEnum(File.class, "A");

        assertThat(file).isEqualTo(File.A);
    }

    @DisplayName("열거형을 문자열로 매핑한다.")
    @Test
    void mapToString() {
        String name = EnumMapper.mapToString(Rank.ONE);

        assertThat(name).isEqualTo("ONE");
    }

    @DisplayName("문자열을 열거형으로 매핑할 수 없으면 예외를 발생시킨다.")
    @Test
    void mapToEnumFail() {
        assertThatCode(() -> EnumMapper.mapToEnum(File.class, "a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("File로 매핑할 수 없습니다.");
    }
}

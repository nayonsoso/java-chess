package chess.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class RankTest {

    @Nested
    @DisplayName("주어진 수만큼 이동한 랭크을 반환한다.")
    class moveByOffset {

        @ParameterizedTest
        @DisplayName("범위를 넘지 않도록 이동한 랭크을 반환한다.")
        @CsvSource(value = {"ONE:1:TWO", "TWO:-1:ONE"}, delimiter = ':')
        void moveByOffset(Rank before, int offset, Rank expectedAfter) {
            Rank actualAfter = before.moveByOffset(offset);

            assertThat(actualAfter).isEqualTo(expectedAfter);
        }

        @ParameterizedTest
        @DisplayName("범위를 넘으면 예외가 발생한다.")
        @CsvSource(value = {"ONE:-1", "EIGHT:1"}, delimiter = ':')
        void moveByOffsetFail(Rank rank, int offset) {
            assertThatCode(() -> rank.moveByOffset(offset))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("체스판 세로 범위를 넘어갔습니다.");
        }
    }

    @DisplayName("값을 받고, 대응하는 랭크를 찾아 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1:ONE", "8:EIGHT"}, delimiter = ':')
    void findByValue(int value, Rank expectedRank) {
        Rank actualRank = Rank.from(value);

        assertThat(actualRank).isEqualTo(expectedRank);
    }
}

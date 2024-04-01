package chess.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class StatusTypeTest {

    @DisplayName("게임이 끝났는지 반환한다.")
    @Test
    void isEnd() {
        boolean isEnd1 = StatusType.CONTINUE.isEnd();
        boolean isEnd2 = StatusType.END.isEnd();

        assertAll(
                () -> assertThat(isEnd1).isFalse(),
                () -> assertThat(isEnd2).isTrue()
        );
    }
}

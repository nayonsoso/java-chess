package chess.domain;


import chess.domain.piece.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DirectionTest {

    @ParameterizedTest
    @DisplayName("Direction 방향으로 이동한 X 좌표와 Y 좌표를 계산한다.")
    @CsvSource(value = {
            "LEFT,B,THREE", "RIGHT,D,THREE", "UP,C,FOUR", "DOWN,C,TWO",
            "LEFT_UP,B,FOUR", "LEFT_DOWN,B,TWO", "RIGHT_UP,D,FOUR", "RIGHT_DOWN,D,TWO",
            "LEFT_LEFT_UP,A,FOUR", "LEFT_LEFT_DOWN,A,TWO",
            "RIGHT_RIGHT_UP,E,FOUR", "RIGHT_RIGHT_DOWN,E,TWO",
            "LEFT_UP_UP,B,FIVE", "RIGHT_UP_UP,D,FIVE",
            "LEFT_DOWN_DOWN,B,ONE", "RIGHT_DOWN_DOWN,D,ONE"
    })
    void calculateNewCoordinates(Direction direction, File expectedFile, Rank expectedRank) {
        File currentFile = File.C;
        Rank currentRank = Rank.THREE;

        File nextFile = direction.moveFile(currentFile);
        Rank nextRank = direction.moveRank(currentRank);

        assertThat(nextFile).isEqualTo(expectedFile);
        assertThat(nextRank).isEqualTo(expectedRank);
    }

    @ParameterizedTest
    @DisplayName("주어진 좌표 변화에 대한 방향을 찾을 수 있다.")
    @CsvSource(value = {
            "-1,0,LEFT", "1,0,RIGHT", "0,1,UP", "0,-1,DOWN",
            "-1,1,LEFT_UP", "-1,-1,LEFT_DOWN", "1,1,RIGHT_UP", "1,-1,RIGHT_DOWN",
            "-2,1,LEFT_LEFT_UP", "-2,-1,LEFT_LEFT_DOWN",
            "2,1,RIGHT_RIGHT_UP", "2,-1,RIGHT_RIGHT_DOWN",
            "-1,2,LEFT_UP_UP", "1,2,RIGHT_UP_UP",
            "-1,-2,LEFT_DOWN_DOWN", "1,-2,RIGHT_DOWN_DOWN"
    })
    void findDirectionByDelta(int deltaX, int deltaY, Direction expectedDirection) {
        Direction foundDirection = Direction.find(deltaX, deltaY);

        assertThat(foundDirection).isEqualTo(expectedDirection);
    }

    @Test
    @DisplayName("주어진 좌표에 대응하는 방향이 없으면 예외를 발생시킨다.")
    void findDirectionByDeltaFail() {
        assertThatThrownBy(() -> Direction.find(2, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 이동 방향입니다.");
    }
}
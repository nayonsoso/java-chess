package chess.fixture;

import chess.domain.Position;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class SourceTargetPositions {

    public static Stream<Arguments> verticalAndHorizon() {
        return Stream.of(
                Arguments.of(Position.of("d4"), Position.of("d5")),
                Arguments.of(Position.of("d4"), Position.of("d3")),
                Arguments.of(Position.of("d4"), Position.of("c4")),
                Arguments.of(Position.of("d4"), Position.of("e4"))
        );
    }

    public static Stream<Arguments> diagonal() {
        return Stream.of(
                Arguments.of(Position.of("d4"), Position.of("e5")),
                Arguments.of(Position.of("d4"), Position.of("e3")),
                Arguments.of(Position.of("d4"), Position.of("c5")),
                Arguments.of(Position.of("d4"), Position.of("c3"))
        );
    }

    public static Stream<Arguments> lShape() {
        return Stream.of(
                Arguments.of(Position.of("d4"), Position.of("b3")),
                Arguments.of(Position.of("d4"), Position.of("b5")),
                Arguments.of(Position.of("d4"), Position.of("c2")),
                Arguments.of(Position.of("d4"), Position.of("c6")),
                Arguments.of(Position.of("d4"), Position.of("e2")),
                Arguments.of(Position.of("d4"), Position.of("e6")),
                Arguments.of(Position.of("d4"), Position.of("f3")),
                Arguments.of(Position.of("d4"), Position.of("f5"))
        );
    }
}

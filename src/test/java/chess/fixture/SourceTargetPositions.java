package chess.fixture;

import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class SourceTargetPositions {

    public static Stream<Arguments> verticalAndHorizon() {
        return Stream.of(
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.D, Rank.FIVE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.D, Rank.THREE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.FOUR)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.FOUR))
        );
    }

    public static Stream<Arguments> diagonal() {
        return Stream.of(
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.FIVE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.THREE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.FIVE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.THREE))
        );
    }

    public static Stream<Arguments> lShape() {
        return Stream.of(
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.B, Rank.THREE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.B, Rank.FIVE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.TWO)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.C, Rank.SIX)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.TWO)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.E, Rank.SIX)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.F, Rank.THREE)),
                Arguments.of(Position.of(File.D, Rank.FOUR), Position.of(File.F, Rank.FIVE))
        );
    }
}

package chess.piece;

import chess.board.Position;
import chess.fixture.FixturePosition;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PawnTest {

    @Nested
    class 흰색_폰이_움직일_때_이동방향은_ {
        @Test
        void 위로_한_칸_이동한다() {
            //given
            Pawn pawn = new WhitePawn();

            Position from = FixturePosition.B2;
            Position to = FixturePosition.B3;

            //when & then
            assertThat(pawn.isMovable(from, to, PieceFixture.EMPTY_PIECE)).isTrue();
        }

        @Test
        void 첫_위치면_두칸_위로갈_수_있다() {
            //given
            Pawn pawn = new WhitePawn();

            Position from = FixturePosition.B2;
            Position to = FixturePosition.B4;

            //then
            assertThat(pawn.isMovable(from, to, PieceFixture.EMPTY_PIECE)).isTrue();
        }

        @Test
        void 상대_말이_있을_때만_위_대각선으로_한칸_이동할_수_있다() {
            //given
            Pawn pawn = new WhitePawn();

            Position from = FixturePosition.B2;
            Position to = FixturePosition.C3;

            //when & then
            assertThat(pawn.isMovable(from, to, new BlackPawn())).isTrue();
        }

        @Test
        void 올바른_방향이_아니면_예외() {
            //given
            Pawn pawn = new WhitePawn();

            Position from = FixturePosition.B2;
            Position to = FixturePosition.C4;

            //when & then
            assertThatThrownBy(() -> pawn.isMovable(from, to, PieceFixture.EMPTY_PIECE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Pawn이 이동할 수 없는 경로입니다.");
        }
    }

    @Nested
    class 검은색_폰이_움직일_때_이동방향은_ {
        @Test
        void 아래로_한_칸_이동한다() {
            //given
            Pawn pawn = new BlackPawn();

            Position from = FixturePosition.B5;
            Position to = FixturePosition.B4;

            //when & then
            assertThat(pawn.isMovable(from, to, PieceFixture.EMPTY_PIECE)).isTrue();
        }

        @Test
        void 첫_위치면_두칸_아래로_갈_수_있다() {
            //given
            Pawn pawn = new BlackPawn();

            Position from = FixturePosition.B7;
            Position to = FixturePosition.B5;

            //when & then
            assertThat(pawn.isMovable(from, to, PieceFixture.EMPTY_PIECE)).isTrue();
        }

        @Test
        void 상대_말이_있을_때만_아래_대각선으로_한칸_이동할_수_있다() {
            //given
            Pawn pawn = new BlackPawn();

            Position from = FixturePosition.C7;
            Position to = FixturePosition.B6;

            //when & then
            assertThat(pawn.isMovable(from, to, new WhitePawn())).isTrue();
        }

        @Test
        void 올바른_방향이_아니면_예외() {
            //given
            Pawn pawn = new BlackPawn();

            Position from = FixturePosition.B2;
            Position to = FixturePosition.C4;

            //when & then
            assertThatThrownBy(() -> pawn.isMovable(from, to, PieceFixture.EMPTY_PIECE))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Pawn이 이동할 수 없는 경로입니다.");
        }
    }
}
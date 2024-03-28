package chess.domain.piece;

public enum Color {

    BLACK,
    WHITE,
    EMPTY;

    public boolean isBlack() {
        return this == BLACK;
    }

    public boolean isWhite() {
        return this == WHITE;
    }

    public Color getOppositeColor() {
        if (this.isWhite()) {
            return BLACK;
        }

        return WHITE;
    }
}

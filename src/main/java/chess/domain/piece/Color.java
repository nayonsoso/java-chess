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

    public boolean isOppositeColor(Color color) {
        if (color.isBlack()) {
            return this.isWhite();
        }
        if (color.isWhite()) {
            return this.isBlack();
        }
        return false;
    }

    public Color getOppositeColor() {
        if (this.isWhite()) {
            return BLACK;
        }

        return WHITE;
    }
}

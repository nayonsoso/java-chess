package chess.domain.piece;

public enum PieceType {

    KING(0),
    QUEEN(9),
    ROOK(5),
    BISHOP(3),
    KNIGHT(2.5),
    PAWN(1),
    REPEATED_PAWN(-0.5),
    ;

    private final double score;

    PieceType(double score) {
        this.score = score;
    }

    public double getScore() {
        return this.score;
    }
}

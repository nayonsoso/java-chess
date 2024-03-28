package chess.domain;

public enum MoveResult {

    GAME_END,
    GAME_NOT_END,
    ;

    public boolean isEnd() {
        return this == GAME_END;
    }
}

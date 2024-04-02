package chess.dao.dto;

import chess.dao.EnumMapper;
import chess.domain.GameStatus;

public record ResponseChessGameDto(
        int id,
        GameStatus gameStatus) {

    public static ResponseChessGameDto of(String id, String gameStatus) {
        int convertedId = Integer.parseInt(id);
        GameStatus convertedGameStatus = EnumMapper.mapToEnum(GameStatus.class, gameStatus);

        return new ResponseChessGameDto(convertedId, convertedGameStatus);
    }
}

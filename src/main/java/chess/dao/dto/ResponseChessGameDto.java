package chess.dao.dto;

import chess.dao.EnumMapper;
import chess.dao.StatusType;

public record ResponseChessGameDto(
        int id,
        StatusType statusType) {

    public static ResponseChessGameDto of(String id, String statusType) {
        int convertedId = Integer.parseInt(id);
        StatusType convertedStatusType = EnumMapper.mapToEnum(StatusType.class, statusType);

        return new ResponseChessGameDto(convertedId, convertedStatusType);
    }
}

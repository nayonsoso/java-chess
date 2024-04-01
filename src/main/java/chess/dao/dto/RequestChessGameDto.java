package chess.dao.dto;

import chess.dao.StatusType;

public record RequestChessGameDto(
        String id,
        String status) {

    public static RequestChessGameDto of(int id, StatusType statusType) {
        String idExpression = String.valueOf(id);
        String statusTypeExpression = EnumMapper.mapToString(statusType);

        return new RequestChessGameDto(idExpression, statusTypeExpression);
    }
}

package chess.dao.dto;

import chess.dao.EnumMapper;
import chess.domain.Position;

public record RequestMovementDto(
        String sourceFile,
        String sourceRank,
        String targetFile,
        String targetRank,
        String chessGameId) {

    public static RequestMovementDto of(Position sourcePosition, Position targetPosition, int chessGameId) {
        String sourceFile = EnumMapper.mapToString(sourcePosition.getFile());
        String sourceRank = EnumMapper.mapToString(sourcePosition.getRank());
        String targetFile = EnumMapper.mapToString(targetPosition.getFile());
        String targetRank = EnumMapper.mapToString(targetPosition.getRank());

        return new RequestMovementDto(sourceFile, sourceRank, targetFile, targetRank, String.valueOf(chessGameId));
    }
}

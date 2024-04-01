package chess.dao.dto;

import chess.dao.EnumMapper;
import chess.domain.File;
import chess.domain.Position;
import chess.domain.Rank;

public record ResponseMovementDto(
        Position sourcePosition,
        Position targetPosition,
        int chessGameId) {

    public static ResponseMovementDto of(String sourceFile, String sourceRank,
                                         String targetFile, String targetRank, String chessGameId) {
        Position sourcePosition = Position.of(
                EnumMapper.mapToEnum(File.class, sourceFile),
                EnumMapper.mapToEnum(Rank.class, sourceRank)
        );
        Position targetPosition = Position.of(
                EnumMapper.mapToEnum(File.class, targetFile),
                EnumMapper.mapToEnum(Rank.class, targetRank)
        );

        return new ResponseMovementDto(sourcePosition, targetPosition, Integer.parseInt(chessGameId));
    }
}


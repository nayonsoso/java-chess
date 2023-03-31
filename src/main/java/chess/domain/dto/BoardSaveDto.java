package chess.domain.dto;

import chess.domain.Position;
import chess.domain.board.Board;
import chess.domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardSaveDto {

    private final Map<String, HashMap<String, SavePieceDto>> data;
    private final String gameId;

    private BoardSaveDto(Map<String, HashMap<String, SavePieceDto>> data, String gameId) {
        this.data = data;
        this.gameId = gameId;
    }

    public static BoardSaveDto from(Board board, String gameId) {
        Map<Position, Piece> boardData = board.getBoardData();
        Map<String, HashMap<String, SavePieceDto>> result = new HashMap<>();
        for (Position position : boardData.keySet()) {
            HashMap<String, SavePieceDto> rankAndPieceDto = result.getOrDefault(position.getFile().name(), new HashMap<>());
            rankAndPieceDto.put(position.getRank().name(), SavePieceDto.from(boardData.get(position)));
            result.putIfAbsent(position.getFile().name(), rankAndPieceDto);
        }
        return new BoardSaveDto(result, gameId);
    }

    public Map<String, HashMap<String, SavePieceDto>> getData() {
        return data;
    }

    public String getGameId() {
        return gameId;
    }
}

package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MovementDao;
import chess.dao.StatusType;
import chess.dao.dto.RequestMovementDto;
import chess.dao.dto.ResponseChessGameDto;
import chess.dao.dto.ResponseMovementDto;
import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.ChessGame;
import chess.domain.Position;
import chess.domain.piece.Color;

import java.util.List;

public class ChessService {

    private final MovementDao movementDao;
    private final ChessGameDao chessGameDao;

    public ChessService(final ChessGameDao chessGameDao, final MovementDao movementDao) {
        this.chessGameDao = chessGameDao;
        this.movementDao = movementDao;
    }

    public ChessGame getRecentChessGame() {
        ResponseChessGameDto responseChessGameDto = chessGameDao.findRecentChessGame();
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();

        if (responseChessGameDto == null || responseChessGameDto.statusType().isEnd()) {
            chessGameDao.saveNewChessGame();
            return new ChessGame(chessBoard);
        }
        List<ResponseMovementDto> movementDtos = movementDao.findByChessGameIdOrderedByAsc(responseChessGameDto.id());

        return restoreChessGame(chessBoard, movementDtos);
    }

    private ChessGame restoreChessGame(final ChessBoard chessBoard, final List<ResponseMovementDto> movementDtos) {
        for (ResponseMovementDto movement : movementDtos) {
            Position sourcePosition = movement.sourcePosition();
            Position targetPosition = movement.targetPosition();
            chessBoard.move(sourcePosition, targetPosition);
        }

        ResponseMovementDto lastMovement = movementDtos.get(movementDtos.size() - 1);
        Position targetPosition = lastMovement.targetPosition();
        Color lastRoundColor = chessBoard.findPieceByPosition(targetPosition).getColor();

        return new ChessGame(chessBoard, lastRoundColor);
    }

    public void addMovement(final Position sourcePosition, final Position targetPosition) {
        int chessGameId = chessGameDao.findRecentChessGameId();
        RequestMovementDto requestMovementDto = RequestMovementDto.of(sourcePosition, targetPosition, chessGameId);
        movementDao.addMovement(requestMovementDto);
    }

    public void endCurrentGame() {
        int recentChessGameId = chessGameDao.findRecentChessGameId();
        chessGameDao.updateStatusType(recentChessGameId, StatusType.END);
    }
}

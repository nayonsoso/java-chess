package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.MovementDao;
import chess.dao.dto.RequestMovementDto;
import chess.dao.dto.ResponseChessGameDto;
import chess.dao.dto.ResponseMovementDto;
import chess.domain.*;
import chess.domain.piece.Color;

import java.util.List;

public class ChessGameService {

    private final MovementDao movementDao;
    private final ChessGameDao chessGameDao;

    public ChessGameService(final ChessGameDao chessGameDao, final MovementDao movementDao) {
        this.chessGameDao = chessGameDao;
        this.movementDao = movementDao;
    }

    public ChessGame getRecentChessGame() {
        ResponseChessGameDto responseChessGameDto = chessGameDao.findRecentChessGame();
        ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();

        if (responseChessGameDto == null || responseChessGameDto.gameStatus().isEnd()) {
            chessGameDao.saveNewChessGame();
            return new ChessGame(chessBoard);
        }
        List<ResponseMovementDto> movementDtos = movementDao.findByChessGameIdOrderedByAsc(responseChessGameDto.id());

        return restoreChessGame(chessBoard, movementDtos);
    }

    private ChessGame restoreChessGame(final ChessBoard chessBoard, final List<ResponseMovementDto> movementDtos) {
        if (movementDtos.isEmpty()) {
            return new ChessGame(chessBoard);
        }
        for (ResponseMovementDto movement : movementDtos) {
            chessBoard.move(movement.sourcePosition(), movement.targetPosition());
        }
        ResponseMovementDto lastMovement = movementDtos.get(movementDtos.size() - 1);
        Position targetPosition = lastMovement.targetPosition();
        Color lastRoundColor = chessBoard.findPieceByPosition(targetPosition).getColor();

        return new ChessGame(chessBoard, lastRoundColor.getOppositeColor(), GameStatus.NOT_END);
    }

    public ChessGame executeMove(final ChessGame chessGame, final Position sourcePosition, final Position targetPosition) {
        int chessGameId = chessGameDao.findRecentChessGameId();
        RequestMovementDto requestMovementDto = RequestMovementDto.of(sourcePosition, targetPosition, chessGameId);
        movementDao.addMovement(requestMovementDto);

        return chessGame.executeRound(sourcePosition, targetPosition);
    }

    public void endCurrentGame() {
        int recentChessGameId = chessGameDao.findRecentChessGameId();
        chessGameDao.updateGameStatus(recentChessGameId, GameStatus.NOT_END);
    }
}

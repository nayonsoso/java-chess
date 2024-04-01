package chess;

import chess.controller.ChessController;
import chess.dao.ChessDBConnectionManager;
import chess.dao.ChessGameDao;
import chess.dao.MovementDao;
import chess.service.ChessService;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ChessDBConnectionManager chessDBConnectionManager = new ChessDBConnectionManager();
        ChessGameDao chessGameDao = new ChessGameDao(chessDBConnectionManager.getConnection());
        MovementDao movementDao = new MovementDao(chessDBConnectionManager.getConnection());
        ChessService chessService = new ChessService(chessGameDao, movementDao);

        ChessController chessController = new ChessController(inputView, outputView, chessService);
        chessController.run();
    }
}

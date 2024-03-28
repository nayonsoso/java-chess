package chess.controller;

import chess.domain.*;
import chess.view.ChessBoardDto;
import chess.view.CommandDto;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        ChessGame chessGame = new ChessGame(chessBoard);
        outputView.printCommandInformation();
        Command command = readCommand();

        if (command.isStart()) {
            printChessBoard(chessBoard);
            startRoundWithHandleError(chessGame);
        }
    }

    private void startRoundWithHandleError(final ChessGame chessGame) {
        try {
            startRound(chessGame);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            startRoundWithHandleError(chessGame);
        }
    }

    private void startRound(final ChessGame chessGame) {
        Command command = readCommand();

        while (command.isMove()) {
            Position source = command.getSourcePosition();
            Position target = command.getTargetPosition();
            chessGame.executeRound(source, target);
            printChessBoard(chessGame.getChessBoard());
            command = readCommand();
        }

        validateStartDuplicate(command);
    }

    private Command readCommand() {
        CommandDto commandDto = inputView.readCommand();
        return Command.of(commandDto);
    }

    private void validateStartDuplicate(Command command) {
        if (command.isStart()) {
            throw new IllegalArgumentException("게임 도중에는 start 명령어를 입력할 수 없습니다.");
        }
    }

    private void printChessBoard(ChessBoard chessBoard) {
        ChessBoardDto chessBoardDto = ChessBoardDto.of(chessBoard);
        outputView.printChessBoard(chessBoardDto);
    }
}

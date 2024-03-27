package chess.controller;

import chess.domain.ChessBoard;
import chess.domain.ChessBoardFactory;
import chess.domain.Command;
import chess.domain.Position;
import chess.view.CommandDto;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.function.Consumer;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        outputView.printCommandInformation();
        Command command = readCommand();

        if (command.isStart()) {
            outputView.printChessBoard(chessBoard);
            repeat(chessBoard, this::startGame);
        }
    }

    private void startGame(final ChessBoard chessBoard) {
        Command command = readCommand();

        while (command.isMove()) {
            Position source = command.getSourcePosition();
            Position target = command.getTargetPosition();
            chessBoard.move(source, target);
            outputView.printChessBoard(chessBoard);
            command = readCommand();
        }

        validateStartDuplicate(command);
    }

    private void validateStartDuplicate(Command command) {
        if (command.isStart()) {
            throw new IllegalArgumentException("게임 도중에는 start 명령어를 입력할 수 없습니다.");
        }
    }

    private void repeat(final ChessBoard chessBoard, final Consumer<ChessBoard> consumer) {
        try {
            consumer.accept(chessBoard);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            repeat(chessBoard, consumer);
        }
    }

    private Command readCommand() {
        CommandDto commandDto = inputView.readCommand();
        return Command.of(commandDto);
    }
}

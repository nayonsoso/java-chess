package chess.controller;

import chess.domain.*;
import chess.domain.piece.Color;
import chess.view.*;

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
        Command command = readGameLifeCycleCommandWithErrorHandling();

        if (command.isStart()) {
            printChessBoard(chessBoard);
            executeGameFlowWithErrorHandling(chessGame);
        }
    }

    private Command readGameLifeCycleCommandWithErrorHandling() {
        try {
            return readGameLifeCycleCommand();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return readGameLifeCycleCommandWithErrorHandling();
        }
    }

    private Command readGameLifeCycleCommand() {
        Command command = readCommand();
        if (!(command.isStart() || command.isEnd())) {
            throw new IllegalArgumentException("start 또는 end만 입력할 수 있습니다.");
        }
        return command;
    }

    private void executeGameFlowWithErrorHandling(final ChessGame chessGame) {
        try {
            executeGameFlow(chessGame);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            executeGameFlowWithErrorHandling(chessGame);
        }
    }

    private void executeGameFlow(final ChessGame chessGame) {
        Command command = readCommandWithNoticingCurrentColor(chessGame.getCurrentRoundColor());
        validateStartDuplicate(command);

        if (command.isMove() && executeMove(chessGame, command).isEnd()) {
            return;
        }
        if (command.isStatus()) {
            printScore(chessGame);
        }
        if (command.isEnd()) {
            return;
        }
        executeGameFlow(chessGame);
    }

    private MoveResult executeMove(ChessGame chessGame, Command command) {
        Position source = command.getSourcePosition();
        Position target = command.getTargetPosition();
        MoveResult moveResult = chessGame.executeRound(source, target);
        printMoveResult(chessGame, moveResult);

        return moveResult;
    }

    private void printMoveResult(ChessGame chessGame, MoveResult moveResult) {
        if (moveResult.isEnd()) {
            outputView.printGameEnd(chessGame.getCurrentRoundColor());
        }
        if (moveResult.isNotEnd()) {
            printChessBoard(chessGame.getChessBoard());
        }
    }

    private void printScore(ChessGame chessGame) {
        double whiteScore = chessGame.calculateScore(Color.WHITE);
        double blackScore = chessGame.calculateScore(Color.BLACK);
        ScoreDto scoreDto = ScoreDto.of(whiteScore, blackScore);
        outputView.printEachScore(scoreDto);
    }

    private Command readCommand() {
        CommandDto commandDto = inputView.readCommand();
        return Command.of(commandDto);
    }

    private Command readCommandWithNoticingCurrentColor(Color color) {
        CommandDto commandDto = inputView.readCommandWithNoticingCurrentColor(color);
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

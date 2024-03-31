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
        ChessGame chessGame = initChessGame();
        Command command = readStartCommand();
        printChessBoard(chessGame.getChessBoard());

        while (!(command.isEnd() || command.isMove() && executeMove(chessGame, command).isEnd())) {
            printScore(chessGame);
            Color roundColor = chessGame.getCurrentRoundColor();
            command = readRunCommand(roundColor);
        }
    }

    private ChessGame initChessGame() {
        final ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        ChessGame chessGame = new ChessGame(chessBoard);
        outputView.printCommandInformation();

        return chessGame;
    }

    private Command readStartCommand() {
        try {
            CommandDto commandDto = inputView.readCommand();
            Command command = Command.from(commandDto);
            validateStartCommand(command);
            return command;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return readStartCommand();
        }
    }

    private void printChessBoard(final ChessBoard chessBoard) {
        ChessBoardDto chessBoardDto = ChessBoardDto.from(chessBoard);
        outputView.printChessBoard(chessBoardDto);
    }

    private void validateStartCommand(Command command) {
        if (!(command.isStart())) {
            throw new IllegalArgumentException("start만 입력할 수 있습니다.");
        }
    }

    private Command readRunCommand(Color color) {
        try {
            CommandDto commandDto = inputView.readCommand(color);
            Command command = Command.from(commandDto);
            validateStartDuplicate(command);
            return command;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return readRunCommand(color);
        }
    }

    private MoveResult executeMove(final ChessGame chessGame, final Command command) {
        Position source = command.getSourcePosition();
        Position target = command.getTargetPosition();
        MoveResult moveResult = chessGame.executeRound(source, target);
        printMoveResult(chessGame, moveResult);

        return moveResult;
    }

    private void printMoveResult(final ChessGame chessGame, final MoveResult moveResult) {
        if (moveResult.isEnd()) {
            outputView.printGameEnd(chessGame.getCurrentRoundColor());
        }
        if (moveResult.isNotEnd()) {
            printChessBoard(chessGame.getChessBoard());
        }
    }

    private void printScore(final ChessGame chessGame) {
        double whiteScore = chessGame.calculateScore(Color.WHITE);
        double blackScore = chessGame.calculateScore(Color.BLACK);
        ScoreDto scoreDto = ScoreDto.of(whiteScore, blackScore);
        outputView.printEachScore(scoreDto);
    }

    private void validateStartDuplicate(final Command command) {
        if (command.isStart()) {
            throw new IllegalArgumentException("게임 도중에는 start 명령어를 입력할 수 없습니다.");
        }
    }
}

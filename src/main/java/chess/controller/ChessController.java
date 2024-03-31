package chess.controller;

import chess.domain.*;
import chess.domain.piece.Color;
import chess.view.*;

import static chess.domain.CommandType.*;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    // 서비스 들어오기

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final ChessBoard chessBoard = ChessBoardFactory.makeChessBoard();
        ChessGame chessGame = new ChessGame(chessBoard);
        // 위의 두줄 게임 초기화로 서비스로 분리하기
        outputView.printCommandInformation();
        Command command = readStartCommand();
        printChessBoard(chessGame.getChessBoard());

        while (!command.matchesType(END)) {
            Color roundColor = chessGame.getCurrentRoundColor();
            command = runRound(chessGame, roundColor); // 이 라인 서비스 함수를 호출해서 실행하기 하기
        }
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

    private void validateStartCommand(Command command) {
        if (!(command.matchesType(START))) {
            throw new IllegalArgumentException("start만 입력할 수 있습니다.");
        }
    }

    private Command runRound(ChessGame chessGame, Color color) {
        try {
            CommandDto commandDto = inputView.readCommand(color);
            Command command = Command.from(commandDto);
            validateStartDuplicate(command);
            return executeCommand(chessGame, command);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return runRound(chessGame, color);
        }
    }

    private Command executeCommand(ChessGame chessGame, Command command) {
        if (command.matchesType(END)) {
            return command;
        }
        if (command.matchesType(MOVE) && executeMove(chessGame, command).isEnd()) {
            // 여기에서 end이면 게임의 상태 바꿔줘야 함
            return Command.END_COMMAND;
        }
        if (command.matchesType(STATUS)) {
            printScore(chessGame);
            return command;
        }
        return command;
    }

    private MoveResult executeMove(final ChessGame chessGame, final Command command) {
        Position source = command.getSourcePosition();
        Position target = command.getTargetPosition();
        MoveResult moveResult = chessGame.executeRound(source, target);
        // 여기 위에 세개 라인은 무조건 서비스에 들어가야 함
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
        if (command.matchesType(START)) {
            throw new IllegalArgumentException("게임 도중에는 start 명령어를 입력할 수 없습니다.");
        }
    }

    private void printChessBoard(final ChessBoard chessBoard) {
        ChessBoardDto chessBoardDto = ChessBoardDto.from(chessBoard);
        outputView.printChessBoard(chessBoardDto);
    }
}

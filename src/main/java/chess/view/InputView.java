package chess.view;

import chess.domain.piece.Color;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public CommandDto readCommand() {
        System.out.print("명령어를 입력하세요. > ");
        String rawInput = scanner.nextLine();

        return CommandDto.from(rawInput);
    }

    public CommandDto readCommandWithNoticingCurrentColor(Color color) {
        if (color.isWhite()) {
            System.out.print("명령어를 입력하세요. (현재 차례 : 백팀) > ");
        }
        if (color.isBlack()) {
            System.out.print("명령어를 입력하세요. (현재 차례 : 흑팀) > ");
        }
        String rawInput = scanner.nextLine();

        return CommandDto.from(rawInput);
    }
}

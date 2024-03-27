package chess.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public CommandDto readCommand() {
        String rawInput = scanner.nextLine();

        return CommandDto.of(rawInput);
    }
}

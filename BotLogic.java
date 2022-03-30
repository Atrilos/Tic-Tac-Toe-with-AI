package tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class BotLogic extends Tictactoe{

    char[] botBoard = new char[9];

    BotLogic(char[] ch, char nextTurnSymbol) {
        super(ch, nextTurnSymbol);
        botBoard = ch.clone();
    }

    public static int botMove(char[] ch, char nextTurnSymbol, String difficulty) {
        BotLogic botGrid = new BotLogic(ch, nextTurnSymbol);
        switch (difficulty) {
            case "easy" -> {
                announceMove("easy");
                return botGrid.botMoveEasy();
            }
            case "medium" -> {
                announceMove("medium");
                return botGrid.botMoveMedium();
            }
            case "hard" -> {
                announceMove("hard");
                return botGrid.botMoveHard();
            }
        }
        return 0;
    }

    private int botMoveEasy() {
        int result;
        Random rand = new Random();
        while (true) {
            int pos = rand.nextInt(9);
            if (grid[pos] == ' ') {
                result = pos;
                break;
            }
        }
        return result;
    }

    private int botMoveMedium() {
        int pos = winningMove(nextTurnSymbol);
        if (pos != -1) {
            return pos;
        }
        if (nextTurnSymbol == 'X') {
            pos = winningMove('O');
        } else {
            pos = winningMove('X');
        }
        if (pos != -1) {
            return pos;
        }
        return botMoveEasy();
    }

    private int botMoveHard() {
        return Minimax.bestMove(botBoard, nextTurnSymbol, true);
    }

    private int winningMove(char nextTurnSymbol) {
        for (int i = 0; i < 3; i++) {
            if (grid[i * 3] == ' '
                    && grid[i * 3 + 1] == nextTurnSymbol
                    && grid[i * 3 + 2] == nextTurnSymbol)
                return i * 3;
            if (grid[i * 3] == nextTurnSymbol
                    && grid[i * 3 + 1] == ' '
                    && grid[i * 3 + 2] == nextTurnSymbol)
                return i * 3 + 1;
            if (grid[i * 3] == nextTurnSymbol
                    && grid[i * 3 + 1] == nextTurnSymbol
                    && grid[i * 3 + 2] == ' ')
                return i * 3 + 2;
            if (grid[i] == ' '
                    && grid[i + 3] == nextTurnSymbol
                    && grid[i + 6] == nextTurnSymbol)
                return i;
            if (grid[i] == nextTurnSymbol
                    && grid[i + 3] == ' '
                    && grid[i + 6] == nextTurnSymbol)
                return i + 3;
            if (grid[i] == nextTurnSymbol
                    && grid[i + 3] == nextTurnSymbol
                    && grid[i + 6] == ' ')
                return i + 6;
        }
        if (grid[0] == ' '
                && grid[4] == nextTurnSymbol
                && grid[8] == nextTurnSymbol)
            return 0;
        if (grid[0] == nextTurnSymbol
                && grid[4] == ' '
                && grid[8] == nextTurnSymbol)
            return 4;
        if (grid[0] == nextTurnSymbol
                && grid[4] == nextTurnSymbol
                && grid[8] == ' ')
            return 8;
        if (grid[2] == ' '
                && grid[4] == nextTurnSymbol
                && grid[6] == nextTurnSymbol)
            return 2;
        if (grid[2] == nextTurnSymbol
                && grid[4] == ' '
                && grid[6] == nextTurnSymbol)
            return 4;
        if (grid[2] == nextTurnSymbol
                && grid[4] == nextTurnSymbol
                && grid[6] == ' ')
            return 6;
        return -1;
    }

    private static void announceMove(String difficulty) {
        System.out.println("Making move level \"" + difficulty + "\"");
    }
}

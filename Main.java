package tictactoe;

import java.util.Scanner;

enum GameState {
    X_WINS, O_WINS, DRAW, NOT_FINISHED
}

enum MenuState {
    PENDING, START, EXIT
}

enum Players {
    UNKNOWN, USER, EASY, MEDIUM, HARD
}

class Tictactoe {
    protected char[] grid = new char[9];
    protected char nextTurnSymbol;
    private static GameState state = GameState.NOT_FINISHED;
    private static MenuState menuState = MenuState.PENDING;
    private static Players playerOne = Players.UNKNOWN;
    private static Players playerTwo = Players.UNKNOWN;
    private final Scanner sc = new Scanner(System.in);

    public Tictactoe() {
        for (int i = 0; i < 9; i++) {
            grid[i] = ' ';
        }
        nextTurnSymbol = 'X';
    }

    public Tictactoe(char[] grid, char nextTurnSymbol) {
        this.grid = grid;
        this.nextTurnSymbol = nextTurnSymbol;
    }

    public void printGrid() {
        String first = grid[0] + " " + grid[1] + " " + grid[2] + " ";
        String second = grid[3] + " " + grid[4] + " " + grid[5] + " ";
        String third = grid[6] + " " + grid[7] + " " + grid[8] + " ";
        System.out.println("---------");
        System.out.println("| " + first + "|");
        System.out.println("| " + second + "|");
        System.out.println("| " + third + "|");
        System.out.println("---------");
    }

    protected static boolean isWin(char[] grid, char nextTurnSymbol) {
        for (int i = 0; i < 3; i++) {
            if (grid[i * 3] == nextTurnSymbol && grid[i * 3 + 1] == nextTurnSymbol
                    && grid[i * 3 + 2] == nextTurnSymbol) return true;
            if (grid[i] == nextTurnSymbol && grid[i + 3] == nextTurnSymbol
                    && grid[i + 6] == nextTurnSymbol) return true;
        }
        if (grid[0] == nextTurnSymbol && grid[4] == nextTurnSymbol
                && grid[8] == nextTurnSymbol) return true;
        if (grid[2] == nextTurnSymbol && grid[4] == nextTurnSymbol
                && grid[6] == nextTurnSymbol) return true;
        return false;
    }

    private boolean isDraw() {
        for (int i = 0; i < 9; i++) {
            if (grid[i] == ' ') return false;
        }
        return true;
    }

    public void gameState() {
        if (isWin(grid, 'X')) {
            state = GameState.X_WINS;
            return;
        }
        if (isWin(grid, 'O')) {
            state = GameState.O_WINS;
            return;
        } else
        if (isDraw()) {
            state = GameState.DRAW;
        }
    }

    public void printResult() {
        switch (state) {
            case DRAW -> {
                System.out.println("Draw");
                restart();
            }
            case O_WINS -> {
                System.out.println("O wins");
                restart();
            }
            case X_WINS -> {
                System.out.println("X wins");
                restart();
            }
            case NOT_FINISHED -> System.out.println("Game not finished");
        }
    }

    public void menu() {
        restart();
        System.out.print("Input command: ");
        String[] initString = sc.nextLine().split(" ", 3);
        if (initString.length < 3 && !initString[0].equals("exit")) {
            System.out.println("Bad parameters!");
            return;
        }
        if (initString[0].equals("exit")) {
            menuState = MenuState.EXIT;
            return;
        }
        switch (initString[0]) {
            case "start" -> menuState = MenuState.START;
            default -> {
                System.out.println("Bad parameters!");
                return;
            }
        }
        switch (initString[1]) {
            case "easy" -> playerOne = Players.EASY;
            case "medium" -> playerOne = Players.MEDIUM;
            case "hard" -> playerOne = Players.HARD;
            case "user" -> playerOne = Players.USER;
            default -> {
                System.out.println("Bad parameters!");
                menuState = MenuState.PENDING;
                return;
            }
        }
        switch (initString[2]) {
            case "easy" -> playerTwo = Players.EASY;
            case "medium" -> playerTwo = Players.MEDIUM;
            case "hard" -> playerTwo = Players.HARD;
            case "user" -> playerTwo = Players.USER;
            default -> {
                System.out.println("Bad parameters!");
                menuState = MenuState.PENDING;
            }
        }
    }

    public static MenuState getMenuState() {
        return menuState;
    }

    private void restart() {
        menuState = MenuState.PENDING;
        playerOne = Players.UNKNOWN;
        playerTwo = Players.UNKNOWN;
        state = GameState.NOT_FINISHED;
    }

    public void nextMove() {
        if (nextTurnSymbol == 'X') {
            switch (playerOne) {
                case EASY ->
                    setGrid(BotLogic.botMove(grid, nextTurnSymbol, "easy"));
                case MEDIUM ->
                    setGrid(BotLogic.botMove(grid, nextTurnSymbol, "medium"));
                case HARD ->
                    setGrid(BotLogic.botMove(grid, nextTurnSymbol, "hard"));
                case USER ->
                    setGrid(User.playerMove(grid, nextTurnSymbol));
            }
        }
        if (nextTurnSymbol == 'O') {
            switch (playerTwo) {
                case EASY ->
                        setGrid(BotLogic.botMove(grid, nextTurnSymbol, "easy"));
                case MEDIUM ->
                        setGrid(BotLogic.botMove(grid, nextTurnSymbol, "medium"));
                case HARD ->
                        setGrid(BotLogic.botMove(grid, nextTurnSymbol, "hard"));
                case USER ->
                        setGrid(User.playerMove(grid, nextTurnSymbol));
            }
        }
    }

    public static Players getPlayerOne() {
        return playerOne;
    }

    public static GameState getState() {
        return state;
    }

    protected void switchNextTurnSymbol() {
        if (nextTurnSymbol == 'X') nextTurnSymbol = 'O';
        else nextTurnSymbol = 'X';
    }

    protected void setGrid(int x) {
        grid[x] = nextTurnSymbol;
        gameState();
        printGrid();
        if (!(state == GameState.NOT_FINISHED)) {
            return;
        }
        switchNextTurnSymbol();
    }
}

public class Main {
    public static void main(String[] args) {
        Tictactoe grid = new Tictactoe();
        while (true) {
            while (Tictactoe.getMenuState().equals(MenuState.PENDING)) {
                grid.menu();
                if (Tictactoe.getMenuState().equals(MenuState.EXIT)) {
                    System.exit(0);
                }
            }
            if (Tictactoe.getPlayerOne().equals(Players.USER)) {
                grid.printGrid();
            }
            while (Tictactoe.getState().equals(GameState.NOT_FINISHED)) {
                grid.nextMove();
            }
            grid.printResult();
            grid = new Tictactoe();
            System.out.println();
        }
    }
}

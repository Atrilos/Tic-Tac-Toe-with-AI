package tictactoe;

import java.util.Scanner;

public class User extends Tictactoe{

    User(char[] ch, char nextTurnSymbol) {
        super(ch, nextTurnSymbol);
    }

    public static int playerMove(char[] ch, char nextTurnSymbol) {
        Scanner sc = new Scanner(System.in);
        User userGrid = new User(ch, nextTurnSymbol);
        for (;;) {
            System.out.print("\nEnter the coordinates: ");
            int result = userGrid.checkErrors(sc.nextLine().split(" ", 2));
            if (result != -1) {
                return result;
            }
        }
    }

    public int checkErrors(String[] str) {
        for (String s : str){
            if (!s.matches("[0-9]+")) {
                System.out.println("You should enter numbers!");
                return -1;
            }
        }
        int row = Integer.parseInt(str[0]) - 1;
        int column = Integer.parseInt(str[1]) - 1;
        if (row < 0 || row > 2 || column < 0 || column > 2) {
            System.out.println("Coordinates should be from 1 to 3!");
            return -1;
        }
        if (grid[row * 3 + column] != ' ') {
            System.out.println("This cell is occupied! Choose another one!");
            return -1;
        }
        return row * 3 + column;
    }

}

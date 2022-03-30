package tictactoe;

import java.util.ArrayList;

public class Minimax extends Tictactoe {
    char[] botBoard = new char[9];

    Minimax(char[] board, char nextTurnSymbol) {
        super(board, nextTurnSymbol);
        this.botBoard = board.clone();
    }

    private int minimax(char[] board, char nextTurnSymbol, boolean isAI) {
        var availIndexes = emptyIndexes(board);
        if (isAI && isWin(board, nextTurnSymbol)) {
            return 10;
        } else if (!isAI && isWin(board, nextTurnSymbol)) {
            return -10;
        } else if (availIndexes.isEmpty()) {
            return 0;
        }
        ArrayList<Minimax> moves = new ArrayList<>();
        int bestScore;
        if (isAI) {
            bestScore = -10000;
            for (int i = 0; i < availIndexes.size(); i++) {
                board[availIndexes.get(i)] = nextTurnSymbol;
                int score = minimax(board.clone(), anotherTurnSymbol(nextTurnSymbol), false);
                board[availIndexes.get(i)] = ' ';
                bestScore = Math.max(score, bestScore);
            }
        } else {
            bestScore = 10000;
            for (int i = 0; i < availIndexes.size(); i++) {
                board[availIndexes.get(i)] = nextTurnSymbol;
                int score = minimax(board.clone(), anotherTurnSymbol(nextTurnSymbol), true);
                board[availIndexes.get(i)] = ' ';
                bestScore = Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    private ArrayList<Integer> emptyIndexes(char[] board) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') result.add(i);
        }
        return result;
    }

    private char anotherTurnSymbol(char currentSymbol) {
        if (currentSymbol == 'X') return 'O';
        else return 'X';
    }

    public static int bestMove(char[] board, char nextTurnSymbol, boolean isAI) {
        int bestScore;
        int result = -1;
        Minimax newBoard = new Minimax(board, nextTurnSymbol);
        var availIndexes = newBoard.emptyIndexes(board);
        if (isAI) {
            bestScore = -10000;
            for (int i = 0; i < availIndexes.size(); i++) {
                board[availIndexes.get(i)] = nextTurnSymbol;
                int score = newBoard.minimax(board.clone(), newBoard.anotherTurnSymbol(nextTurnSymbol), false);
                board[availIndexes.get(i)] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    result = availIndexes.get(i);
                }
            }
        }
        return result;
    }
}

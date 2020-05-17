import java.util.*;

public class game {

    int score = 0;
    Scanner in = new Scanner(System.in);
    public boolean quite = false;
    private int diff = 0; //controlls difficulties
    //displayBoard contains the game board that is printed in the console;
    //board array is used in the logical checkings of the game
    private String[][] displayBoard = {{"|X|", "|2|", "|3|"}, {"|4|", "|5|", "|6|"}, {"|7|", "|8|", "|9|"}};
    private String[][] board = {{"|X|", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};

    //declaring players
    private String ai = "|X|";
    private String human = "|O|";
    private String currentPlayer = human;

    public void innit() {
        //This function initialize the game and fixes the difficulty
        System.out.println("Welcome to TicTacToe game with the computer");
        while (diff == 0) {

            System.out.println("Choose difficulty-");
            System.out.println("1.HARD      2.easy ");
            diff = in.nextInt();
        }

        System.out.println("Press 1-9 to put O \nComputer makes the first move");
    }

    public void printBoard() {
        //prints the game board
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(displayBoard[i][j] + " ");
            }
            System.out.println();
            System.out.println("---------");
        }
        System.out.println("---------");
    }

    private void clearBoard() {
        //clears the board after a tie
        String[][] dB = {{"|X|", "|2|", "|3|"}, {"|4|", "|5|", "|6|"}, {"|7|", "|8|", "|9|"}};
        String[][] b = {{"|X|", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        displayBoard = dB;
        board = b;
    }

    public int[] getInput() {
        //coverts the input(1-9) into the index of board array.
        int[] input = new int[2];
        int inp = in.nextInt();
        switch (inp) {
            case 1:
                input[0] = 0;
                input[1] = 0;
                break;
            case 2:
                input[0] = 0;
                input[1] = 1;
                break;
            case 3:
                input[0] = 0;
                input[1] = 2;
                break;
            case 4:
                input[0] = 1;
                input[1] = 0;
                break;
            case 5:
                input[0] = 1;
                input[1] = 1;
                break;
            case 6:
                input[0] = 1;
                input[1] = 2;
                break;
            case 7:
                input[0] = 2;
                input[1] = 0;
                break;
            case 8:
                input[0] = 2;
                input[1] = 1;
                break;
            case 9:
                input[0] = 2;
                input[1] = 2;
                break;

            default:
                System.out.println("Invalid number... pick again");
                turn();

                break;
        }

        return input;
    }

    void turn() {

        printBoard();
        if (currentPlayer == human) {
            // Human makes a turn
            int[] input = getInput();
            int i = input[0];
            int j = input[1];
            // If the cell is empty
            if (board[i][j].equals(" ")) {
                board[i][j] = human;
                displayBoard[i][j] = human;
                currentPlayer = ai;
                bestMove();
                printBoard();
            } else {
                System.out.println("invalid input");
                turn();
            }
        }
    }

    void humanVShuman() {
        //concept to implement human vs human
//            board[0][0]=" ";
//            displayBoard[0][0]=" ";
//        printBoard();
//        if (currentPlayer == human) {
//            // Human make turn
//            int[] input = getInput();
//            int i = input[0];
//            int j = input[1];
//            // If valid turn
//            if (board[i][j].equals(" ")) {
//                board[i][j] = human;
//                displayBoard[i][j] = human;
//                currentPlayer = ai;
//
//                printBoard();
//            } else {
//                System.out.println("invalid input");
//                turn();
//            }
//        } else if (currentPlayer == ai) {
//            int[] input = getInput();
//            int i = input[0];
//            int j = input[1];
//            // If valid turn
//            if (board[i][j].equals(" ")) {
//                board[i][j] = human;
//                displayBoard[i][j] = ai;
//                currentPlayer = human;
//
//                printBoard();
//            } else {
//                System.out.println("invalid input");
//                turn();
//            }
//        }
    }

    public String checkWinner() {
        //search the board for a winner and returns it as a String
        String winner = "none";

        // horizontal lines
        for (int i = 0; i < 3; i++) {
            if (stringEq(board[i][0], board[i][1], board[i][2]) && !(board[i][0].equals(" "))) {
                winner = board[i][0];
            }
        }

        // Vertical lines
        for (int i = 0; i < 3; i++) {
            if (stringEq(board[0][i], board[1][i], board[2][i]) && !(board[0][i].equals(" "))) {
                winner = board[0][i];
            }
        }

        // Diagonal lines
        if (stringEq(board[0][0], board[1][1], board[2][2]) && !(board[1][1].equals(" "))) {
            winner = board[0][0];
        }
        if (stringEq(board[2][0], board[1][1], board[0][2]) && !(board[1][1].equals(" "))) {
            winner = board[2][0];
        }
        //counts the openSpots of the board
        int openSpots = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    openSpots++;
                }
            }
        }
        // returns "tie" if the board is full and there is no winner
        if (winner.equals("none") && openSpots == 0) {
            return "tie";
        } else {
            return winner;
        }
    }

    void bestMove() {
        // AI makes it's move
        int bestScore = Integer.MIN_VALUE;
        int[] input = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // If the cell is empty
                if (board[i][j].equals(" ")) {

                    board[i][j] = ai;
                    //uses MiniMax Algorithm to find the best spot
                    score = minimax(board, 0, false);

                    board[i][j] = " ";

                    if (score > bestScore) {
                        bestScore = score;
                        input[0] = i;
                        input[1] = j;
                    }
                }
            }
        }

        System.out.println("Computer's wining probability : " + bestScore + "%");
        if (diff == 2) {
            randomSpot();
            int i = randominp[0];
            int j = randominp[1];
            board[i][j] = ai;
            displayBoard[i][j] = ai;
            currentPlayer = human;
        } else {
            board[input[0]][input[1]] = ai;
            displayBoard[input[0]][input[1]] = ai;
            currentPlayer = human;
        }
    }

    public int scores(String r) {
        //scoring system for minimax algotithm
        if (r.equals("|X|")) {
            return 100;
        } else if (r.equals("|O|")) {
            return -100;
        } else {
            return 50;
        }

    }

    public int minimax(String[][] board, int depth, boolean isMaximizing) {
        //MiniMax Algorithm
        String result = checkWinner();
        if (!(result.equals("none"))) {
            return scores(result);
        }

        if (isMaximizing) {

            int bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // If the cell is empty
                    if (board[i][j].equals(" ")) {

                        board[i][j] = ai;

                        score = minimax(board, depth + 1, false);
                        board[i][j] = " ";

                        if (score > bestScore) {
                            bestScore = score;

                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // If the cell is empty
                    if (board[i][j].equals(" ")) {

                        board[i][j] = human;
                        score = minimax(board, depth + 1, true);
                        board[i][j] = " ";

                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public void printWinner(String win) {
        //prints the winner
        if (win.equals("|X|") || win.equals("|O|")) {
            System.out.println("the winner is " + win);
            quite = true;

        } else if (win.equals("tie")) {
            System.out.println("It's a tie\npress A to play again? and any other key to exit");
            in.nextLine();
            String check = in.nextLine().toUpperCase();
            if (check.equals("A")) {
                clearBoard();
            } else {
                System.out.println("Thank you");
                quite = true;
            }

        }
    }
    // array to store the random input;
    int[] randominp = new int[2];

    public void randomSpot() {
        //chooses a random empty spot on the board
        int i = randomRange(0, 2);
        int j = randomRange(0, 2);
        if (board[i][j].equals(" ")) {
            randominp[0] = i;
            randominp[1] = j;
        } else {
            randomSpot();
        }

    }

    public boolean stringEq(String x, String y, String z) {
        //checks if given 3 Strings are equal
        return x.equals(y) && x.equals(z) && y.equals(z);
    }

    public int randomRange(int min, int max) {
        //returns a random number in given range
        return (int) (Math.floor(Math.random() * (max - min + 1) + min));
    }

}

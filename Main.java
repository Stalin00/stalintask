    import java.util.*;

public class Main {
    private static final int SIZE = 3;
    private char[][] grid = new char[SIZE][SIZE];
    private String player1, player2;
    private int player1Wins = 0, player2Wins = 0;
    private long startTime;

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Player 1 name: ");
        player1 = scanner.nextLine();
        System.out.print("Enter Player 2 name: ");
        player2 = scanner.nextLine();

        boolean playAgain;
        do {
            playGame();
            System.out.println("Leaderboard:");
            System.out.println(player1 + ": " + player1Wins + " wins");
            System.out.println(player2 + ": " + player2Wins + " wins");
            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.nextLine().equalsIgnoreCase("yes");
        } while (playAgain);

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private void playGame() {
        resetGrid();
        startTime = System.currentTimeMillis();
        boolean isPlayer1Turn = true;

        while (true) {
            displayGrid();
            boolean validMove = makeMove(isPlayer1Turn);
            if (validMove) {
                if (checkWinner()) {
                    displayGrid();
                    String winner = isPlayer1Turn ? player1 : player2;
                    System.out.println("Congratulations! " + winner + " wins!");
                    if (isPlayer1Turn) player1Wins++;
                    else player2Wins++;
                    break;
                }
                if (isGridFull()) {
                    displayGrid();
                    System.out.println("It's a draw!");
                    break;
                }
                isPlayer1Turn = !isPlayer1Turn;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Game duration: " + (endTime - startTime) / 1000 + " seconds");
    }

    private void resetGrid() {
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(grid[i], ' ');
        }
    }

    private void displayGrid() {
        System.out.println("\nCurrent Grid:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + (j < SIZE - 1 ? " | " : ""));
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("---------");
        }
    }

    private boolean makeMove(boolean isPlayer1Turn) {
        Scanner scanner = new Scanner(System.in);
        String currentPlayer = isPlayer1Turn ? player1 : player2;
        char symbol = isPlayer1Turn ? 'X' : 'O';

        System.out.println(currentPlayer + "'s turn (" + symbol + "). Enter row and column (0-2): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || grid[row][col] != ' ') {
            System.out.println("Invalid move. Try again.");
            return false;
        }

        grid[row][col] = symbol;
        return true;
    }

    private boolean checkWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][0] != ' ' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) return true;
            if (grid[0][i] != ' ' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) return true;
        }
        if (grid[0][0] != ' ' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) return true;
        if (grid[0][2] != ' ' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) return true;
        return false;
    }

    private boolean isGridFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == ' ') return false;
            }
        }
        return true;
    }
}

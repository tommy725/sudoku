import sudoku.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class Main {

    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        System.out.println(greeter.greet("world!"));
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.solveGame();
    }
}

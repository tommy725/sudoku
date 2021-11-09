package sudoku;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import sudoku.group.SudokuBox;
import sudoku.group.SudokuColumn;
import sudoku.group.SudokuRow;
import sudoku.solver.SudokuSolver;

public class SudokuBoard implements PropertyChangeListener {
    private SudokuField[][] board = new SudokuField[9][9];
    private List<SudokuRow> rows = Arrays.asList(new SudokuRow[9]);
    private List<SudokuColumn> columns = Arrays.asList(new SudokuColumn[9]);
    private List<SudokuBox> boxes = Arrays.asList(new SudokuBox[9]);
    private SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver solver) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
                board[i][j].setListener(this);
            }
        }
        // Create columns and rows
        for (int i = 0; i < 9; i++) {
            SudokuField[] column = new SudokuField[9];
            for (int j = 0; j < 9; j++) {
                column[j] = board[j][i];
            }
            rows.set(i, new SudokuRow(board[i]));
            columns.set(i, new SudokuColumn(column));
        }
        // Create boxes
        for (int i = 0; i < 9; i++) {
            SudokuField[] box = new SudokuField[9];
            int j = 0;
            for (int k = (i / 3) * 3; k < (i / 3) * 3 + 3; k++) {
                for (int l = (i % 3) * 3; l < (i % 3) * 3 + 3; l++) {
                    box[j++] = board[k][l];
                }
            }
            boxes.set(i,new SudokuBox(box));
        }
        sudokuSolver = solver;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!checkBoard()) {
            SudokuField field = (SudokuField) evt.getSource();
            field.setFieldValue((Integer) evt.getOldValue());
        }
    }

    public int get(int x, int y) {
        if (x >= 0 && x <= 8 && y >= 0 && y <= 8) {
            return board[x][y].getFieldValue();
        }
        return -1;
    }

    public void set(int x, int y, int value) {
        if (x >= 0 && x <= 8 && y >= 0 && y <= 8 && value >= 0 && value <= 9) {
            (board[x][y]).setFieldValue(value);
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!(rows.get(i).verify() && columns.get(i).verify() && boxes.get(i).verify())) {
                return false;
            }
        }
        return true;
    }

    public SudokuRow getRow(int y) {
        return rows.get(y);
    }

    public SudokuColumn getColumn(int x) {
        return columns.get(x);
    }

    public SudokuBox getBox(int x, int y) {
        return boxes.get(x * 3 + y);
    }

    public boolean check(int row, int col, int generated) {
        //Check row
        for (int j = 0; j < 9; j++) {
            if (get(row, j) == generated) {
                return false;
            }
        }
        //Check column
        for (int j = 0; j < 9; j++) {
            if (get(j, col) == generated) {
                return false;
            }
        }
        //Check square
        int boxRowFirst = row - row % 3;
        int boxColFirst = col - col % 3;
        for (int i = boxRowFirst; i < boxRowFirst + 3; i++) {
            for (int j = boxColFirst; j < boxColFirst + 3; j++) {
                if (get(i, j) == generated) {
                    return false;
                }
            }
        }
        return true;
    }
}

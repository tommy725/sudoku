import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    @Test
    void fillBoardCheckTest() {
        SudokuBoard board = new SudokuBoard();
        for(int i = 0; i < 9; i++) {
            assertFalse(checkRowDuplicates(board, i));
            assertFalse(checkColDuplicates(board, i));
            if(i % 3 == 0) {
                for (int j = 0; j < 9; j += 3) {
                    assertFalse(checkBoxDuplicates(board, i, j));
                }
            }
        }
    }

    @Test
    void fillBoardRandomizeTest() {
        SudokuBoard board1 = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        boolean isDifference = false;
        outer: for (int i = 0; i < 9; i++) {
            for (int j = 0 ; j < 9; j++) {
                if (board1.getBoardFieldValue(i,j) != board2.getBoardFieldValue(i,j)) {
                    isDifference = true;
                    break outer;
                }
            }
        }
        assertTrue(isDifference);
    }

    boolean checkBoxDuplicates(SudokuBoard board, int rowStart, int colStart) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[arrayIndex++] = board.getBoardFieldValue(rowStart + i,colStart + j);
            }
        }
        return checkDuplicates(array);
    }

    boolean checkRowDuplicates(SudokuBoard board, int rowNumber) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 9; i++) {
            array[arrayIndex++] = board.getBoardFieldValue(rowNumber,i);
        }
        return checkDuplicates(array);
    }

    boolean checkColDuplicates(SudokuBoard board, int colNumber) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 9; i++) {
            array[arrayIndex++] = board.getBoardFieldValue(i,colNumber);
        }
        return checkDuplicates(array);
    }

    boolean checkDuplicates(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1 ; j < array.length; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
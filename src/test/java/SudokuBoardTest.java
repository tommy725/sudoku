import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    @Test
    void solveGameCheckTest() {
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
    void solveGameRandomizeTest() {
        SudokuBoard board1 = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        boolean isDifference = false;
        outer: for (int i = 0; i < 9; i++) {
            for (int j = 0 ; j < 9; j++) {
                if (board1.get(i,j) != board2.get(i,j)) {
                    isDifference = true;
                    break outer;
                }
            }
        }
        assertTrue(isDifference);
    }

    @Test
    void setterPositiveTest() {
        SudokuBoard board1 = new SudokuBoard();
        board1.set(0,0,2);
        int changed = board1.get(0,0);
        assertEquals(changed,2);
        board1.set(0,0,3);
        changed = board1.get(0,0);
        assertEquals(changed,3);
    }

    @Test
    void setterNegativeTests() {
        SudokuBoard board3 = new SudokuBoard();
        board3.set(0,0,-1);
        assertNotEquals(-1,board3.get(0,0));
        board3.set(0,0,10);
        assertNotEquals(10,board3.get(0,0));
        board3.set(9,9,1);
        assertNotEquals(1,board3.get(9,9));
        board3.set(8,9,1);
        assertNotEquals(1,board3.get(8,9));
        board3.set(9,8,1);
        assertNotEquals(1,board3.get(9,8));
        board3.set(8,-1,1);
        assertNotEquals(1,board3.get(8,-1));
        board3.set(-1,8,1);
        assertNotEquals(1,board3.get(-1,8));
    }

    boolean checkBoxDuplicates(SudokuBoard board, int rowStart, int colStart) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[arrayIndex++] = board.get(rowStart + i,colStart + j);
            }
        }
        return checkDuplicates(array);
    }

    boolean checkRowDuplicates(SudokuBoard board, int rowNumber) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 9; i++) {
            array[arrayIndex++] = board.get(rowNumber,i);
        }
        return checkDuplicates(array);
    }

    boolean checkColDuplicates(SudokuBoard board, int colNumber) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 9; i++) {
            array[arrayIndex++] = board.get(i,colNumber);
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sudoku.SudokuBoard;
import sudoku.group.SudokuBox;
import sudoku.group.SudokuColumn;
import sudoku.group.SudokuRow;
import sudoku.solver.BacktrackingSudokuSolver;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private SudokuBoard board1, board2, boardNotSolved;

    @BeforeEach
    void setUp() {
        board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board2.solveGame();
        boardNotSolved = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    @Test
    @DisplayName("Solve game check test")
    void solveGameCheckTest() {
        for (int i = 0; i < 9; i++) {
            assertFalse(checkRowDuplicates(board1, i));
            assertFalse(checkColDuplicates(board1, i));
            if (i % 3 == 0) {
                for (int j = 0; j < 9; j += 3) {
                    assertFalse(checkBoxDuplicates(board1, i, j));
                }
            }
        }
    }

    @Test
    @DisplayName("Solve game randomize test")
    void solveGameRandomizeTest() {
        boolean isDifference = false;
        outer:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board1.get(i, j) != board2.get(i, j)) {
                    isDifference = true;
                    break outer;
                }
            }
        }
        assertTrue(isDifference);
    }
    
    @Test
    @DisplayName("Setter positive test")
    void setterPositiveTest() {
        boardNotSolved.set(0, 0, 2);
        assertEquals(2, boardNotSolved.get(0, 0));
        boardNotSolved.set(0, 0, 3);
        assertEquals(3, boardNotSolved.get(0, 0));
    }

    public static Stream<Arguments> setterOutOfRangesNegativeTestsDataProvider() {
        return Stream.of(
            Arguments.of(0,0,-1),
            Arguments.of(0,0,10),
            Arguments.of(9,9,1),
            Arguments.of(8,9,1),
            Arguments.of(9,8,1),
            Arguments.of(8,-1,1),
            Arguments.of(-1,8,1)
        );
    }

    @ParameterizedTest
    @DisplayName("Setter out of ranges negative tests")
    @MethodSource("setterOutOfRangesNegativeTestsDataProvider")
    void setterOutOfRangesNegativeTests(int x, int y, int value) {
        board1.set(x, y, value);
        assertNotEquals(value, board1.get(x, y));
    }

    public static Stream<Arguments> setterSameValueInGroupsNegativeTestsDataProvider() {
        return Stream.of(
            Arguments.of(0,1,1),
            Arguments.of(1,0,1),
            Arguments.of(1,1,1)
        );
    }

    @ParameterizedTest
    @DisplayName("Setter same value in groups negative tests")
    @MethodSource("setterSameValueInGroupsNegativeTestsDataProvider")
    void setterSameValueInGroupsNegativeTests(int x, int y, int value) {
        boardNotSolved.set(0, 0, 1);
        boardNotSolved.set(x, y, value);
        assertNotEquals(value, boardNotSolved.get(x, y));
    }

    @Test
    @DisplayName("Get row test")
    void getRowTest() {
        for (int i = 0; i < 9; i++) {
            SudokuRow row = board1.getRow(i);
            for (int j = 0; j < 9; j++) {
                assertEquals(board1.get(i, j), row.getField(j));
            }
        }
    }
    
    @Test
    @DisplayName("Get column test")
    void getColumnTest() {
        for (int i = 0; i < 9; i++) {
            SudokuColumn column = board1.getColumn(i);
            for (int j = 0; j < 9; j++) {
                assertEquals(board1.get(j, i), column.getField(j));
            }
        }
    }

    @Test
    @DisplayName("Get box test")
    void getBoxTest() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                SudokuBox box = board1.getBox(i, j);
                for (int k = 0; k < 9; k++) {
                    assertEquals(board1.get(i * 3 + k / 3, j * 3 + k % 3), box.getField(k));
                }
            }
        }
    }

    boolean checkBoxDuplicates(SudokuBoard board, int rowStart, int colStart) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[arrayIndex++] = board.get(rowStart + i, colStart + j);
            }
        }
        return checkDuplicates(array);
    }

    boolean checkRowDuplicates(SudokuBoard board, int rowNumber) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 9; i++) {
            array[arrayIndex++] = board.get(rowNumber, i);
        }
        return checkDuplicates(array);
    }

    boolean checkColDuplicates(SudokuBoard board, int colNumber) {
        int[] array = new int[9];
        int arrayIndex = 0;
        for (int i = 0; i < 9; i++) {
            array[arrayIndex++] = board.get(i, colNumber);
        }
        return checkDuplicates(array);
    }

    boolean checkDuplicates(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
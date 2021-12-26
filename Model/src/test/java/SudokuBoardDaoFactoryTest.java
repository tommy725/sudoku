import dao.FileSudokuBoardDao;
import dao.SudokuBoardDaoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    @Test
    @DisplayName("Get file dao test")
    void getFileDaoTest() {
        assertTrue(SudokuBoardDaoFactory.getFileDao("test") instanceof FileSudokuBoardDao);
    }
}
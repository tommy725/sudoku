open module ModelProject {
    requires java.desktop;
    requires com.google.common;
    exports sudoku;
    exports sudoku.solver;
    exports dao;
    exports exceptions;
}
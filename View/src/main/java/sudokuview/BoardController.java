package sudokuview;

import dao.Dao;
import dao.SudokuBoardDaoFactory;
import java.io.File;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import sudoku.SudokuBoard;
import sudoku.solver.BacktrackingSudokuSolver;

public class BoardController {
    @FXML
    private VBox board;
    private SudokuBoard initialBoard;

    public class SudokuFieldAdapter {
        private SudokuBoard sudokuBoard;
        private int xx;
        private int yy;

        public SudokuFieldAdapter(SudokuBoard board, int x, int y) {
            this.sudokuBoard = board;
            this.xx = x;
            this.yy = y;
        }

        public String getValue() {
            return String.valueOf(this.sudokuBoard.get(xx, yy));
        }

        public void setValue(String value) {
            if (value.equals("")) {
                value = "0";
            }
            if (value.length() == 1
                    && '0' <= value.charAt(0)
                    && value.charAt(0) <= '9') {
                this.sudokuBoard.set(xx, yy, Integer.parseInt(value));
                System.out.println(this.sudokuBoard);
            }
        }
    }

    public void startGame(SudokuBoard modelSudokuBoard) {
        for (int i = 0; i < board.getChildren().size(); i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < row.getChildren().size(); j++) {
                TextField textField = (TextField) row.getChildren().get(j);
                int value = modelSudokuBoard.get(i, j);
                if (value != 0) {
                    textField.setText(String.valueOf(value));
                    textField.setDisable(true);
                }
                textField.textProperty().addListener(this::fieldListener);
                try {
                    SudokuFieldAdapter fieldAdapter =
                            new SudokuFieldAdapter(modelSudokuBoard, i, j);
                    StringProperty fieldProperty = JavaBeanStringPropertyBuilder.create()
                            .bean(fieldAdapter).name("value").build();
                    textField.textProperty().bindBidirectional(fieldProperty);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        }
        try {
            initialBoard = modelSudokuBoard.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void fieldListener(ObservableValue<? extends String> observable,
                               String oldValue, String newValue) {
        if (!newValue.isEmpty() && (newValue.length() > 1
                || '0' >= newValue.charAt(0)
                || newValue.charAt(0) > '9')) {
            StringProperty stringProperty = (StringProperty) observable;
            stringProperty.setValue(oldValue);
        }
    }

    public void saveToFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save board to file");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Sudoku game save (*.bin)", "*.bin")
        );
        File chosenFile = chooser.showSaveDialog(
                ((MenuItem) actionEvent.getSource()).getParentPopup()
                        .getScene()
                        .getWindow()
        );
        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(chosenFile.getAbsolutePath());
        SudokuBoard boardToSave = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < board.getChildren().size(); i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < row.getChildren().size(); j++) {
                TextField textField = (TextField) row.getChildren().get(j);
                if (textField.getText().equals("")) {
                    int val = 0;
                    boardToSave.set(i, j, val);
                } else {
                    int val = Integer.parseInt(textField.getText());
                    boardToSave.set(i, j, val);
                }
            }
        }
        dao.write(boardToSave);
    }

    public void saveInitialToFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save initial board to file");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Sudoku game save (*.bin)", "*.bin")
        );
        File chosenFile = chooser.showSaveDialog(
                ((MenuItem) actionEvent.getSource()).getParentPopup()
                        .getScene()
                        .getWindow()
        );
        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(chosenFile.getAbsolutePath());
        dao.write(initialBoard);
    }

    public void loadFromFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Sudoku game save (*.bin)", "*.bin")
        );
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*"));
        File chosenFile = chooser.showOpenDialog(
                ((MenuItem) actionEvent.getSource()).getParentPopup()
                        .getScene()
                        .getWindow()
        );
        if(chosenFile==null){
            return;
        }
        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(chosenFile.getAbsolutePath());
        SudokuBoard boardFromFile = dao.read();
        for (int i = 0; i < board.getChildren().size(); i++) {
            HBox row = (HBox) board.getChildren().get(i);
            for (int j = 0; j < row.getChildren().size(); j++) {
                TextField textField = (TextField) row.getChildren().get(j);
                if (boardFromFile.get(i, j) == 0) {
                    String val = "";
                    textField.setText(val);
                    textField.setDisable(false);
                } else {
                    String val = String.valueOf(boardFromFile.get(i, j));
                    textField.setText(val);
                    textField.setDisable(true);
                }
            }
        }
    }
}

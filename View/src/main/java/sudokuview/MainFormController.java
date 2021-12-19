package sudokuview;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainFormController {
    /**
     * Action after click the button.
     *
     * @param actionEvent action which executed event
     */
    public void levelGenerate(ActionEvent actionEvent) {
        try {
            Button button = (Button) actionEvent.getSource();
            FXMLLoader board = new FXMLLoader(
                    getClass().getResource("/Board.fxml")
            );
            board.setController(new BoardController(button.getId()));
            Stage stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            stage.setScene(new Scene(board.load()));
            stage.setTitle("TurboSudoku");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(ActionEvent actionEvent) {
    }

    public void loadToFile(ActionEvent actionEvent) {
    }
}

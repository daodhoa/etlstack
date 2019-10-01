package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class Controller {
    @FXML
    private void showDataFlow(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../View/DataFlowFXML.fxml"));

        Scene scene = new Scene(parent);
        //Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}

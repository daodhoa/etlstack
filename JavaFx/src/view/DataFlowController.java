package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import main.Main;
import services.ExcelService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataFlowController {
    private ExcelService excelService = new ExcelService();

    @FXML
    private TextField txtFilePath;
    @FXML
    private ComboBox cbBoxSheet;
    @FXML
    private TableView<List<StringProperty>> tableView;

    @FXML
    private void chooseExcelFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel (*.xlsx)",
                "*.xlsx");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Main.filePath = file.getPath();
            txtFilePath.setText(file.getPath());
            try {
                ArrayList<String> listSheet = excelService.getListSheet(Main.filePath);
                listSheet.forEach(item -> cbBoxSheet.getItems().add(item));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            txtFilePath.setText("No chosen file");
            Main.filePath = "";
        }
    }

    @FXML
    private void previewData() throws IOException {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        int index = cbBoxSheet.getItems().indexOf(cbBoxSheet.getValue());
        System.out.println(index);
        List<List<String>> listData = excelService.getDataFromSheet(index, Main.filePath);
        ArrayList<String> firstRow = (ArrayList<String>) listData.get(0);
        int numberOfColumn = firstRow.size();
        for (int i = 0; i< numberOfColumn; i++) {
            TableColumn<List<StringProperty>, String> column = new TableColumn<>(firstRow.get(i));
            int finalI = i;
            column.setCellValueFactory(data -> data.getValue().get(finalI));
            tableView.getColumns().add(column);
        }
        ObservableList<List<StringProperty>> data = FXCollections.observableArrayList();
        for (int i = 1; i < listData.size(); i++) {
            List<StringProperty> rowData = new ArrayList<>();
            ArrayList<String> row = (ArrayList<String>) listData.get(i);
            for (int j = 0; j < numberOfColumn; j++) {
                rowData.add(j, new SimpleStringProperty(row.get(j)));
            }
            data.add(rowData);
        }
        tableView.setItems(data);
    }
}
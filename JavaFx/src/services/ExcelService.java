package services;

import main.Main;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelService {
    public ExcelService() {
    }

    public Workbook readExcel(String filePath) throws IOException {
        File file = new File(filePath);
        Workbook workbook = WorkbookFactory.create(file);
        workbook.close();
        return workbook;
    }

    public ArrayList<String> getListSheet(String filePath) throws IOException {
        ArrayList<String> listSheet = new ArrayList<String>();
        Workbook workbook = this.readExcel(filePath);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            listSheet.add(sheet.getSheetName());
        }
        return listSheet;
    }

    public List<List<String>> getDataFromSheet(int index, String filePath) throws IOException {
        List<List<String>> listData = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        Workbook workbook = this.readExcel(filePath);
        Sheet sheet = workbook.getSheetAt(index);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> listCellData = new ArrayList<>();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                listCellData.add(cellValue);
            }
            listData.add(listCellData);
        }
        return listData;
    }
}

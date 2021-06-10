package main.java.tangenttesting.utilities;

import java.io.File;

import main.java.tangenttesting.entities.DataColumn;
import main.java.tangenttesting.entities.DataRow;
import main.java.tangenttesting.entities.Enums;
import main.java.tangenttesting.entities.TestEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import static java.lang.System.err;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtility {
    private static final List<TestEntity> testDataList = new ArrayList<>();
    private static Sheet _workSheet;
    private static Workbook _workbook;
    private static final String excel_extension = ".xlsx";

    public ExcelReaderUtility() {
        System.setProperty("java.awt.headless", "true");
    }

    public static List<TestEntity> getTestDataFromExcelFile(String filePath) {
        if (filePath.contains(excel_extension)) {
            _workbook = getExcelWorkbook(filePath);
            readExcelWorkSheet(_workbook);
            retrieveTestDataFromSheet();
        }
        else {//Assuming the path provided is a folder path with Excel sheets in for a regression pack
            List<String> excelFilePaths = new ArrayList<>();
            List<File> fileList = new ArrayList<>(Arrays.asList(new File(filePath).listFiles()));
            fileList = fileList.stream().filter(s -> s.getName() != null && s.getName().toLowerCase().contains(excel_extension.toLowerCase())).collect(Collectors.toList());
            fileList.forEach(s -> excelFilePaths.add(s.getAbsolutePath()));
            excelFilePaths.sort((s1, s2) -> s1.compareTo(s2));//Sorting List Alphabetically

            for (String currentExcelFilePath : excelFilePaths) {
                _workbook = getExcelWorkbook(currentExcelFilePath);
                readExcelWorkSheet(_workbook);
                retrieveTestDataFromSheet();
                _workbook = null;
            }
        }
        return testDataList;
    }

    public static Workbook getExcelWorkbook(String filePath) {
        try (InputStream reader = new FileInputStream(filePath)) {
            return WorkbookFactory.create(reader);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static boolean readExcelWorkSheet(Workbook workbook) {
        try {
            _workSheet = workbook.getSheetAt(0);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private static boolean readExcelWorkSheet(Workbook workbook, String sheetName) {
        try {
            _workSheet = workbook.getSheet(sheetName);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private static boolean retrieveTestDataFromSheet()
    {
        int lastColumn = 0;
        if (_workSheet == null) {
            return false;
        }
        try {
            for (Row row : _workSheet) {
                String firstCellValue = getCellValue(row.getCell(0));
                if (!"".equals(firstCellValue)) {
                    lastColumn = row.getLastCellNum();
                    getTestParameters(row.getRowNum(), row.getRowNum() + 1, lastColumn);
                }
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue().toString();
                    }
                    else {
                        cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                default:
            }
            if (cellValue == null) {
                cellValue = "";
            }
        }
        catch (Exception e) {
            return "";
        }
        return cellValue;
    }

    private static void getTestParameters(int parameterRowIndex, int valueRowIndex, int lastColumn) {
        TestEntity testData = new TestEntity();
        Row parameterRow;
        Row valueRow;
        String testCaseId;
        String methodName;
        String testDescription = "";
        int testParemeterStartcolumn = 3;

        parameterRow = _workSheet.getRow(parameterRowIndex);
        valueRow = _workSheet.getRow(valueRowIndex);

        testCaseId = getCellValue(parameterRow.getCell(0)).trim();
        methodName = getCellValue(parameterRow.getCell(1)).trim();
        // Check the formatting of the inputfile, if the test description column is missing
        // and a test data parameter is present, reset the start column for data to 2.
        if (getCellValue(_workSheet.getRow(parameterRowIndex + 1).getCell(2)).equals("")) {
            testDescription = getCellValue(parameterRow.getCell(2)).trim();
        }
        else {
            testParemeterStartcolumn = 2;
        }
        testData.setTestCaseID(testCaseId);
        testData.setTestMethod(methodName);
        testData.setTestDescription(testDescription);

        try {
            for (int i = testParemeterStartcolumn; i < lastColumn; i++) {
                String parameter = getCellValue(parameterRow.getCell(i)).trim();
                String value = getCellValue(valueRow.getCell(i)).trim();
                if (!"".equals(parameter)) {
                    testData.addParameter(parameter, value);
                }
            }
        }
        catch (Exception ex) {
            //Parameters were not detected - keyword might not have data requirements.
        }
        testDataList.add(testData);
    }

    public static List<TestEntity> getTestSet(String filepath) {
        List<TestEntity> dataSet =  new ArrayList<>();
        try {
            _workbook = getExcelWorkbook(filepath);
            _workSheet = _workbook.getSheetAt(0);
            Row paramRow = _workSheet.getRow(0);
            for (int i = 1; i <= _workSheet.getLastRowNum(); i++) {
                TestEntity testData = new TestEntity();

                Row row = _workSheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    String parameter = getCellValue(paramRow.getCell(j)).trim();
                    String value = getCellValue(row.getCell(j)).trim();
                    if (!"".equals(parameter)) {
                        testData.addParameter(parameter, value);
                    }
                }

                dataSet.add(testData);
            }

            return dataSet;
        }
        catch (Exception e) {
            return null;
        }

    }

    public static LinkedList<DataRow> retrieveDataRowsFromSheet(String workBookPath, String workSheetName) {
        LinkedList<DataRow> dataRows = new LinkedList<>();

        try {
            readExcelWorkSheet(getExcelWorkbook(workBookPath), workSheetName);

            Row firstRow = _workSheet.getRow(0);

            int lastColumnIndex = firstRow.getLastCellNum();

            for (Row row : _workSheet) {
                DataRow currentRow = new DataRow();

                if (row.getRowNum() > 0 && !getCellValue(row.getCell(0)).equals("")) {
                    for (int i = 0; i < lastColumnIndex; i++) {
                        Cell currentCell = row.getCell(i);

                        DataColumn column;

                        if (currentCell == null)
                        {
                            column = new DataColumn(getCellValue(firstRow.getCell(i)), "", Enums.ResultStatus.UNCERTAIN);
                        }
                        else
                        {
                            column = new DataColumn(getCellValue(firstRow.getCell(currentCell.getColumnIndex())), getCellValue(currentCell), Enums.ResultStatus.UNCERTAIN);
                        }
                        currentRow.addToDataColumns(column);
                    }
                    dataRows.add(currentRow);
                }
            }

            out.println("[INFO] Read " + dataRows.size() + " rows from the excel input");

        }
        catch (Exception ex) {
            err.println("[ERROR] Failed to retrieve data rows from sheet - " + ex.getMessage());
        }
        return dataRows;
    }

    public static LinkedList<DataColumn> convertExcelToColumn(String ExcelFilePath) {
        DataRow newRow = new DataRow();
        Workbook book = getExcelWorkbook(ExcelFilePath);
        readExcelWorkSheet(book);
        List<String> Headers = new ArrayList<>();

        //Row 1 is treated as Header values
        Row row1 = _workSheet.getRow(0);

        int colCounts = row1.getLastCellNum();
        for (int j = 0; j < colCounts; j++) {
            Cell cell = row1.getCell(j);
            String value = cell.getStringCellValue();
            Headers.add(value);
        }

        //All other rows are treated as Values
        List<String> Values = new ArrayList<>();
        int rowsCount = _workSheet.getLastRowNum() + 1;
        for (int i = 1; i < rowsCount; i++) {
            for (int j = 0; j < colCounts; j++) {
                Row row = _workSheet.getRow(i);
                Cell cell = row.getCell(j);
                String value = cell.getStringCellValue();
                Values.add(value);
            }
        }

        for (int i = 0; i < Values.size(); i++) {
            DataColumn newColumn = new DataColumn("", "", Enums.ResultStatus.UNCERTAIN);

            newColumn.setColumnHeader(Headers.get((i % (Headers.size()))));
            newColumn.setColumnValue(Values.get(i));

            newRow.addToDataColumns(newColumn);
        }

        return newRow.getDataColumns();
    }

    public static boolean compareTableToExcel(LinkedList<DataColumn> Column, String ExcelFilePath) {
        LinkedList<DataColumn> ExcelData = convertExcelToColumn(ExcelFilePath);
        LinkedList<DataColumn> TableData = Column;

        if ((ExcelData.size() != TableData.size())) {
            //  Narrator.logError("Excel Data,and Table Data length do not match.");
            return false;
        }
        //Compare Headers
        for (int i = 0; i < ExcelData.size(); i++) {
            String tempHead = ExcelData.get(i).getColumnHeader();
            String tempHeadi = TableData.get(i).getColumnHeader();
            String tempValue = ExcelData.get(i).getColumnValue();
            String tempValuei = TableData.get(i).getColumnValue();

            if (!tempHead.equals(tempHeadi)) {
                //   Narrator.logError("Headers: " + tempHead + " and " + tempHeadi + " do not match.");
                return false;
            }

            if (!tempValue.equals(tempValuei)) {
                //   Narrator.logError("Values: " + tempValue + " and " + tempValuei + " do not match.");
                return false;
            }
        }

        return true;
    }

    public static boolean writeTableToExcel(LinkedList<DataColumn> Column, String ExcelFilePath, String sheetName) {
        int total = getColumnLength(Column);

        try(FileOutputStream fos = new FileOutputStream(ExcelFilePath)) {
            //LinkedList<DataColumn> TableData = Column;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet worksheet = workbook.createSheet(sheetName);

            //Create the header Row
            XSSFRow row1 = worksheet.createRow((short) 0);

            for (int i = 0; i < total; i++) {
                XSSFCell cellA1 = row1.createCell(i);
                cellA1.setCellValue(Column.get(i).getColumnHeader());
            }

            //Create Data Rows
            int totalRows = Column.size() / total;
            int counter = 0;
            //Starts at the second row
            for (int i = 1; i < totalRows; i++) {
                XSSFRow row = worksheet.createRow((short) i);
                for (int j = 0; j < total; j++) {

                    XSSFCell cellA1 = row.createCell(j);
                    cellA1.setCellValue(Column.get(counter).getColumnValue());
                    counter++;

                }
            }

            //Save workbook
            workbook.write(fos);
            fos.flush();
            fos.close();
        }
        catch (Exception e) {
            //  Narrator.logError("Failed to write HTML table to Excel, error - " + e.getMessage());

            return false;
        }

        return true;
    }

    public static int getColumnLength(LinkedList<DataColumn> Column) {
        String temp2 = "";
        int total;
        LinkedList<DataColumn> TableData = Column;
        Set<String> totalHeaders = new HashSet<String>();
        for (int i = 0; i < TableData.size(); i++) {
            String tempHead = TableData.get(i).getColumnHeader();
            if (!tempHead.equals(temp2)) {
                totalHeaders.add(tempHead);
                temp2 = tempHead;
            }
        }
        total = totalHeaders.size();

        return total;
    }

    //IN PROGRESS
    public static LinkedList<DataColumn> convertExcelCellsToColumn(String ExcelFilePath, String RowKeyword) {
        DataRow newRow = new DataRow();
        Workbook book = getExcelWorkbook(ExcelFilePath);
        readExcelWorkSheet(book);
        List<String> Headers = new ArrayList<>();

        //Searches for Header Row
        int counter;
        int rowsCount = _workSheet.getLastRowNum();
        int colCounts = 0;

        for (int j = 0; j < rowsCount; j++) {
            counter = 0;
            Row row = _workSheet.getRow(j);
            colCounts = row.getLastCellNum();

            for (int i = 0; i < colCounts; i++) {
                Cell cell = row.getCell(i);
                if (cell != null && (cell.getCellType() != cell.CELL_TYPE_BLANK)) {//Updated method name{
                    String value = cell.getStringCellValue();
                    if (value.equals(RowKeyword)) {
                        for (int k = counter; k < colCounts; k++) {
                            Cell cells = row.getCell(k);
                            String Header = cells.getStringCellValue();
                            Headers.add(Header);
                            counter++;
                        }
                        break;
                    }
                    else {
                        counter++;
                    }
                }
                else {
                    counter++;
                }
            }
        }

        //All other rows are treated as Values
        List<String> Values = new ArrayList<>();
        int rowsCounti = _workSheet.getLastRowNum() + 1;
        for (int i = 1; i < rowsCounti; i++) {
            for (int j = 0; j < colCounts; j++) {
                Row row = _workSheet.getRow(i);
                Cell cell = row.getCell(j);
                String value = cell.getStringCellValue();
                Values.add(value);
            }
        }

        for (int i = 0; i < Values.size(); i++) {
            DataColumn newColumn = new DataColumn("", "", Enums.ResultStatus.UNCERTAIN);

            newColumn.setColumnHeader(Headers.get((i % (Headers.size()))));
            newColumn.setColumnValue(Values.get(i));

            newRow.addToDataColumns(newColumn);
        }
        return newRow.getDataColumns();
    }
}

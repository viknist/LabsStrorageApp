package com.labstorageapp.util;

import com.labstorageapp.ui.ShowMonthDataForm;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelLoader<T>{

    private JFileChooser fileChooser = null;
    private final Class<T> cls; //объект который описывает тип данных
    private final String[] columnNames; //массив который хранит названия колонок
    private List<T> rows; //коллекция хранящая сущности таблицы
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public ExcelLoader(Class<T> cls, String[] columnNames, List<T> rows)
    {
        this.cls = cls;
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            //получаем поле UserEntity по номеру колонки
            Field field = cls.getDeclaredFields()[columnIndex];

            //делаем его доступным (если оно private)
            field.setAccessible(true);

            //получаем значение этого поля из объекта
            return field.get(this.rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public void saveExcel() throws IOException {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        Sheet sheet = wb.createSheet("SavedTable");
        Row rowTitle = sheet.createRow(0);
        for (int i = 0; i < columnNames.length; i++){
            Cell titleCell = rowTitle.createCell(i);
            titleCell.setCellValue(columnNames[i]);
        }

        for(int i = 1; i <= rows.size(); i++){
            Row row = sheet.createRow(i);
            for (int j = 0; j < cls.getDeclaredFields().length; j++){
                Cell cell = row.createCell(j);
                if(cls.getDeclaredFields()[j].getType() == int.class)
                    cell.setCellValue((int) getValueAt(i-1, j));
                else if(cls.getDeclaredFields()[j].getType() == String.class)
                    cell.setCellValue((String) getValueAt(i-1, j));
                else if(cls.getDeclaredFields()[j].getType() == Date.class) {
                    sheet.setColumnWidth(j,3000);
                    try {
                        System.out.println(new Date(format.parse(((Date)(getValueAt(i - 1, j))).toString()).getTime()));
                        cell.setCellValue(new Date(format.parse(((Date)(getValueAt(i - 1, j))).toString()).getTime()));
                        cell.setCellStyle(cellStyle);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println(cls.getDeclaredFields()[j].getType());
                    cell.setCellValue("error");
                }
            }
        }

        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранение файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if ( fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
            try ( FileOutputStream fileOutputStream = new FileOutputStream(fileChooser.getSelectedFile() + ".xlsx") ) {
                wb.write(fileOutputStream);
                fileOutputStream.close();
            }
            catch ( IOException e ) {
                System.out.println("Произошла ошибка");
            }
        }
    }
}

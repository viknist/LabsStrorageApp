package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.entity.MonthOrgEntity;
import com.labstorageapp.manager.MonthOrgManager;
import com.labstorageapp.util.BaseForm;
import com.labstorageapp.util.CustomTableModel;
import com.labstorageapp.util.ExcelLoader;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MonthOrgConsumableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JTextField nameOrgField;
    private JTextField nameConsField;
    private JTextField monthField;
    private JTextField yearField;
    private JButton saveExcelButton;
    private JButton consumableListButton;
    private JButton journalListButton;
    private JButton monthDataButton;
    private JButton расходОрганизацийПоМесяцамButton;
    private JButton showButton;

    private MonthOrgManager monthOrgManager = Application.getInstance().getMonthOrgManager();
    private List<MonthOrgEntity> monthOrgEntityList;
    private ExcelLoader<MonthOrgEntity> excelLoader;

    private CustomTableModel<MonthOrgEntity> model;

    public MonthOrgConsumableForm(){
        setContentPane(mainPanel);

        initTable();
        initButtons();

        setVisible(true);
    }

    private void initTable(){
        table.getTableHeader().setReorderingAllowed(false);

        try {
            monthOrgEntityList = monthOrgManager.getByConsOrgMonthYear("","","","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        model = new CustomTableModel<>(MonthOrgEntity.class,
                new String[] { "ID", "ID Организации", "Организация","ID Материала", "Расходный материал", "Месяц", "Год", "Остаток", "Приход", "Списание"},
                monthOrgEntityList);
        excelLoader = new ExcelLoader<>(MonthOrgEntity.class,
                new String[] { "ID", "ID Организации", "Организация","ID Материала", "Расходный материал", "Месяц", "Год", "Остаток", "Приход", "Списание"},
                monthOrgEntityList);
        table.setModel(model);

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if(row != -1 && e.getClickCount() == 2){
                    new AddOutcomeOrgForm(MonthOrgConsumableForm.this, row, model.getRows().get(row));
                }
            }
        });
    }

    private void initButtons(){
        showButton.addActionListener(e -> {
            try {
                monthOrgEntityList = monthOrgManager.getByConsOrgMonthYear(nameConsField.getText(), nameOrgField.getText(), monthField.getText(),yearField.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            model = new CustomTableModel<>(MonthOrgEntity.class,
                    new String[] { "ID", "ID Организации", "Организация","ID Материала", "Расходный материал", "Месяц", "Год", "Остаток", "Приход", "Списание"},
                    monthOrgEntityList);
            excelLoader = new ExcelLoader<>(MonthOrgEntity.class,
                    new String[] { "ID", "ID Организации", "Организация","ID Материала", "Расходный материал", "Месяц", "Год", "Остаток", "Приход", "Списание"},
                    monthOrgEntityList);

            table.setModel(model);
            model.fireTableDataChanged();
        });

        saveExcelButton.addActionListener(e -> {
            try {
                excelLoader.saveExcel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        consumableListButton.addActionListener(e -> {
            new ConsumableListForm();
            dispose();
        });

        monthDataButton.addActionListener(e -> {
            new ShowMonthDataForm();
            dispose();
        });

        journalListButton.addActionListener(e -> {
            new JournalForm();
            dispose();
        });

    }

    public CustomTableModel<MonthOrgEntity> getModel() {
        return model;
    }

    @Override
    public int getFormWidth() {
        return 900;
    }

    @Override
    public int getFormHeight() {
        return 500;
    }
}

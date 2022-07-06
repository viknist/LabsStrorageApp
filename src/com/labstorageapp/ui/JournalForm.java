package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.entity.JournalEntity;
import com.labstorageapp.manager.ConsumableManager;
import com.labstorageapp.manager.ConsumptionManager;
import com.labstorageapp.manager.JournalManager;
import com.labstorageapp.util.BaseForm;
import com.labstorageapp.util.CustomTableModel;
import com.labstorageapp.util.ExcelLoader;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JournalForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton monthDataButton;
    private JButton journalDataButton;
    private JButton addOutcomeButton;
    private JButton showButton;
    private JTextField nameFiled;
    private JTextField orgNameField;
    private JButton saveExcelButton;
    private JButton consumableListButton;
    private JButton monthOrgButton;

    private final ConsumableManager consumableManager = Application.getInstance().getConsumableManager();
    private final JournalManager journalManager = Application.getInstance().getJournalManager();
    private final ConsumptionManager consumptionManager = Application.getInstance().getConsumptionManager();
    private List<JournalEntity> list = new ArrayList<>();
    private ExcelLoader<JournalEntity> excelLoader;
    private CustomTableModel<JournalEntity> model;

    public JournalForm(){
        setContentPane(mainPanel);

        initTable();
        initButtons();

        setVisible(true);
    }

    private void initTable(){
        table.getTableHeader().setReorderingAllowed(false);

        try {
            list = journalManager.getByNameOrg("","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        model = new CustomTableModel<>(JournalEntity.class,
                new String[] {"ID", "ID Материала", "Название Материала", "ФИО", "Организация", "Ед. измерения", "Кол-во", "Дата взятия"},
                list);
        excelLoader = new ExcelLoader<>(JournalEntity.class,
                new String[] {"ID", "ID Материала", "Название Материала", "ФИО", "Организация", "Ед. измерения", "Кол-во", "Дата взятия"},
                list);
        table.setModel(model);
    }

    private void initButtons(){
        saveExcelButton.addActionListener(e -> {
            try {
                excelLoader.saveExcel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        showButton.addActionListener(e -> {
            try {
                list = journalManager.getByNameOrg(nameFiled.getText(), orgNameField.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            model = new CustomTableModel<>(JournalEntity.class,
                    new String[]{"ID", "ID Материала", "Название Материала", "ФИО", "Организация", "Ед. измерения", "Кол-во", "Дата взятия"},
                    list);

            excelLoader = new ExcelLoader<>(JournalEntity.class,
                    new String[] {"ID", "ID Материала", "Название Материала", "ФИО", "Организация", "Ед. измерения", "Кол-во", "Дата взятия"},
                    list);

            table.setModel(model);
            model.fireTableDataChanged();
        });

        monthDataButton.addActionListener(e -> {
            new ShowMonthDataForm();
            dispose();
        });

        consumableListButton.addActionListener(e -> {
            new ConsumableListForm();
            dispose();
        });

        addOutcomeButton.addActionListener(e -> {
            new OutcomeForm(this);
        });

        monthOrgButton.addActionListener(e -> {
            new MonthOrgConsumableForm();
            dispose();
        });
    }


    @Override
    public int getFormWidth() {
        return 900;
    }

    @Override
    public int getFormHeight() {
        return 500;
    }

    public CustomTableModel<JournalEntity> getModel() {
        return model;
    }
}

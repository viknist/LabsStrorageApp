package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumableEntity;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.manager.ConsumableManager;
import com.labstorageapp.util.BaseForm;
import com.labstorageapp.util.CustomTableModel;
import com.labstorageapp.util.ExcelLoader;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class ConsumableListForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton monthDataButton;
    private JButton saveExcelButton;
    private JButton listButton;
    private JButton journalButton;
    private JButton monthOrgButton;
    private final ConsumableManager consumableManager = Application.getInstance().getConsumableManager();
    private ExcelLoader<ConsumableEntity> excelLoader;

    private CustomTableModel<ConsumableEntity> model;

    public ConsumableListForm(){
        setContentPane(mainPanel);

        initTable();
        initButtons();

        setVisible(true);
    }

    private void initTable(){
        table.getTableHeader().setReorderingAllowed(false);
        try {
            model = new CustomTableModel<>(ConsumableEntity.class,
                    new String[] { "ID", "Название", "ед. изм.", "Назначение"},
                    consumableManager.getAll()
                    );
            table.setModel(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            excelLoader = new ExcelLoader<>(ConsumableEntity.class,
                    new String[] { "ID", "Название", "ед. изм.", "Назначение"},
                    consumableManager.getAll()
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initButtons(){
        monthDataButton.addActionListener(e -> {
            new ShowMonthDataForm();
            dispose();
        });

        saveExcelButton.addActionListener(e -> {
            try {
                excelLoader.saveExcel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        journalButton.addActionListener(e -> {
            new JournalForm();
            dispose();
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
}

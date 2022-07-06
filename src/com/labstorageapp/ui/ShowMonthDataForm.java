package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumableEntity;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.entity.MonthOrgEntity;
import com.labstorageapp.entity.MonthYearEntity;
import com.labstorageapp.manager.ConsumableManager;
import com.labstorageapp.manager.ConsumptionManager;
import com.labstorageapp.manager.MonthOrgManager;
import com.labstorageapp.util.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowMonthDataForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JTextField nameField;
    private JTextField monthField;
    private JTextField yearField;
    private JButton showButton;
    private JButton addIncomeButton;
    private JButton addConsumableButton;
    private JButton newMonthFormButton;
    private JButton saveExcelButton;
    private JButton consumableListButton;
    private JButton journalListButton;
    private JButton monthDataButton;
    private JButton monthOrgButton;

    private final ConsumptionManager consumptionManager = Application.getInstance().getConsumptionManager();
    private final ConsumableManager consumableManager = Application.getInstance().getConsumableManager();
    private final MonthOrgManager monthOrgManager = Application.getInstance().getMonthOrgManager();

    private List<ConsumptionEntity> consumptionEntityList;
    private ExcelLoader<ConsumptionEntity> excelLoader;

    private CustomTableModel<ConsumptionEntity> model;

    public ShowMonthDataForm() {
        setContentPane(mainPanel);

        initTable();
        initButtons();

        setVisible(true);
    }

    private void initTable(){
        table.getTableHeader().setReorderingAllowed(false);
        try {
            consumptionEntityList = consumptionManager.getByNameMonthYear("", "", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        model = new CustomTableModel<>(ConsumptionEntity.class,
                new String[] { "ID", "Название", "ед. изм.", "Назначение", "Месяц", "Год", "Остаток", "Приход", "Расход"},
                consumptionEntityList);
        excelLoader = new ExcelLoader<>(ConsumptionEntity.class,
                new String[] { "ID", "Название", "ед. изм.", "Назначение", "Месяц", "Год", "Остаток", "Приход", "Расход"},
                consumptionEntityList);
        table.setModel(model);
    }

    private void initButtons(){
        showButton.addActionListener(e -> {
            try {
                consumptionEntityList = consumptionManager.getByNameMonthYear(nameField.getText(),monthField.getText(), yearField.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            model = new CustomTableModel<>(ConsumptionEntity.class,
                    new String[]{"ID", "Название", "ед. изм.", "Назначение", "Месяц", "Год", "Остаток", "Приход", "Расход"},
                    consumptionEntityList);

            excelLoader = new ExcelLoader<>(ConsumptionEntity.class,
                    new String[] { "ID", "Название", "ед. изм.", "Назначение", "Месяц", "Год", "Остаток", "Приход", "Расход"},
                    consumptionEntityList);

            table.setModel(model);
            model.fireTableDataChanged();
        });

        addIncomeButton.addActionListener(e -> {
            new IncomeForm(this);
        });

        addConsumableButton.addActionListener(e -> {
            new NewConsumableForm(this);
        });

        newMonthFormButton.addActionListener(e -> {
            if(DialogUtil.showConfirm("Создать поля для расходных материалов текущего месяца?"))
            try {
                List<ConsumableEntity> consumableEntityList = consumableManager.getAll();

                MonthYearEntity monthYearEntity = PreviousMonthUtil.getPreviousMonth();
                System.out.println(monthYearEntity.getMonth() + " " + monthYearEntity.getYear());
                for(ConsumableEntity consumable : consumableEntityList){

                    List<ConsumptionEntity> list = consumptionManager.getByNameMonthYear(consumable.getName(),monthYearEntity.getMonth(), monthYearEntity.getYear());
                    consumptionManager.addByConsumbleIncomeOutcomeBalance(consumable, list.get(0).getBalance(), list.get(0).getIncome(), list.get(0).getOutcome());

                }

                List<MonthOrgEntity> list1 = monthOrgManager.getByConsOrgMonthYear("", "", monthYearEntity.getMonth(),monthYearEntity.getYear());
                for (int i = 0; i < list1.size(); i++)
                    monthOrgManager.addByEntity(list1.get(i));

                DialogUtil.showWarn("Успешно добавлено");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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

        journalListButton.addActionListener(e -> {
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

    public CustomTableModel<ConsumptionEntity> getModel() {
        return model;
    }

}

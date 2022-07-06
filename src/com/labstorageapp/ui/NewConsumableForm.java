package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumableEntity;
import com.labstorageapp.manager.ConsumableManager;
import com.labstorageapp.manager.ConsumptionManager;
import com.labstorageapp.util.BaseSubForm;
import com.labstorageapp.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class NewConsumableForm extends BaseSubForm<ShowMonthDataForm> {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField unitTypeField;
    private JTextField purposeField;
    private JButton addConsumableButton;
    private JButton backButton;

    private final ConsumableManager consumableManager = Application.getInstance().getConsumableManager();
    private final ConsumptionManager consumptionManager = Application.getInstance().getConsumptionManager();

    public NewConsumableForm(ShowMonthDataForm mainForm) {
        super(mainForm);
        setContentPane(mainPanel);

        initButtons();
        setVisible(true);
    }

    private void initButtons(){
        backButton.addActionListener(e -> {
            mainForm.getModel().fireTableDataChanged();
            closeSubForm();
        });

        addConsumableButton.addActionListener(e -> {
            try {
                ConsumableEntity consumableEntity = new ConsumableEntity(nameField.getText(), unitTypeField.getText(), purposeField.getText());
                consumableManager.add(consumableEntity);
                consumptionManager.addByConsumble(consumableEntity);
                DialogUtil.showInfo("Успешно сохранено");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @Override
    public int getFormWidth() {
        return 600;
    }

    @Override
    public int getFormHeight() {
        return 400;
    }
}

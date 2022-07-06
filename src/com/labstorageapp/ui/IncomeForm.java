package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.SupplyEntity;
import com.labstorageapp.manager.ConsumableManager;
import com.labstorageapp.manager.ConsumptionManager;
import com.labstorageapp.manager.SupplyManager;
import com.labstorageapp.util.BaseForm;
import com.labstorageapp.util.BaseSubForm;
import com.labstorageapp.util.DialogUtil;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;

public class IncomeForm extends BaseSubForm<ShowMonthDataForm> {
    private final ConsumptionManager consumptionManager = Application.getInstance().getConsumptionManager();
    private final SupplyManager supplyManager = Application.getInstance().getSupplyManager();
    private final ConsumableManager consumableManager = Application.getInstance().getConsumableManager();
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField amountField;
    private JButton addButton;
    private JButton backButton;
    private JTextField providerField;

    public IncomeForm(ShowMonthDataForm mainForm){
        super(mainForm);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    private void initButtons(){
        addButton.addActionListener(e -> {
            try {
                supplyManager.add(new SupplyEntity(
                        consumableManager.getByName(nameField.getText()).getId(),
                        Integer.parseInt(amountField.getText()),
                        providerField.getText(),
                        new Date(System.currentTimeMillis())
                ));
                consumptionManager.addIncomeAmount(nameField.getText(), Integer.parseInt(amountField.getText()));
                DialogUtil.showInfo("Успешно добавлено");
                mainForm.getModel().fireTableDataChanged();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                DialogUtil.showError("Возникла ошибка. Обратитесь к программисту");
            }
        });

        backButton.addActionListener(e -> {
            mainForm.getModel().fireTableDataChanged();
            closeSubForm();
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

package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.MonthOrgEntity;
import com.labstorageapp.manager.MonthOrgManager;
import com.labstorageapp.util.BaseSubForm;
import com.labstorageapp.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class AddOutcomeOrgForm extends BaseSubForm<MonthOrgConsumableForm> {
    private JTextField nameOrgField;
    private JTextField amountField;
    private JButton backButton;
    private JButton saveButton;
    private JPanel mainPanel;
    private final MonthOrgManager monthOrgManager = Application.getInstance().getMonthOrgManager();

    private MonthOrgEntity monthOrgEntity;
    private int row;

    public AddOutcomeOrgForm(MonthOrgConsumableForm mainForm, int row, MonthOrgEntity monthOrgEntity){
        super(mainForm);
        this.monthOrgEntity = monthOrgEntity;
        this.row = row;
        setContentPane(mainPanel);
        initButtons();
        initField();
        setVisible(true);
    }

    private void initButtons(){
        backButton.addActionListener(e -> {
            closeSubForm();
        });

        saveButton.addActionListener(e -> {
            try {
                monthOrgEntity.setOutcome(Integer.parseInt(amountField.getText()));
                monthOrgManager.addOutcomeAmount(monthOrgEntity.getIdOrg(), Integer.parseInt(amountField.getText()));
                mainForm.getModel().getRows().set(row, monthOrgEntity);
                mainForm.getModel().fireTableDataChanged();
                closeSubForm();
            } catch (SQLException throwables) {
                DialogUtil.showError("Что-то пошло не так");
                throwables.printStackTrace();
            }
        });
    }

    private void initField(){
        nameOrgField.setText(monthOrgEntity.getNameOrg());
        nameOrgField.setEnabled(false);
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

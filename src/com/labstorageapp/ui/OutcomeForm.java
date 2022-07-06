package com.labstorageapp.ui;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumableEntity;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.entity.JournalEntity;
import com.labstorageapp.entity.OrganisationEntity;
import com.labstorageapp.manager.*;
import com.labstorageapp.util.BaseSubForm;
import com.labstorageapp.util.DialogUtil;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class OutcomeForm extends BaseSubForm<JournalForm> {


    private JPanel mainPanel;
    private JTextField nameConsField;
    private JTextField amountField;
    private JTextField nameTakerField;
    private JTextField nameOrgField;
    private JButton backButton;
    private JButton saveButton;
    private JCheckBox isNewOrgBox;
    private OrganisationManager organisationManager = Application.getInstance().getOrganisationManager();
    private ConsumptionManager consumptionManager = Application.getInstance().getConsumptionManager();
    private JournalManager journalManager = Application.getInstance().getJournalManager();
    private ConsumableManager consumableManager = Application.getInstance().getConsumableManager();
    private MonthOrgManager monthOrgManager = Application.getInstance().getMonthOrgManager();
    private Calendar calendar = Calendar.getInstance();
    private String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };


    public OutcomeForm(JournalForm mainForm) {
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

        saveButton.addActionListener(e -> {
            if(isNewOrgBox.isSelected()){
                try {
                    organisationManager.add(new OrganisationEntity(nameOrgField.getText()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    DialogUtil.showError("Данная организация уже существует.");
                }
            }

            try {
                List<ConsumptionEntity> list = consumptionManager.getByNameMonthYear(nameConsField.getText(), monthNames[calendar.get(Calendar.MONTH)], String.valueOf(calendar.get(Calendar.YEAR)));
                int balance = list.get(0).getBalance() + list.get(0).getIncome() - list.get(0).getOutcome();
                if(balance < Integer.parseInt(amountField.getText()) && balance != 0){
                    amountField.setText(String.valueOf(balance));
                    DialogUtil.showError("Было отдано " + amountField.getText() + " шт. т.к. на складе недостаточно расходного материала." );
                }

                if(balance != 0) {
                    ConsumableEntity consumable = consumableManager.getByName(nameConsField.getText());
                    JournalEntity journalEntity = new JournalEntity(
                            consumable.getId(),
                            consumable.getName(),
                            nameTakerField.getText(),
                            organisationManager.getByName(nameOrgField.getText()).getNameOrg(),
                            consumable.getUnitType(),
                            Integer.parseInt(amountField.getText()),
                            new Date(System.currentTimeMillis())
                    );

                    journalManager.add(journalEntity);
                    consumptionManager.addOutcomeAmount(nameConsField.getText(), Integer.parseInt(amountField.getText()));
                    if(monthOrgManager.getByConsOrgMonthYear(journalEntity.getNameConsumable(),journalEntity.getOrgTaker(),monthNames[calendar.get(Calendar.MONTH)], String.valueOf(calendar.get(Calendar.YEAR))) == null)
                        monthOrgManager.addByJournal(journalEntity);
                    else
                        monthOrgManager.addIncomeAmount(journalEntity, journalEntity.getAmount());

                    DialogUtil.showInfo("Успешно добавлено");
                }
                else{
                    DialogUtil.showError("Расходный материал отсутсвует на складе");
                }
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
        return 300;
    }
}

package com.labstorageapp;

import com.labstorageapp.manager.*;
import com.labstorageapp.ui.ShowMonthDataForm;
import com.labstorageapp.util.BaseForm;
import com.labstorageapp.util.MysqlDatabase;

import javax.swing.*;
import java.sql.Connection;
import java.util.Calendar;

public class Application
{
    private static Application instance;
    private final MysqlDatabase database = new MysqlDatabase("localhost", "", "", "");

    private final ConsumptionManager consumptionManager = new ConsumptionManager(database);
    private final ConsumableManager consumableManager = new ConsumableManager(database);
    private final JournalManager journalManager = new JournalManager(database);
    private final SupplyManager supplyManager = new SupplyManager(database);
    private final OrganisationManager organisationManager = new OrganisationManager(database);
    private final MonthOrgManager monthOrgManager = new MonthOrgManager(database);

    private Application(){


        instance = this;
        initDatabase();
        initUi();

        new ShowMonthDataForm();
    }

    private void initDatabase(){
        try(Connection c = database.getConnection()) {

        } catch (Exception e) {
            System.out.println("Ошибка подключения к бд");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void initUi()
    {
        BaseForm.setBaseApplicationTitle("Складской учет МЛ" +
                "Ц");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new Application(); }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public ConsumptionManager getConsumptionManager() {
        return consumptionManager;
    }

    public ConsumableManager getConsumableManager() { return consumableManager; }

    public SupplyManager getSupplyManager() { return supplyManager; }

    public JournalManager getJournalManager() { return journalManager; }

    public OrganisationManager getOrganisationManager() { return organisationManager; }

    public MonthOrgManager getMonthOrgManager() { return monthOrgManager; }

    public static Application getInstance() {
        return instance;
    }
}

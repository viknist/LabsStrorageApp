package com.labstorageapp.manager;

import com.labstorageapp.Application;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.entity.JournalEntity;
import com.labstorageapp.entity.MonthOrgEntity;
import com.labstorageapp.entity.OrganisationEntity;
import com.labstorageapp.util.MysqlDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthOrgManager {
    private MysqlDatabase database;
    private Calendar calendar = Calendar.getInstance();
    private String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };


    public MonthOrgManager(MysqlDatabase database) {
        this.database = database;
    }

    public void addByJournal(JournalEntity journalEntity) throws SQLException {
        try (Connection c = database.getConnection()){
            OrganisationManager organisationManager = Application.getInstance().getOrganisationManager();

            String sql = "INSERT INTO monthorg(idConsumable, idOrg, Month, Year, balanceOrg, incomeOrg, outcomeOrg) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, journalEntity.getIdConsumable());
            ps.setInt(2, organisationManager.getByName(journalEntity.getOrgTaker()).getIdOrg());
            ps.setString(3, monthNames[calendar.get(Calendar.MONTH)]);
            ps.setString(4, String.valueOf(calendar.get(Calendar.YEAR)));
            ps.setInt(5, 0);
            ps.setInt(6, journalEntity.getAmount());
            ps.setInt(7, 0);

            ps.executeUpdate();
        }
    }

    public void addByEntity(MonthOrgEntity monthOrgEntity) throws SQLException {
        try (Connection c = database.getConnection()){
            OrganisationManager organisationManager = Application.getInstance().getOrganisationManager();

            String sql = "INSERT INTO monthorg(idConsumable, idOrg, Month, Year, balanceOrg, incomeOrg, outcomeOrg) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, monthOrgEntity.getIdConsumable());
            ps.setInt(2, monthOrgEntity.getIdOrg());
            ps.setString(3, monthNames[calendar.get(Calendar.MONTH)]);
            ps.setString(4, String.valueOf(calendar.get(Calendar.YEAR)));
            ps.setInt(5, monthOrgEntity.getBalance() + monthOrgEntity.getIncome() - monthOrgEntity.getOutcome());
            ps.setInt(6, 0);
            ps.setInt(7, 0);

            ps.executeUpdate();
        }
    }

    public List<MonthOrgEntity> getByConsOrgMonthYear(String nameCons, String nameOrg, String month, String year) throws SQLException {
        try (Connection c = database.getConnection()){
            String sql = "SELECT mo.*, ce.name, org.nameOrg " +
                    "FROM monthOrg AS mo, consumable AS ce, organisation AS org " +
                    "WHERE mo.idConsumable = ce.idConsumable AND mo.idOrg = org.idOrg " +
                    "AND ce.name LIKE ? AND org.nameOrg LIKE ? AND mo.month LIKE ? AND mo.year LIKE ? " +
                    "ORDER BY mo.year, mo.month, org.nameOrg, ce.name";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + nameCons + "%");
            ps.setString(2, "%" + nameOrg + "%");
            ps.setString(3, "%" + month + "%");
            ps.setString(4, "%" + year + "%");

            ResultSet resultSet = ps.executeQuery();
            List <MonthOrgEntity> list = new ArrayList<>();

            while (resultSet.next()){
                list.add(new MonthOrgEntity(
                        resultSet.getInt("idMonthOrg"),
                        resultSet.getInt("idOrg"),
                        resultSet.getString("nameOrg"),
                        resultSet.getInt("idConsumable"),
                        resultSet.getString("name"),
                        resultSet.getString("month"),
                        resultSet.getString("year"),
                        resultSet.getInt("balanceOrg"),
                        resultSet.getInt("incomeOrg"),
                        resultSet.getInt("outcomeOrg")
                ));
            }
            if (list.size() > 0)
                return list;
            else
                return null;
        }

    }

    public void addIncomeAmount(JournalEntity journalEntity, int amount) throws SQLException {
        OrganisationManager organisationManager = Application.getInstance().getOrganisationManager();
        try (Connection c = database.getConnection()){
            String sql = "UPDATE monthOrg " +
                    "SET incomeOrg = incomeOrg + ? " +
                    "WHERE idConsumable = ? AND idOrg = ? AND month LIKE ? AND year LIKE ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setInt(2, journalEntity.getIdConsumable());
            ps.setInt(3, organisationManager.getByName(journalEntity.getOrgTaker()).getIdOrg());
            ps.setString(4, "%" + monthNames[calendar.get(Calendar.MONTH)] + "%");
            ps.setString(5, "%" + calendar.get(Calendar.YEAR) + "%");

            ps.executeUpdate();
        }
    }

    public void addOutcomeAmount(int id, int amount) throws SQLException {
        OrganisationManager organisationManager = Application.getInstance().getOrganisationManager();
        try (Connection c = database.getConnection()){
            String sql = "UPDATE monthOrg " +
                    "SET outcomeOrg = outcomeOrg + ? " +
                    "WHERE idMonthOrg = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }
}

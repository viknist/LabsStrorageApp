package com.labstorageapp.manager;

import com.labstorageapp.Application;
import com.labstorageapp.entity.JournalEntity;
import com.labstorageapp.util.MysqlDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * private int idTake;
 *     private int idConsumable;
 *     private String nameConsumable;
 *     private String nameTaker;
 *     private String orgTaker;
 *     private String unitType;
 *     private int amount;
 *     private Date dateTaken;
 */

public class JournalManager {
    public MysqlDatabase database;

    private Calendar calendar = Calendar.getInstance();
    private String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    public JournalManager(MysqlDatabase database){
        this.database = database;
    }

    public void add(JournalEntity journal) throws SQLException{
        OrganisationManager organisationManager = Application.getInstance().getOrganisationManager();
        try (Connection c = database.getConnection()){
            String sql = "INSERT INTO takefromstorage(idConsumable, nameTaker, idOrgTaker, amount, dateTaken) VALUES(?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, journal.getIdConsumable());
            ps.setString(2, journal.getNameTaker());
            ps.setInt(3, organisationManager.getByName(journal.getOrgTaker()).getIdOrg());
            ps.setInt(4, journal.getAmount());
            ps.setDate(5, journal.getDateTaken());

            ps.executeUpdate();
        }

    }

    public List<JournalEntity> getByNameOrg(String name, String nameOrg) throws SQLException {
        try(Connection c = database.getConnection()) {
            String sql = "SELECT t.idTake, t.idConsumable, ce.name, t.nameTaker, o.nameOrg, ce.unitType, t.amount, t.dateTaken " +
                    "FROM takefromstorage AS t, consumable AS ce, organisation AS o " +
                    "WHERE t.idConsumable = ce.idConsumable AND t.idOrgTaker = o.idOrg " +
                    "AND ce.name like ? AND o.nameOrg like ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + nameOrg + "%");

            ResultSet resultSet = ps.executeQuery();
            List<JournalEntity> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new JournalEntity(
                        resultSet.getInt("idTake"),
                        resultSet.getInt("idConsumable"),
                        resultSet.getString("name"),
                        resultSet.getString("nameTaker"),
                        resultSet.getString("nameOrg"),
                        resultSet.getString("unitType"),
                        resultSet.getInt("amount"),
                        resultSet.getDate("dateTaken")
                ));
            }
            return list;
        }
    }
}

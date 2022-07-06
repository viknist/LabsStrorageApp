package com.labstorageapp.manager;

import com.labstorageapp.entity.ConsumableEntity;
import com.labstorageapp.entity.ConsumptionEntity;
import com.labstorageapp.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
private int id;
private String month;
private String year;
private int balance;
private int income;
private int outcome;
**/
public class ConsumptionManager {
    public MysqlDatabase database;

    Calendar calendar = Calendar.getInstance();
    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    public ConsumptionManager(MysqlDatabase database) {
        this.database = database;
    }

    public void addByConsumble(ConsumableEntity consumable) throws SQLException {
        try(Connection c = database.getConnection()) {
            String sql = "INSERT INTO consumption(idConsumable, month, year, balance, income, outcome) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, consumable.getId());
            ps.setString(2, monthNames[calendar.get(Calendar.MONTH)]);
            ps.setString(3, String.valueOf(calendar.get(Calendar.YEAR)));
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);

            ps.executeUpdate();
        }
    }

    public void addByConsumbleIncomeOutcomeBalance(ConsumableEntity consumable, int balance, int income, int outcome) throws SQLException {
        try(Connection c = database.getConnection()) {
            String sql = "INSERT INTO consumption(idConsumable, month, year, balance, income, outcome) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, consumable.getId());
            ps.setString(2, monthNames[calendar.get(Calendar.MONTH)]);
            ps.setString(3, String.valueOf(calendar.get(Calendar.YEAR)));
            ps.setInt(4, balance + income - outcome);
            ps.setInt(5, 0);
            ps.setInt(6, 0);

            ps.executeUpdate();
        }
    }

    public List<ConsumptionEntity> getByNameMonthYear(String name, String month, String year) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT ce.*, cn.month, cn.year, cn.balance, cn.income, cn.outcome " +
                    "FROM consumption AS cn, consumable AS ce " +
                    "WHERE ce.idConsumable = cn.idConsumable AND name LIKE ? AND month LIKE ? AND year LIKE ? " +
                    "ORDER BY  cn.month, ce.name, cn.year";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + month + "%");
            ps.setString(3, "%" + year + "%");

            ResultSet resultSet = ps.executeQuery();
            List<ConsumptionEntity> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new ConsumptionEntity(
                        resultSet.getInt("idConsumable"),
                        resultSet.getString("name"),
                        resultSet.getString("unitType"),
                        resultSet.getString("purpose"),
                        resultSet.getString("month"),
                        resultSet.getString("year"),
                        resultSet.getInt("balance"),
                        resultSet.getInt("income"),
                        resultSet.getInt("outcome")
                ));
            }
            return list;
        }
    }

    public void addIncomeAmount (String name, int amount) throws SQLException{
        try(Connection c = database.getConnection()){
            String sql = "UPDATE consumption " +
                    "SET income = income + ? " +
                    "WHERE idConsumable = " +
                    "(SELECT idConsumable " +
                    "from consumable " +
                    "where name like ?)" +
                    "and month like ? " +
                    "and year like ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setString(2, "%" + name + "%");
            ps.setString(3, "%" + monthNames[calendar.get(Calendar.MONTH)] + "%");
            ps.setString(4, "%" + calendar.get(Calendar.YEAR) + "%");

            ps.executeUpdate();
        }
    }

    public void addOutcomeAmount (String name, int amount) throws SQLException{
        try(Connection c = database.getConnection()){
            String sql = "UPDATE consumption " +
                    "SET outcome = outcome + ? " +
                    "WHERE idConsumable = " +
                    "(SELECT idConsumable " +
                    "from consumable " +
                    "where name like ?)" +
                    "and month like ? " +
                    "and year like ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setString(2, "%" + name + "%");
            ps.setString(3, "%" + monthNames[calendar.get(Calendar.MONTH)] + "%");
            ps.setString(4, "%" + calendar.get(Calendar.YEAR) + "%");

            ps.executeUpdate();
        }
    }

//    public void addNewMonth() throws SQLException{
//        try(Connection c = database.getConnection()) {
//            String sql = "INSERT INTO consumption VALUES (?,?,?,?)"
//
//        }
//    }
}

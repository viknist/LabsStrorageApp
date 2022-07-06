package com.labstorageapp.manager;

import com.labstorageapp.entity.ConsumableEntity;
import com.labstorageapp.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConsumableManager {
    public MysqlDatabase database;
    public ConsumableManager(MysqlDatabase database){
        this.database = database;
    }

    public void add(ConsumableEntity consumable) throws SQLException {
        try (Connection c = database.getConnection()){
            String sql = "INSERT INTO consumable(name, unitType, purpose) VALUES(?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, consumable.getName());
            ps.setString(2, consumable.getUnitType());
            ps.setString(3, consumable.getPurpose());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                consumable.setId(keys.getInt(1));
            }
        }
    }

    public ConsumableEntity getByName(String name) throws SQLException{
        try(Connection c = database.getConnection()) {
            String sql = "SELECT * FROM consumable where name like ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1,"%" + name + "%");
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return new ConsumableEntity(
                        resultSet.getInt("idConsumable"),
                        resultSet.getString("name"),
                        resultSet.getString("unitType"),
                        resultSet.getString("purpose")
                );
            }
            return null;
        }
    }

    public List<ConsumableEntity> getAll() throws SQLException{
        try(Connection c = database.getConnection()){
            String sql = "SELECT * FROM consumable";

            Statement s = c.createStatement();
            ResultSet resultSet =s.executeQuery(sql);

            List<ConsumableEntity> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new ConsumableEntity(
                    resultSet.getInt("idConsumable"),
                    resultSet.getString("name"),
                    resultSet.getString("unitType"),
                    resultSet.getString("purpose")
                ));
            }
            return list;
        }
    }
}

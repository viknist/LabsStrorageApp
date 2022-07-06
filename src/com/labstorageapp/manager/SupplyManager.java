package com.labstorageapp.manager;

import com.labstorageapp.Application;
import com.labstorageapp.entity.SupplyEntity;
import com.labstorageapp.util.MysqlDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplyManager {
    private MysqlDatabase database;

    public SupplyManager(MysqlDatabase database) {
        this.database = database;
    }

    public void add(SupplyEntity supply) throws SQLException {
        try(Connection c = database.getConnection()) {
            String sql = "INSERT INTO supply(idConsumable, amount, provider, supplyDate) VALUES (?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, supply.getIdConsumable());
            ps.setInt(2, supply.getAmount());
            ps.setString(3, supply.getProvider());
            ps.setDate(4, supply.getDate());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                supply.setIdSupply(keys.getInt(1));
            }
        }
    }
}

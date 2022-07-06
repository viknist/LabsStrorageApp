package com.labstorageapp.manager;

import com.labstorageapp.entity.OrganisationEntity;
import com.labstorageapp.util.MysqlDatabase;

import java.sql.*;

public class OrganisationManager {
    private MysqlDatabase database;

    public OrganisationManager(MysqlDatabase database){
        this.database = database;
    }

    public void add(OrganisationEntity organisation) throws SQLException {
        try (Connection c = database.getConnection()){
            String sql = "INSERT INTO organisation (nameOrg) VALUES(?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, organisation.getNameOrg());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                organisation.setIdOrg(keys.getInt(1));
            }
        }
    }

    public OrganisationEntity getByName(String nameOrg) throws SQLException {
        try (Connection c = database.getConnection()){
            String sql = "SELECT * from organisation where nameOrg like ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + nameOrg + "%");

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                OrganisationEntity organisation = new OrganisationEntity(resultSet.getInt("idOrg"),resultSet.getString("nameOrg"));
                return organisation;
            }
            else return null;
        }

    }
}

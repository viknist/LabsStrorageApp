package com.labstorageapp.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDatabase
{
    private String address;
    private  int port;
    private String database;
    private String user;
    private String password;

    private MysqlDataSource source;

    public MysqlDatabase(String address, int port, String database, String user, String password) {
        this.address = address;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public MysqlDatabase(String address, String database, String user, String password) {
        this(address, 3306, database, user, password);
    }

    public Connection getConnection() throws SQLException {
        if(source == null){
            source = new MysqlDataSource();

            source.setServerName((address));
            source.setPort(port);
            source.setDatabaseName(database);
            source.setUser(user);
            source.setPassword(password);

            source.setCharacterEncoding("UTF-8");
            source.setServerTimezone("Europe/Moscow");
        }
        return source.getConnection();
    }
}


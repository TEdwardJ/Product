package com.study.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {
    private String url;
    private String user;
    private String password;
    private String server;
    private String database;
    private int port;

    public DbProperties() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");

        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        server = props.getProperty("db.serverName");
        database = props.getProperty("db.databaseName");
        port = Integer.valueOf(props.getProperty("db.port"));
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getServer() {
        return server;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }
}

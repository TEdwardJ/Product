package com.study;

import com.study.dao.DbProperties;
import com.study.dao.PostgresUserDAO;
import com.study.security.AuthenticationService;
import com.study.security.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Random;

public class RMain {
    private static PostgresUserDAO userDAO = new PostgresUserDAO(getDataSource());
    private static Random r = new Random();
    private static RandomStringGenerator rs = new RandomStringGenerator
            .Builder()
            .withinRange('a','z')
            .usingRandom(r::nextInt)
            .build();


    public static void main(String[] args) {

        for (int i = 0; i < 11; i++) {
            System.out.println(rs.generate(10));
        }
        User user = userDAO.getOne("USER");
        encrypt(user);
        user.setPassword(md5Apache("user"+user.getSole()));
        userDAO.update(user);
    }

    private static void encrypt(User user) {
        String sole = rs.generate(10);
        user.setSole(sole);

    }

    public static DataSource getDataSource(){
        DbProperties dbProperties = new DbProperties();
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(dbProperties.getServer());
        ds.setDatabaseName(dbProperties.getDatabase());
        ds.setPortNumber(dbProperties.getPort());
        ds.setUser(dbProperties.getUser());
        ds.setPassword(dbProperties.getPassword());
        return ds;
    }

    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);

        return md5Hex;
    }
}

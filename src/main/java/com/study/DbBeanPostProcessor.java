package com.study;

import com.study.dao.DbProperties;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DbBeanPostProcessor implements BeanPostProcessor {
    public DataSource configureDataSource(DataSource dataSource) {
        String dbUrl = System.getenv("DATABASE_URL");

            PGSimpleDataSource ds = (PGSimpleDataSource) dataSource;//new PGSimpleDataSource();
        if (dbUrl != null) {

            try {
                URI dbUri = new URI(dbUrl);
                ds.setServerName(dbUri.getHost());
                ds.setDatabaseName(dbUri.getPath().substring(1));
                ds.setPortNumber(dbUri.getPort());
                String userInfo = dbUri.getUserInfo();
                ds.setUser(userInfo.substring(0,userInfo.indexOf(":")));
                ds.setPassword(userInfo.substring(userInfo.indexOf(":")+1));

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            return ds;

        }else{
            DbProperties dbProperties = new DbProperties();//(DbProperties)context.getBean("dbProperties");
            ds.setServerName(dbProperties.getServer());
            ds.setDatabaseName(dbProperties.getDatabase());
            ds.setPortNumber(dbProperties.getPort());
            ds.setUser(dbProperties.getUser());
            ds.setPassword(dbProperties.getPassword());
        }
        return dataSource;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) {

        return o;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) {
        if (s.equals("dataSource")) {
            System.out.println("post processor started!!!");
            o = configureDataSource((DataSource) o);
        }
        return o;
    }
}

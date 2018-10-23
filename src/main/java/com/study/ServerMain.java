package com.study;

import com.study.dao.DbProperties;
import com.study.dao.JDBCProductDao;
import com.study.dao.JDBCUserDao;
import com.study.security.AuthenticationService;
import com.study.security.DefaultAuthenticationService;
import com.study.service.DefaultProductService;
import com.study.service.ProductService;
import com.study.service.DefaultUserService;
import com.study.web.filter.AdminSecurityFilter;
import com.study.web.filter.GuestSecurityFilter;
import com.study.web.filter.UserSecurityFilter;
import com.study.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.util.EnumSet;

public class ServerMain {

    public static void main(String[] args) throws Exception {
        DataSource dataSource = getDataSource();
        ProductService productService = new DefaultProductService(new JDBCProductDao(dataSource));
        DefaultUserService userService = new DefaultUserService(new JDBCUserDao(dataSource));

        AuthenticationService authService = new DefaultAuthenticationService(userService);

        ProductListServlet productListServlet = new ProductListServlet(productService);

        AddProductServlet addNewServlet = new AddProductServlet(productService);

        ProductInfoServlet productInfoServlet = new ProductInfoServlet(productService);

        DeleteServlet deleteServlet = new DeleteServlet(productService);

        ProductEditServlet productEditServlet = new ProductEditServlet(productService);

        CartListServlet cartListServlet = new CartListServlet(productService);
        cartListServlet.setAuthService(authService);

        AddCartItemServlet addCartItemServlet = new AddCartItemServlet(productService);
        addCartItemServlet.setAuthService(authService);

        LoginServlet loginServlet = new LoginServlet();


        loginServlet.setAuthService(authService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(productListServlet), "/*");
        context.addServlet(new ServletHolder(addNewServlet), "/product/add/*");
        context.addServlet(new ServletHolder(productInfoServlet), "/product/info/*");
        context.addServlet(new ServletHolder(deleteServlet), "/product/delete/*");
        context.addServlet(new ServletHolder(productEditServlet), "/product/edit/*");
        context.addServlet(new ServletHolder(loginServlet), "/login/*");
        context.addServlet(new ServletHolder(cartListServlet), "/cart/*");
        context.addServlet(new ServletHolder(addCartItemServlet), "/cart/add/*");

        AdminSecurityFilter adminRoleFilter = new AdminSecurityFilter(authService);
        UserSecurityFilter userRoleFilter = new UserSecurityFilter(authService);
        GuestSecurityFilter guestRoleFilter = new GuestSecurityFilter(authService);
        context.addFilter(new FilterHolder(adminRoleFilter),"/product/edit/*",EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(adminRoleFilter),"/product/delete/*",EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(adminRoleFilter),"/product/add/*",EnumSet.of(DispatcherType.REQUEST));
        //context.addFilter(new FilterHolder(adminRoleFilter),"/product/edit",EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(guestRoleFilter),"/login",EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(userRoleFilter),"/",EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(userRoleFilter),"/cart/add/*",EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();

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
}

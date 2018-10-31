package com.study;

import com.study.dao.DbProperties;
import com.study.security.AuthenticationService;
import com.study.security.DefaultAuthenticationService;
import com.study.service.DefaultProductService;
import com.study.service.ProductService;
import com.study.web.filter.AdminSecurityFilter;
import com.study.web.filter.GuestSecurityFilter;
import com.study.web.filter.UserSecurityFilter;
import com.study.web.servlet.*;
import edu.eteslenko.ioc.ApplicationContext;
import edu.eteslenko.ioc.ClassPathApplicationContext;

import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;
import java.util.EnumSet;
import java.util.Optional;

public class ServerMain {


    public static void main(String[] args) throws Exception {
/*        System.out.println(System.getenv("PORT")+" is the port");

        Integer port = Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElse("8080"));
        ClassPathApplicationContext xmlContext = new ClassPathApplicationContext("conf.xml");
        ProductService productService = (DefaultProductService)xmlContext.getBean("productService");//new DefaultProductService(productDao);

        AuthenticationService authService = (DefaultAuthenticationService)xmlContext.getBean("authenticationService");//new DefaultAuthenticationService(userService);

        ProductListServlet productListServlet = (ProductListServlet)xmlContext.getBean("productListServlet");//new ProductListServlet(productService);

        AddProductServlet addNewServlet = (AddProductServlet)xmlContext.getBean("addProductServlet");//new ProductListServlet(productService);

        ProductInfoServlet productInfoServlet = (ProductInfoServlet)xmlContext.getBean("productInfoServlet");//new ProductListServlet(productService);

        DeleteServlet deleteServlet = (DeleteServlet)xmlContext.getBean("deleteServlet");//new DeleteServlet(productService);

        ProductEditServlet productEditServlet = (ProductEditServlet)xmlContext.getBean("productEditServlet");;

        CartListServlet cartListServlet = (CartListServlet)xmlContext.getBean("cartListServlet");//new CartListServlet(productService);

        AddCartItemServlet addCartItemServlet = (AddCartItemServlet)xmlContext.getBean("addCartItemServlet");// new AddCartItemServlet(productService);

        LoginServlet loginServlet = (LoginServlet)xmlContext.getBean(LoginServlet.class);

        //loginServlet.setAuthService(authService);

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
        context.addFilter(new FilterHolder(userRoleFilter),"/cart/",EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(userRoleFilter),"/cart/add/*",EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(port);
        server.setHandler(context);

        server.start();*/
    }

    
}

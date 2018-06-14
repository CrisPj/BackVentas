package com.pythonteam.dao;

import com.pythonteam.models.OrderGet;
import com.pythonteam.models.Product;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.ArrayList;

public interface OrderDao {

    @SqlUpdate("INSERT INTO orders(customerId, orderDate, employeeId) VALUES (:customerId,now(),:employeeId);")
    @GetGeneratedKeys("id")
    int create(@Bind("customerId") int customerID, @Bind("employeeId") int employeeId);

    @SqlUpdate("insert into customer_order(orderid, productId, quantity) VALUES (:orderid, :productId, :quantity);")
    int addProduct(@Bind("orderid") int orderId, @Bind("productId") int productID, @Bind("quantity") int quantity);

    @SqlUpdate("delete from orders where id = :id")
    boolean delete(@Bind("id") int id);

    @SqlUpdate("delete from customer_order where orderId = :id")
    void deleteOrder(@Bind("id") int id);

    @SqlUpdate("delete from customer_order where productid = :id")
    boolean deleteAllProducts(@Bind("id") int id);

    @SqlUpdate("delete from customer_order where orderId = :id")
    boolean deleteProducts(@Bind("id") int id);

    @SqlUpdate("update orders set status = :status, customerId = :customerId where id = :id")
    @GetGeneratedKeys
    @RegisterBeanMapper(OrderGet.class)
    OrderGet updateStatus(@Bind("id") int id, @Bind("status") boolean status, @Bind("customerId") int customerid);

    @SqlUpdate("insert into customer_order(orderid, productid, quantity) values (:orderid, :productid, :quantity );")
    @GetGeneratedKeys
    @RegisterBeanMapper(OrderGet.class)
    OrderGet updateProduct(@Bind("orderid") int orderId, @Bind("productid") int id, @Bind("quantity") int quantity);


    @SqlQuery("select id, name, price, stock from products join productPrices on id=productId\n" +
            " where date = (SELECT MAX(date) " +
            "              from productPrices " +
            "              where id = productId and date <= now()) and id not in ((select productId " +
            "            from customer_order where orderId = :orderid)) order by id;")
    @RegisterBeanMapper(Product.class)
    ArrayList<Product> getProducts(@Bind("orderid") int orderid);

    @SqlUpdate("update orders set completeddate = now() where id = :id")
    boolean complete(@Bind("id") int id);
}

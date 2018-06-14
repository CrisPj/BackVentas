package com.pythonteam.dao;

import com.pythonteam.models.Customer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.ArrayList;

public interface CustomerDao {

    @SqlQuery("select * from customers  order by id")
    @RegisterBeanMapper(Customer.class)
    ArrayList<Customer> list();

    @SqlQuery("SELECT * FROM customers where :id = id")
    @RegisterBeanMapper(Customer.class)
    Customer findOne(@Bind("id") int id);

    @SqlUpdate("INSERT INTO customers(name, phone, email, latlong) VALUES (:name,:phone,:email,point(:x,:y) );")
    @GetGeneratedKeys("id")
    int create(@Bind("name") String name, @Bind("phone") String phone, @Bind("email") String email, @Bind("x") double x, @Bind("y") double y);

    @SqlUpdate("delete from customers where id = :id")
    boolean delete(@Bind("id") int id);

    @SqlUpdate("delete from routes where idCustomer = :id;")
    void deleteRoute(@Bind("id") int id);

    @SqlUpdate("update customers set name = :name, phone = :phone, email = :email, latlong = point(:x,:y) where id = :id")
    @GetGeneratedKeys
    @RegisterBeanMapper(Customer.class)
    Customer update(@Bind("id") int id, @Bind("name") String name, @Bind("phone") String phone, @Bind("email") String email, @Bind("x") double x, @Bind("y") double y);

}

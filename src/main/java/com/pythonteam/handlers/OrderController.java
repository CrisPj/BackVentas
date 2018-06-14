package com.pythonteam.handlers;


import com.pythonteam.dao.OrderDao;
import com.pythonteam.dao.ProductDao;
import com.pythonteam.database.Database;
import com.pythonteam.models.OrderGet;
import com.pythonteam.models.Product;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class OrderController implements BaseController<OrderGet, Integer> {

    private static class SingletonHelper {
        private static final OrderController INSTANCE = new OrderController();
    }

    public static OrderController getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private String selectAll = "select orderid o_orderid, customerId o_customerid, status o_status, orderdate o_orderdate, completeddate o_completeddate,employeeId o_employeeId,quantity p_quantity, p.id p_id, p.name p_name, p.description p_description, price p_price, stock p_stock from orders o" +
            "                   join customer_order on o.id = customer_order.orderId" +
            "                   join products p on customer_order.productId = p.id" +
            "                   join productPrices on p.id=productPrices.productId" +
            "                   where date = (SELECT" +
            "                   MAX(date)" +
            "                   from productPrices" +
            "                   where p.id = productPrices.productId and date <= now())";

    private String selectOne = selectAll + " and orderid = :id";

    @Override
    public List<OrderGet> findAll() {
        return Database.getJdbi().withHandle(handle ->
                handle.createQuery(selectAll)
                        .registerRowMapper(BeanMapper.factory(OrderGet.class, "o"))
                        .registerRowMapper(BeanMapper.factory(Product.class, "p"))
                        .<Integer, OrderGet>reduceRows(((map, rowView) -> {
                            OrderGet order = map.computeIfAbsent(
                                    rowView.getColumn("o_orderid", Integer.class),
                                    id -> rowView.getRow(OrderGet.class));
                            if (rowView.getColumn("p_id", Integer.class) != null) {
                                order.addProduct(rowView.getRow(Product.class));
                            }
                        })).collect(toList()));
    }

    @Override
    public OrderGet findOne(Integer orderid) {
        Optional<OrderGet> order = Database.getJdbi().withHandle(handle ->
                handle.createQuery(selectOne)
                        .bind("id", orderid)
                        .registerRowMapper(BeanMapper.factory(OrderGet.class, "o"))
                        .registerRowMapper(BeanMapper.factory(Product.class, "p"))
                        .<Integer, OrderGet>reduceRows(((map, rowView) -> {
                            OrderGet orderTemp = map.computeIfAbsent(
                                    rowView.getColumn("o_orderid", Integer.class),
                                    id -> rowView.getRow(OrderGet.class));

                            if (rowView.getColumn("p_id", Integer.class) != null) {
                                orderTemp.addProduct(rowView.getRow(Product.class));
                            }
                        })).findFirst());

        return order.get();
    }

    @Override
    public boolean delete(Integer id) {
        return Database.getJdbi().withExtension(OrderDao.class, dao -> {
            dao.deleteOrder(id);
            return dao.delete(id);
        });
    }

    @Override
    public OrderGet update(OrderGet orderGet) {
        return null;
    }

    @Override
    public OrderGet create(OrderGet orderGet) throws SQLException {
        return null;
    }


    public OrderGet updateOrder(OrderGet order) {
        Database.getJdbi().withExtension(OrderDao.class, dao -> dao.deleteProducts(order.getOrderId()));
        for (Product p : order.getProductList()) {
            if (p.getQuantity() > 0)
                Database.getJdbi().withExtension(OrderDao.class, dao -> dao.updateProduct(order.getOrderId(), p.getId(), p.getQuantity()));
        }
        Database.getJdbi().withExtension(OrderDao.class, dao -> dao.updateStatus(order.getOrderId(), order.isStatus(), order.getCustomerId()));
        if (order.isStatus()) {
            Database.getJdbi().withExtension(OrderDao.class, dao -> dao.complete(order.getOrderId()));
            for (Product product : order.getProductList()) {
                Product ori = Database.getJdbi().withExtension(ProductDao.class, dao -> dao.findOne(product.getId()));
                int change = 0;

                if (product.getQuantity() > ori.getStock()) {
                    Database.getJdbi().withExtension(OrderDao.class, dao -> dao.updateStatus(order.getOrderId(), false, order.getCustomerId()));
                    change = 0;
                } else change = ori.getStock() - product.getQuantity();
                int finalChange = change;
                System.out.println(finalChange);
                Database.getJdbi().withExtension(ProductDao.class, dao -> dao.updateStock(product.getId(), finalChange));
            }
        }
        return findOne(order.getOrderId());
    }

    public ArrayList<Product> getProducts(int orderid) {
        return Database.getJdbi().withExtension(OrderDao.class, dao -> dao.getProducts(orderid));

    }


    public OrderGet createOrder(OrderGet order) {
        order.setOrderId(Database.getJdbi().withExtension(OrderDao.class, dao -> dao.create(order.getCustomerId(), order.getEmployeeId())));
        for (Product p : order.getProductList()) {
            if (p.getQuantity() > 0) {
                Product product = Database.getJdbi().withExtension(ProductDao.class, dao -> dao.findOne(p.getId()));
                if (p.getQuantity() > product.getAvailable()) {
                    return null;
                }
                Database.getJdbi().withExtension(OrderDao.class, dao -> dao.addProduct(order.getOrderId(), p.getId(), p.getQuantity()));
            }
        }
        return findOne(order.getOrderId());
    }
}

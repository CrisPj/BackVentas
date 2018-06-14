package com.pythonteam.handlers;

import com.pythonteam.dao.OrderDao;
import com.pythonteam.dao.ProductDao;
import com.pythonteam.database.Database;
import com.pythonteam.models.Product;

import java.util.List;

public class ProductController implements BaseController<Product, Integer> {
    private static class SingletonHelper {
        private static final ProductController INSTANCE = new ProductController();
    }

    public static ProductController getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<Product> findAll() {
        return Database.getJdbi().withExtension(ProductDao.class, ProductDao::list);
    }

    @Override
    public Product findOne(Integer id) {
        return Database.getJdbi().withExtension(ProductDao.class, dao -> dao.findOne(id));
    }

    @Override
    public boolean delete(Integer id) {
        Database.getJdbi().withExtension(OrderDao.class, dao -> dao.deleteAllProducts(id));
        return Database.getJdbi().withExtension(ProductDao.class, dao -> {
            dao.deletePrice(id);
            return dao.delete(id);
        });
    }

    @Override
    public Product update(Product product) {
        return Database.getJdbi().withExtension(ProductDao.class, dao -> {
            Product p = dao.findOne(product.getId());
            if (p.getPrice() != product.getPrice())
                dao.createPrice(product.getId(), product.getPrice());
            else if (p.getStock() != product.getStock()) {
                if (p.getStock() > product.getStock()) {
                    int diff = p.getStock() - product.getStock();
                    return dao.update(product.getId(), product.getName(), product.getDescription(), p.getStock(), p.getAvailable() - diff);
                } else if (p.getStock() < product.getStock()) {
                    int diff = product.getStock() - p.getStock();
                    return dao.update(product.getId(), product.getName(), product.getDescription(), product.getStock(), p.getAvailable() + diff);
                }
            }
            return dao.update(product.getId(), product.getName(), product.getDescription(), product.getStock(), product.getAvailable());
        });
    }

    @Override
    public Product create(Product product) {
        product.setId(Database.getJdbi().withExtension(ProductDao.class, dao -> dao.create(product.getName(), product.getDescription(), product.getStock())));
        Database.getJdbi().withExtension(ProductDao.class, dao -> dao.createPrice(product.getId(), product.getPrice()));
        return product;
    }
}

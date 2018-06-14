package com.pythonteam.services;

import com.pythonteam.handlers.ProductController;
import com.pythonteam.models.Product;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductsService implements ServiceInterface<Product> {

    @Override
    public Response create(Product product) {
        Product response = ProductController.getInstance().create(product);
        if (response != null)
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }


    @Override
    public Response read(int id) {
        Product product = ProductController.getInstance().findOne(id);
        if (product == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(product, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response readAll() {
        List<Product> products = ProductController.getInstance().findAll();
        return Response.ok(products, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response update(Product product) {
        return Response.ok(ProductController.getInstance().update(product), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response delete(int id) {
        boolean response = ProductController.getInstance().delete(id);
        if (response) {
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}

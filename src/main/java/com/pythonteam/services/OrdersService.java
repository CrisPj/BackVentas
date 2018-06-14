package com.pythonteam.services;

import com.pythonteam.handlers.OrderController;
import com.pythonteam.models.OrderGet;
import com.pythonteam.models.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/orders")
public class OrdersService implements ServiceInterface<OrderGet> {
    @Override
    public Response create(OrderGet order) {
        OrderGet response = OrderController.getInstance().createOrder(order);
        if (response != null)
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response read(int id) {
        OrderGet order = OrderController.getInstance().findOne(id);
        if (order == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products/{id}")
    public Response getProducts(@PathParam("id") int id) {
        ArrayList<Product> products = OrderController.getInstance().getProducts(id);
        ArrayList<Product> products2 = OrderController.getInstance().findOne(id).getProductList();
        products.forEach(p -> {
            p.setQuantity(0);
            products2.add(p);
        });
        return Response.ok(products2, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response readAll() {
        return Response.ok(OrderController.getInstance().findAll(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response update(OrderGet order) {
        return Response.ok(OrderController.getInstance().updateOrder(order), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response delete(int id) {
        boolean response = OrderController.getInstance().delete(id);
        if (response) {
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}

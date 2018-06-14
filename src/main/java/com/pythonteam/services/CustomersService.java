package com.pythonteam.services;

import com.pythonteam.handlers.CustomerController;
import com.pythonteam.models.Customer;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
public class CustomersService implements ServiceInterface<Customer> {
    @Override
    public Response create(Customer customer) {
        Customer response = CustomerController.getInstance().create(customer);
        if (response != null)
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Override
    public Response read(int id) {
        Customer customer = CustomerController.getInstance().findOne(id);
        if (customer == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(customer, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response readAll() {
        return Response.ok(CustomerController.getInstance().findAll(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response update(Customer customer) {
        return Response.ok(CustomerController.getInstance().update(customer), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response delete(int id) {
        boolean response = CustomerController.getInstance().delete(id);
        if (response) {
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

}

package com.pythonteam.services;

import com.pythonteam.handlers.TokenController;
import com.pythonteam.models.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/login")
public class TokensService implements ServiceInterface<User> {

    @Override
    public Response create(User user) {
        if (user == null)
        {
            return Response.serverError().entity("User no puede ir vacio").build();
        }
        try {
            return  Response.ok(TokenController.getInstance().create(user), MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Datos incorrectos").build();
        }
    }

    @Override
    public Response read(int id) {
        return Response.serverError().entity("No implementado").build();
    }

    @Override
    public Response readAll() {
        return  Response.ok(TokenController.getInstance().findAll(), MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response update(User user) {
        return Response.serverError().entity("No implementado").build();
    }

    @Override
    public Response delete(int id) {
        return Response.serverError().entity("No implementado").build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/token")
    public Response delete(User user)
    {
        boolean response = TokenController.getInstance().delete(user.getId(), user.getToken());
        if (!response)
            return Response.status(Response.Status.NOT_FOUND).build();
        else
            return Response.ok(true, MediaType.APPLICATION_JSON).build();
    }
}

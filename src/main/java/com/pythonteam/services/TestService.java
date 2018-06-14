package com.pythonteam.services;


import com.pythonteam.handlers.TokenController;
import com.pythonteam.models.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(User user) {
        if (user == null)
            return Response.serverError().entity("No hay token").build();
        User x = TokenController.getInstance().findOne(user.getToken());
        if (x == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        return Response.ok(x, MediaType.APPLICATION_JSON).build();
    }
}

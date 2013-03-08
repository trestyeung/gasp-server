package com.cloudbees.gasp.resources;

import com.cloudbees.gasp.models.Restaurant;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.*;

/**
 * @author Kohsuke Kawaguchi
 */
@Path("/restaurants")
public class RestaurantResource {
    @Inject
    EntityManager manager;

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{id}")
    public Restaurant get(@PathParam("id") int id) {
        return manager.find(Restaurant.class,id);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("{id}")
    @Transactional
    public Restaurant update(@PathParam("id") int id, Restaurant src) {
        Restaurant r = get(id);
        r.setUrl(src.getUrl());
        r.setName(src.getName());
        return manager.find(Restaurant.class,id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response update(@PathParam("id") int id) {
        manager.remove(get(id));
        return Response.ok().build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Transactional
    public void create(Restaurant r) {
        manager.persist(r);
    }
}

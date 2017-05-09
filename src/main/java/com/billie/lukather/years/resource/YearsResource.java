package com.billie.lukather.years.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by matthew on 5/9/17.
 */
@Path("/years")
@Component("yearsResource")
public class YearsResource {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String healthCheck() {
        return "UP AND RUNNING";
    }
}

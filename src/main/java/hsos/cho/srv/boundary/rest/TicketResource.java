package hsos.cho.srv.boundary.rest;

import hsos.cho.srv.properties.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/tickets")
public class TicketResource {

    @GET
    @Path("ticketurl")
    public String getTicketUrl(){
        return Properties.ticketTicketUrl;
    }

    @GET
    @Path("date")
    public String getTicketDate(){
        return Properties.ticketTicketDate.toString();
    }
}

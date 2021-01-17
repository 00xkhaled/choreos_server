package hsos.cho.srv.boundary.rest;

import hsos.cho.srv.properties.Settings;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/tickets")
public class TicketResource {

    @GET
    @Path("ticketurl")
    public String getTicketUrl(){
        return Settings.ticketTicketUrl;
    }

    @GET
    @Path("date")
    public String getTicketDate(){
        return Settings.ticketTicketDate.toString();
    }
}

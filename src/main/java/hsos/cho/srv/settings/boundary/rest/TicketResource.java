package hsos.cho.srv.settings.boundary.rest;

import hsos.cho.srv.settings.entity.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author Lukas Grew
 * Returns the actual value of the Propertie Parameter ticketUrl/ticketDate
 */

@Path("/tickets")
public class TicketResource {

    /**
     * @return String of the actual ticketurl
     */
    @GET
    @Path("ticketurl")
    public String getTicketUrl(){
        return Properties.ticketUrl;
    }

    /**
     * @return String of the actual ticketDate
     */
    @GET
    @Path("date")
    public String getTicketDate(){
        return Properties.ticketDate.toString();
    }
}

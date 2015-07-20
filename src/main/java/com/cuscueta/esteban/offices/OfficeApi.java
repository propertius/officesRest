package com.cuscueta.esteban.offices;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.glassfish.jersey.server.JSONP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("offices")
@Produces(MediaType.APPLICATION_JSON)
public class OfficeApi {

    private static final String ITEMS_URL = "/api/offices";

    @GET
    @JSONP(queryParam = "callback")
    public String getAllOffices(@QueryParam("offset") int offset,
                                @QueryParam("count") int count,
                                @QueryParam("callback") String callback) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        List<Office> offices = OfficeDao.getInstance().getAllOffices(offset, count);
        for (Office office : offices) {
            office.setLink(ITEMS_URL + "/" + office.getId());
        }
        return mapper.writeValueAsString(offices);
    }

    @GET
    @Path("open")
    @JSONP(queryParam = "callback")
    public String getAllOpenOffices(@QueryParam("utc") int utc,
                                @QueryParam("time") String time,
                                @QueryParam("callback") String callback) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        List<Office> offices = OfficeDao.getInstance().getAllOpenOffices(utc, time);
        for (Office office : offices) {
            office.setLink(ITEMS_URL + "/" + office.getId());
        }
        return mapper.writeValueAsString(offices);
    }

    @DELETE
    @JSONP(queryParam = "callback")
    public void deleteAllOffices() throws Exception {
        OfficeDao.getInstance().deleteAllOffices();
    }

    @GET
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public String getOffice(@PathParam("id") int id) throws Exception {
        Office office = OfficeDao.getInstance().getOffice(id);
        if (office != null)
            office.setLink(ITEMS_URL + "/" + office.getId());
        return new ObjectMapper().writeValueAsString(office);
    }

    @PUT
    @JSONP(queryParam = "callback")
    public void putOffice(String officeJson) throws Exception {
        Office office = new ObjectMapper().readValue(officeJson, Office.class);
        OfficeDao.getInstance().saveOrUpdateOffice(office);
    }

    @DELETE
    @Path("/{id}")
    @JSONP(queryParam = "callback")
    public void deleteOffice(@PathParam("id") int id) throws Exception {
        OfficeDao.getInstance().deleteOffice(id);
    }

}

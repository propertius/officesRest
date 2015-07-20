package com.cuscueta.esteban.offices;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OfficeApiTest extends OfficeCommonTest {

    private static HttpServer server;
    private WebTarget itemsTarget;
    private ObjectMapper objectMapper;

    @BeforeClass
    public static void beforeOfficeApiTestClass() {
        server = new Server().startServer();
    }

    @Before
    public void beforeOfficeApiTest() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/api/");
        itemsTarget = target.path("offices");
        objectMapper = new ObjectMapper();
    }

    @After
    public void afterUserResourceTest() throws Exception {
        officeDao.deleteAllOffices();
    }

    @AfterClass
    public static void afterUserResourceTestClass() {
        server.shutdown();
    }

    @Test
    public void officesShouldReturnStatus200() {
        assertThat(itemsTarget.request().head().getStatus(), is(200));
    }

    @Test
    public void getOfficesShouldReturnTypeApplicationJson() {
        assertThat(itemsTarget.request().get().getMediaType().toString(), is("application/json"));
    }

    @Test
    public void getOfficesShouldReturnListOfOffices() throws Exception {
        int size = 3;
        insertOffices(size);
        String json = itemsTarget.request().get(String.class);
        List<Office> actual = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Office.class));
        assertThat(actual.size(), is(size));
    }

    @Test
    public void getOfficesShouldStartFromSpecifiedFirstResult() throws Exception {
        insertOffices(10);
        String json = requestItems(5, 0);
        List<Office> actual = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Office.class));
        assertThat(actual.size(), is(5));
    }

    @Test
    public void getOfficesShouldReturnSpecifiedMaximumNumberOfOffices() throws Exception {
        insertOffices(10);
        String json = requestItems(0, 7);
        List<Office> actual = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Office.class));
        assertThat(actual.size(), is(7));
    }

    @Test
    public void getOfficesShouldReturnSpecifiedMaximumNumberOfOfficesAndSkipToTheSpecifiedFirstResult() throws Exception {
        insertOffices(20);
        String json = requestItems(5, 7);
        List<Office> actual = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Office.class));
        assertThat(actual.size(), is(7));
        assertThat(actual.get(0).getId(), is(equalTo(6)));
    }

    @Test
    public void getOfficesShouldReturnTheCorrectJson() throws Exception {
        insertOffices(1);
        String json = itemsTarget.request().get(String.class);
        JSONAssert.assertEquals("[{id: 1}]", json, false);
        JSONAssert.assertEquals("[{link: \"/api/offices/1\"}]", json, false);
        JSONAssert.assertEquals("[{location: \"" + location + "\"}]", json, false);
    }

    @Test
    public void deleteOfficesShouldDeleteAllOffices() {
        itemsTarget.request().delete();
        assertThat(officeDao.getAllOffices().size(), is(0));
    }

    @Test
    public void getOfficesIdShouldReturnSpecifiedOffice() throws Exception {
        Office expected = insertOffices(1).get(0);
        String json = itemsTarget.path("/" + expected.getId()).request().get(String.class);
        Office actual = objectMapper.readValue(json, Office.class);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getOfficesIdShouldReturnCorrectJson() throws Exception {
        Office expected = insertOffices(1).get(0);
        String json = itemsTarget.path("/" + expected.getId()).request().get(String.class);
        JSONAssert.assertEquals("{id: " + expected.getId() + "}", json, false);
        JSONAssert.assertEquals("{location: \"" + expected.getLocation() + "\"}", json, false);
        JSONAssert.assertEquals("{timeDifference: \"" + expected.getTimeDifference() + "\"}", json, false);
        JSONAssert.assertEquals("{openFrom: \"" + expected.getOpenFrom() + "\"}", json, false);
        JSONAssert.assertEquals("{openTo: " + expected.getOpenTo() + "}", json, false);
    }

    @Test
    public void putOfficesIdShouldSaveNewOffice() throws Exception {
        String json = new ObjectMapper().writeValueAsString(office);
        itemsTarget.request().put(Entity.text(json));
        Office actual = officeDao.getOffice(id);
        assertThat(actual, is(not(nullValue())));
        assertThat(actual, is(equalTo(office)));
        assertThat(officeDao.getAllOffices().size(), is(1));
    }

    @Test
    public void putOfficesShouldUpdateExistingOffice() throws Exception {
        officeDao.saveOrUpdateOffice(office);
        office.setLocation("new Location");
        String json = new ObjectMapper().writeValueAsString(location);
        itemsTarget.request().put(Entity.text(json));
        Office actual = officeDao.getOffice(id);
        assertThat(actual, is(equalTo(office)));
        assertThat(officeDao.getAllOffices().size(), is(1));
    }

    @Test
    public void deleteOfficesIdShouldDeleteExistingOffice() throws Exception {
        List<Office> offices = insertOffices(3);
        officeDao.saveOrUpdateOffice(offices.get(0));
        itemsTarget.path("/" + offices.get(0).getId()).request().delete();
        assertThat(officeDao.getAllOffices().size(), is(offices.size() - 1));
    }

    private String requestItems(int firstResult, int maxResult) {
        if (firstResult > 0) {
            itemsTarget = itemsTarget.queryParam("offset", firstResult);
        }
        if (maxResult > 0) {
            itemsTarget = itemsTarget.queryParam("count", maxResult);
        }
        return itemsTarget.request().get(String.class);
    }


}
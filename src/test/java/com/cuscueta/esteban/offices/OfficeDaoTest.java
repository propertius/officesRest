package com.cuscueta.esteban.offices;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class OfficeDaoTest extends OfficeCommonTest {

    @Before
    public void beforeOfficeDaoTest() {
        officeDao.saveOrUpdateOffice(office);
    }

    @After
    public void afterOfficeDaoTest() {
        officeDao.deleteAllOffices();
    }

    @Test
    public void getAllOfficesShouldReturnAllOffices() {
        assertThat(officeDao.getAllOffices().size(), is(equalTo(1)));
    }

    @Test
    public void deleteAllOfficesShouldDeleteAllOffices() {
        officeDao.deleteAllOffices();
        assertThat(officeDao.getAllOffices().size(), is(equalTo(0)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithId() {
        Office actual = officeDao.getAllOffices().get(0);
        assertThat(actual.getId(), is(equalTo(id)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithLocation() {
        Office actual = officeDao.getAllOffices().get(0);
        assertThat(actual.getLocation(), is(equalTo(location)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithTimeDifference() {
        Office actual = officeDao.getAllOffices().get(0);
        assertThat(actual.getTimeDifference(), is(equalTo(timeDifference)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithOpenFrom() {
        Office actual = officeDao.getAllOffices().get(0);
        assertThat(actual.getOpenFrom(), is(equalTo(openFrom)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithOpenTo() {
        Office actual = officeDao.getAllOffices().get(0);
        assertThat(actual.getOpenTo(), is(equalTo(openTo)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesStartingFromSpecifiedFirstResult() {
        int size = 10;
        int firstResult = 5;
        int expected = size - firstResult;
        officeDao.deleteAllOffices();
        insertOffices(size);
        assertThat(officeDao.getAllOffices(firstResult, 0).size(), is(equalTo(expected)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithSpecifiedMaxResult() {
        int size = 20;
        int maxResult = 10;
        officeDao.deleteAllOffices();
        insertOffices(size);
        assertThat(officeDao.getAllOffices(0, maxResult).size(), is(equalTo(maxResult)));
    }

    @Test
    public void getAllOfficesShouldReturnOfficesWithSpecifiedMaxResultStartingFromSpecifiedFirstResult() {
        int size = 20;
        int firstResult = 5;
        int maxResult = 10;
        officeDao.deleteAllOffices();
        insertOffices(size);
        List<Office> actual = officeDao.getAllOffices(firstResult, maxResult);
        assertThat(actual.size(), is(equalTo(maxResult)));
        assertThat(actual.get(0).getId(), is(equalTo(firstResult + 1)));
    }

    @Test
    public void getOfficeShouldReturnOfficeWithTheSpecifiedId() {
        Office actual = officeDao.getOffice(id);
        assertThat(actual, is(equalTo(actual)));
    }

    @Test
    public void getOfficeShouldReturnNullIfIdDoesNotExist() {
        Office actual = officeDao.getOffice(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void saveOrUpdateOfficeShouldSaveTheNewOffice() {
        int actualId = 123;
        office.setId(actualId);
        officeDao.saveOrUpdateOffice(office);
        assertThat(officeDao.getAllOffices().size(), is(equalTo(2)));
        assertThat(officeDao.getOffice(actualId), is(equalTo(office)));
    }

    @Test
    public void saveOrUpdateOfficeShouldUpdateExistingOffice() {
        office.setLocation("new Location");
        officeDao.saveOrUpdateOffice(office);
        assertThat(officeDao.getAllOffices().size(), is(equalTo(1)));
        assertThat(officeDao.getOffice(id), is(equalTo(office)));
    }

    @Test
    public void deleteOfficeShouldReturnDeletedOffice() {
        Office actual = officeDao.deleteOffice(id);
        assertThat(actual, is(equalTo(office)));
    }

    @Test
    public void deleteOfficeShouldReturnNullIfOfficeDoesNotExist() {
        Office actual = officeDao.deleteOffice(123);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void deleteOfficeShouldDeleteSpecifiedOffice() {
        officeDao.deleteOffice(id);
        assertThat(officeDao.getAllOffices().size(), is(equalTo(0)));
    }
}
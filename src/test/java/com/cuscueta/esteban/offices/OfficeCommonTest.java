package com.cuscueta.esteban.offices;

import org.junit.Before;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OfficeCommonTest {

    protected final int id = 42;
    protected final String location = "Berlin";
    protected final Integer timeDifference = 2;
    protected final Time openFrom = getTime(OPEN_FROM);
    protected final Time openTo = getTime(OPEN_TO);
    protected final String link = "/api/offices/42";
    protected final static String OPEN_FROM = "09:00 AM";
    protected final static String OPEN_TO = "06:00 PM";
    protected final static SimpleDateFormat df = new SimpleDateFormat( "h:mm a" );
    protected Office office;
    protected OfficeDao officeDao = OfficeDao.getInstance();

    @Before
    public void beforeCommonTest() {
        office = new Office(id);
        office.setLocation(location);
        office.setTimeDifference(timeDifference);
        office.setOpenFrom(openFrom);
        office.setOpenTo(openTo);
    }

    protected List<Office> insertOffices(int count) {
        List<Office> offices = new ArrayList<>();
        for (int index = 1; index <= count; index++) {
            Office office = new Office(index);
            office.setLocation(location);
            office.setTimeDifference(timeDifference);
            office.setOpenFrom(openFrom);
            office.setOpenTo(openTo);
            officeDao.saveOrUpdateOffice(office);
            offices.add(office);
        }
        return offices;
    }

    protected Time getTime(String stringTime){
        Time ret = null;
        try {
            ret = new Time(df.parse(stringTime).getTime()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

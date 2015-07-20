package com.cuscueta.esteban.offices;

import org.junit.Test;

import java.sql.Time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class OfficeTest extends OfficeCommonTest {

    @Test
    public void idShouldHaveSetterAndGetter() {
        int expected = 123;
        office.setId(expected);
        assertThat(office.getId(), is(equalTo(expected)));
    }

    @Test
    public void locationShouldHaveSetterAndGetter() {
        String expected = "Muenchen";
        office.setLocation(expected);
        assertThat(office.getLocation(), is(equalTo(expected)));
    }

    @Test
    public void timeDifferenceShouldHaveSetterAndGetter() {
        Integer expected = 2;
        office.setTimeDifference(expected);
        assertThat(office.getTimeDifference(), is(equalTo(expected)));
    }

    @Test
    public void openFromShouldHaveSetterAndGetter() {
        Time expected = getTime(OPEN_FROM);
        office.setOpenFrom(expected);
        assertThat(office.getOpenFrom(), is(equalTo(expected)));
    }

    @Test
    public void openToShouldHaveSetterAndGetter() {
        Time expected = getTime(OPEN_TO);
        office.setOpenTo(expected);
        assertThat(office.getOpenTo(), is(equalTo(expected)));
    }

    @Test
    public void linkShouldHaveSetterAndGetter() {
        String expected = "new link";
        office.setLink(expected);
        assertThat(office.getLink(), is(equalTo(expected)));
    }

    @Test
    public void equalsShouldFailIfIdIsNotTheSame() {
        Office actual = new Office(123);
        actual.setLocation(location);
        actual.setTimeDifference(timeDifference);
        actual.setOpenFrom(openFrom);
        actual.setOpenTo(openTo);
        assertThat(actual, is(not(equalTo(office))));
    }

    @Test
    public void equalsShouldReturnFalseIfLocationIsNotTheSame() {
        Office actual = new Office(id);
        actual.setLocation("Muenchen");
        actual.setTimeDifference(timeDifference);
        actual.setOpenFrom(openFrom);
        actual.setOpenTo(openTo);
        assertThat(actual, is(not(equalTo(office))));
    }

    @Test
    public void equalsShouldReturnFalseIfTimeDifferenceIsNotTheSame() {
        Office actual = new Office(id);
        actual.setLocation(location);
        actual.setTimeDifference(5);
        actual.setOpenFrom(openFrom);
        actual.setOpenTo(openTo);
        assertThat(actual, is(not(equalTo(office))));
    }

    @Test
    public void equalsShouldReturnFalseIfOpenFromIsNotTheSame() {
        Office actual = new Office(id);
        actual.setLocation(location);
        actual.setTimeDifference(timeDifference);
        actual.setOpenFrom(getTime("10:00 AM"));
        actual.setOpenTo(openTo);
        assertThat(actual, is(not(equalTo(office))));
    }

    @Test
    public void equalsShouldReturnFalseIfOpenToIsNotTheSame() {
        Office actual = new Office(id);
        actual.setLocation(location);
        actual.setTimeDifference(timeDifference);
        actual.setOpenFrom(openFrom);
        actual.setOpenTo(getTime("07:00 PM"));
        assertThat(actual, is(not(equalTo(office))));
    }

    @Test
    public void equalsShouldReturnTrueIfIdLocationTimeDifferenceOpenFromAndOpenToAreTheSame() {
        Office actual = new Office(id);
        actual.setLocation(location);
        actual.setTimeDifference(timeDifference);
        actual.setOpenFrom(openFrom);
        actual.setOpenTo(openTo);
        assertThat(actual, is(equalTo(office)));
    }
}

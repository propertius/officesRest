package com.cuscueta.esteban.offices;

import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OfficeDao {

    private static volatile OfficeDao instance = null;
    private OfficeDao() { }

    public static synchronized OfficeDao getInstance() {
        if (instance == null) {
            instance = new OfficeDao();
        }
        return instance;
    }

    public void deleteAllOffices() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("delete from Office").executeUpdate();
        session.close();
    }

    public List<Office> getAllOffices() { return getAllOffices(0, 0); }

    public List<Office> getAllOffices(int firstResult, int maxResult) {
        List<Office> offices = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select id, location, timeDifference, openFrom, openTo from Office");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        @SuppressWarnings("unchecked")
        List allBooks = query.list();
        for (Iterator it = allBooks.iterator(); it.hasNext(); ) {
            Object[] officeObject = (Object[]) it.next();
            Office office = new Office((Integer) officeObject[0]);
            office.setLocation((String) officeObject[1]);
            office.setTimeDifference((Integer) officeObject[2]);
            office.setOpenFrom((Time) officeObject[3]);
            office.setOpenTo((Time) officeObject[4]);
            offices.add(office);
        }
        session.close();
        return offices;
    }

    public List<Office> getAllOpenOffices(int utc, String time) {
        List<Office> offices = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select id, location, timeDifference, openFrom, openTo from Office");
        @SuppressWarnings("unchecked")
        List allBooks = query.list();
        for (Iterator it = allBooks.iterator(); it.hasNext(); ) {
            Object[] officeObject = (Object[]) it.next();
            Office office = new Office((Integer) officeObject[0]);
            office.setLocation((String) officeObject[1]);
            office.setTimeDifference((Integer) officeObject[2]);
            office.setOpenFrom((Time) officeObject[3]);
            office.setOpenTo((Time) officeObject[4]);
            if (isOpenNow(utc, time, office)) {
                offices.add(office);
            }
        }
        session.close();
        return offices;
    }

    private boolean isOpenNow(int utc, String timeString, Office office){
        SimpleDateFormat df = new SimpleDateFormat( "h:mm a" );
        Time time = null;
        try {
            time = new Time(df.parse(timeString).getTime()) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalTime requestTimeUTC = new LocalTime(time).minusHours(utc);
        LocalTime officeFromUTC = new LocalTime(office.getOpenFrom()).minusHours(office.getTimeDifference());
        LocalTime officeToUTC = new LocalTime(office.getOpenTo()).minusHours(office.getTimeDifference());

        return requestTimeUTC.isAfter(officeFromUTC) && requestTimeUTC.isBefore(officeToUTC);
    }

    public Office getOffice(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Office office = (Office) session.get(Office.class, id);
        session.close();
        return office;
    }

    public void saveOrUpdateOffice(Office office) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.saveOrUpdate(office);
        session.flush();
        session.close();
    }

    public Office deleteOffice(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Office office = getOffice(id);
        if (office != null) {
            session.delete(office);
            session.flush();
        }
        session.close();
        return office;
    }

}

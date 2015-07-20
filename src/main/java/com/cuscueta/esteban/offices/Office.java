package com.cuscueta.esteban.offices;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;
import java.util.Locale;

@Entity
@Table(name = "Office")
@XmlRootElement
public class Office {

    public Office() { }
    public Office(int id) {
        setId(id);
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "location", nullable = false)
    private String location;
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) { this.location = location; }

    @Column(name = "timeDifference", nullable = false)
    private Integer timeDifference;
    public Integer getTimeDifference() {
        return timeDifference;
    }
    public void setTimeDifference(Integer timeDifference) {
        this.timeDifference = timeDifference;
    }

    @Column(name = "openFrom", nullable = false)
    private Time openFrom;
    public Time getOpenFrom() { return openFrom; }
    public void setOpenFrom(Time openFrom) { this.openFrom = openFrom; }

    @Column(name = "openTo", nullable = false)
    private Time openTo;
    public Time getOpenTo() { return openTo; }
    public void setOpenTo(Time openTo) { this.openTo = openTo; }

    private String link;
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;

        if (id != office.id) return false;
        if (!location.equals(office.location)) return false;
        if (!openFrom.equals(office.openFrom)) return false;
        if (!openTo.equals(office.openTo)) return false;
        if (!timeDifference.equals(office.timeDifference)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + location.hashCode();
        result = 31 * result + timeDifference.hashCode();
        result = 31 * result + openFrom.hashCode();
        result = 31 * result + openTo.hashCode();
        return result;
    }
}

package com.zenika.flightlate.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Airport.
 */
@Entity
@Table(name = "airport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iata")
    private String iata;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIata() {
        return iata;
    }

    public Airport iata(String iata) {
        this.iata = iata;
        return this;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public Float getLon() {
        return lon;
    }

    public Airport lon(Float lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public Airport lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public Airport name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Airport airport = (Airport) o;
        if (airport.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, airport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Airport{" +
            "id=" + id +
            ", iata='" + iata + "'" +
            ", lon='" + lon + "'" +
            ", lat='" + lat + "'" +
            ", name='" + name + "'" +
            '}';
    }
}

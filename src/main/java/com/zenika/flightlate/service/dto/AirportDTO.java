package com.zenika.flightlate.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Airport entity.
 */
public class AirportDTO implements Serializable {

    private Long id;

    private String iata;

    private Float lon;

    private Float lat;

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

    public void setIata(String iata) {
        this.iata = iata;
    }
    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }
    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }
    public String getName() {
        return name;
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

        AirportDTO airportDTO = (AirportDTO) o;

        if ( ! Objects.equals(id, airportDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AirportDTO{" +
            "id=" + id +
            ", iata='" + iata + "'" +
            ", lon='" + lon + "'" +
            ", lat='" + lat + "'" +
            ", name='" + name + "'" +
            '}';
    }
}

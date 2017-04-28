package com.zenika.flightlate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Flight entity.
 */
public class FlightDTO implements Serializable {

    private Long id;

    private Integer year;

    private Integer month;

    private Integer dayOfMonth;

    private Integer dayOfWeek;

    private Integer depTime;

    private String flightNum;

    private Long originId;

    private Long destId;

    private Long uniqueCarrierId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public Integer getDepTime() {
        return depTime;
    }

    public void setDepTime(Integer depTime) {
        this.depTime = depTime;
    }
    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long airportId) {
        this.originId = airportId;
    }

    public Long getDestId() {
        return destId;
    }

    public void setDestId(Long airportId) {
        this.destId = airportId;
    }

    public Long getUniqueCarrierId() {
        return uniqueCarrierId;
    }

    public void setUniqueCarrierId(Long carrierId) {
        this.uniqueCarrierId = carrierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FlightDTO flightDTO = (FlightDTO) o;

        if ( ! Objects.equals(id, flightDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
            "id=" + id +
            ", year='" + year + "'" +
            ", month='" + month + "'" +
            ", dayOfMonth='" + dayOfMonth + "'" +
            ", dayOfWeek='" + dayOfWeek + "'" +
            ", depTime='" + depTime + "'" +
            ", flightNum='" + flightNum + "'" +
            '}';
    }
}

package com.zenika.flightlate.domain;

/**
 * Created by g.bernonville-ext on 12/05/2017.
 */
public class FlightView {
    private String year;
    private String month;
    private String dayOfMonth;
    private String dayOfWeek;
    private Integer depTime;
    private String uniqueCarrier;
    private String flightNum;
    private String origin;
    private String dest;

    public static FlightView of(Flight flight) {
        FlightView flightView = new FlightView();
        flightView.setYear(String.valueOf(flight.getYear()));
        flightView.setMonth(String.valueOf(flight.getMonth()));
        flightView.setDayOfMonth(String.valueOf(flight.getDayOfMonth()));
        flightView.setDayOfWeek(String.valueOf(flight.getDayOfWeek()));
        flightView.setDepTime(flight.getDepTime());
        flightView.setUniqueCarrier(flight.getUniqueCarrier().getCode());
        flightView.setOrigin(flight.getOrigin().getIata());
        flightView.setDest(flight.getDest().getIata());
        return flightView;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getDepTime() {
        return depTime;
    }

    public void setDepTime(Integer depTime) {
        this.depTime = depTime;
    }

    public String getUniqueCarrier() {
        return uniqueCarrier;
    }

    public void setUniqueCarrier(String uniqueCarrier) {
        this.uniqueCarrier = uniqueCarrier;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}

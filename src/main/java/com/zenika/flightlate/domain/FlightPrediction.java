package com.zenika.flightlate.domain;

/**
 * Created by geraud on 28/04/17.
 */
public class FlightPrediction {
    private Boolean late;
    private Float confidence;

    public Boolean getLate() {
        return late;
    }

    public void setLate(Boolean late) {
        this.late = late;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }
}

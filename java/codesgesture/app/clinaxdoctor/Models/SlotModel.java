package codesgesture.app.clinaxdoctor.Models;

import java.io.Serializable;

public class SlotModel implements Serializable  {
    private String interval;
    private String no_patient;
    private String available;


    // Getter Methods

    public String getInterval() {
        return interval;
    }

    public String getNo_patient() {
        return no_patient;
    }

    public String getAvailable() {
        return available;
    }

    // Setter Methods

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setNo_patient(String no_patient) {
        this.no_patient = no_patient;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
package controllers.dto;

import models.Bill;
import models.config.Settings;
import models.patient.Consultation;
import models.patient.Patient;

import java.util.Collection;

/**
 * Created by Zer0 on 13/09/2015.
 */
public class Backup {

    private String version = "2.0";
    private Collection<Patient> patients;
    private Collection<Bill> bills;
    private Collection<Consultation> consultations;
    private Collection<Settings> settings;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Collection<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Collection<Patient> patients) {
        this.patients = patients;
    }

    public Collection<Bill> getBills() {
        return bills;
    }

    public void setBills(Collection<Bill> bills) {
        this.bills = bills;
    }

    public Collection<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(Collection<Consultation> consultations) {
        this.consultations = consultations;
    }

    public Collection<Settings> getSettings() {
        return settings;
    }

    public void setSettings(Collection<Settings> settings) {
        this.settings = settings;
    }
}

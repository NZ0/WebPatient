package controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.patient.Consultation;
import models.patient.Profile;
import play.data.format.Formats;
import utils.JsonDateSerializer;

import java.util.Date;

/**
 * Created by zero on 07/02/15.
 */
public class ConsultationDto {

    private long id;
    @Formats.DateTime(pattern = JsonDateSerializer.JSON_DATE_FORMAT)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date date;
    private long patientId;
    private String title;
    private boolean important;
    private Integer size;
    private Integer weight;
    private Profile profile;
    private String mainReason;
    private String tests;
    private String treatment;
    private String sketch;
    private String bill;

    public ConsultationDto() {}

    public ConsultationDto(Consultation consultation, long patientId) {
        init(consultation);
        this.patientId = patientId;
    }

    public ConsultationDto(Consultation consultation) {
        init(consultation);
        this.patientId = consultation.getPatientId();
    }

    private void init(Consultation consultation) {
        this.id = consultation.getId();
        this.date = consultation.getDate();
        this.title = consultation.getTitle();
        this.important = consultation.isImportant();
        this.size = consultation.getSize();
        this.weight = consultation.getWeight();
        this.profile = consultation.getProfile();
        this.mainReason = consultation.getMainReason();
        this.tests = consultation.getTests();
        this.treatment = consultation.getTreatment();
        this.sketch = consultation.getSketch();
        if (consultation.getBill() != null) {
            this.bill = consultation.getBill().getHash();
        }
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isImportant() {
        return important;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getMainReason() {
        return mainReason;
    }

    public void setMainReason(String mainReason) {
        this.mainReason = mainReason;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}

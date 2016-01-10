package models.patient;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.dto.ConsultationDto;
import models.Bill;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Zer0 on 30/12/2014.
 */
@Entity
public class Consultation extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String title;
    private boolean important;
    private Integer size;
    private Integer weight;
    @Enumerated(EnumType.STRING)
    private Profile profile;

    @Lob
    private String mainReason;

    @Lob
    private String tests;

    @Lob
    private String treatment;

    @Lob
    private String sketch;

    @ManyToOne
    private Patient patient;

    @OneToOne(optional = true)
    private Bill bill;

    public static final Find<Long, Consultation> find = new Find<Long, Consultation>(){};

    public Consultation() {

    }

    public Consultation(ConsultationDto dto) {
        this.update(dto);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
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

    @JsonIgnore
    public long getPatientId() {
        return this.patient.getId();
    }

    public Bill getBill() {
        return bill;
    }

    public void update(ConsultationDto dto) {
        this.date = dto.getDate();
        this.title = dto.getTitle();
        this.important = dto.isImportant();
        this.size = dto.getSize();
        this.weight = dto.getWeight();
        this.profile = dto.getProfile();
        this.mainReason = dto.getMainReason();
        this.tests = dto.getTests();
        this.treatment = dto.getTreatment();
        this.sketch = dto.getSketch();
    }
}

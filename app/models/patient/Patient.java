package models.patient;

import com.avaje.ebean.Model;
import controllers.dto.PatientDto;
import play.data.format.Formats;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Zer0 on 30/12/2014.
 */
@Entity
public class Patient extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String firstName;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String address;
    private String email;
    private String phone;
    private boolean dead;

    @OneToOne(cascade = CascadeType.ALL)
    private PatientInformation information;

    @OneToOne
    private Antecedent antecedents;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Consultation> consultations;

    public static final Find<Long,Patient> find = new Find<Long,Patient>(){};

    public Patient() {

    }

    public Patient(final PatientDto dto) {
        this.update(dto);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isDead() {
        return dead;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Antecedent getAntecedents() {
        return this.antecedents;
    }

    public void setAntecedents(final Antecedent antecedents) {
        this.antecedents = antecedents;
    }

    public List<Consultation> getConsultations() {
        return this.consultations;
    }

    public PatientInformation getInformation() {
        return this.information;
    }

    public void update(PatientDto dto) {
        this.firstName = dto.getFirstName();
        this.name = dto.getName();
        this.birthDate = dto.getBirthday();
        this.phone = dto.getPhone();
        this.dead = dto.isDead();
        this.address = dto.getAddress();
        this.email = dto.getEmail();
        this.sex = dto.getSex();
    }

    public void setInformation(PatientInformation information) {
        this.information = information;
    }
}

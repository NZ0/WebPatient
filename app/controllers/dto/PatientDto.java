package controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.patient.Patient;
import models.patient.Sex;
import play.data.format.Formats;
import play.data.validation.Constraints;
import utils.JsonDateSerializer;

import javax.validation.Constraint;
import java.util.Date;

/**
 * Created by zero on 21/01/15.
 */
public class PatientDto {

    private Long id;

    @Constraints.Required
    private String name;

    @Constraints.Required
    private String firstName;

    @Constraints.Required
    private Sex sex;

    @Formats.DateTime(pattern = JsonDateSerializer.JSON_DATE_FORMAT)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date birthday;

    private String address;

    @Constraints.Email
    private String email;

    @Constraints.Required
    private String phone;

    private boolean dead;

    private PatientInformationDto information;

    public PatientDto() {}

    public PatientDto(final Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.firstName = patient.getFirstName();
        this.sex = patient.getSex();
        this.address = patient.getAddress();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.dead = patient.isDead();
        this.birthday = patient.getBirthDate();
        if (patient.getInformation() != null) {
            this.information = new PatientInformationDto(patient.getInformation());
        }
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setSex(final Sex sex) {
        this.sex = sex;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setDead(final boolean dead) {
        this.dead = dead;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return this.id;
    }

    public boolean isDead() {
        return this.dead;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public Sex getSex() {
        return this.sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public PatientInformationDto getInformation() {
        return information;
    }

    public void setInformation(PatientInformationDto information) {
        this.information = information;
    }
}

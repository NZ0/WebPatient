package controllers.dto;

import models.patient.Hand;
import models.patient.MaritalStatus;
import models.patient.PatientInformation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by zero on 31/01/15.
 */
public class PatientInformationDto {

    private MaritalStatus maritalStatus;
    private int children;
    private String profession;
    private String hobbits;
    private String doctor;
    private boolean mutuel;
    private String healthInsuranceNumber;
    private Hand hand;
    private String misc;

    public PatientInformationDto() {

    }

    public PatientInformationDto(PatientInformation information) {
        this.maritalStatus = information.getMaritalStatus();
        this.children = information.getChildren();
        this.profession = information.getProfession();
        this.hobbits = information.getHobbits();
        this.doctor = information.getDoctor();
        this.mutuel = information.isMutuel();
        this.healthInsuranceNumber = information.getHealthInsuranceNumber();
        this.hand = information.getHand();
        this.misc = information.getMisc();


    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getHobbits() {
        return hobbits;
    }

    public void setHobbits(String hobbits) {
        this.hobbits = hobbits;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public boolean isMutuel() {
        return mutuel;
    }

    public void setMutuel(boolean mutuel) {
        this.mutuel = mutuel;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }
}

package controllers.dto;

import models.patient.Hand;
import models.patient.MaritalStatus;
import models.patient.PatientInformation;

import java.util.HashMap;
import java.util.Map;

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

    private String other;
    private String avp;
    private String gyneco;
    private String ohn;
    private String cardio;
    private String gastrointestinal;
    private String surgery;
    private String treatment;

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

        Map<String, String> additional = information.getAdditionalInformation();
        if (additional != null) {
            this.avp = additional.get(PatientInformation.AVP);
            this.gyneco = additional.get(PatientInformation.GYNECO);
            this.ohn = additional.get(PatientInformation.OHN);
            this.other = additional.get(PatientInformation.OTHER);
            this.cardio = additional.get(PatientInformation.CARDIO);
            this.gastrointestinal = additional.get(PatientInformation.GASTROINTESTINAL);
            this.surgery = additional.get(PatientInformation.SURGERY);
            this.treatment = additional.get(PatientInformation.TREATMENT);
        }
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

    public void setOther(String other) {
        this.other = other;
    }

    public void setAvp(String avp) {
        this.avp = avp;
    }

    public void setGyneco(String gyneco) {
        this.gyneco = gyneco;
    }

    public void setOhn(String ohn) {
        this.ohn = ohn;
    }

    public String getAvp() {
        return avp;
    }

    public String getGyneco() {
        return gyneco;
    }

    public String getOhn() {
        return ohn;
    }

    public String getOther() {
        return other;
    }

    public String getCardio() {
        return cardio;
    }

    public void setCardio(String cardio) {
        this.cardio = cardio;
    }

    public String getGastrointestinal() {
        return gastrointestinal;
    }

    public void setGastrointestinal(String gastrointestinal) {
        this.gastrointestinal = gastrointestinal;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}

package models.patient;

import controllers.dto.PatientInformationDto;

import javax.persistence.*;

/**
 * Created by Zer0 on 30/12/2014.
 */
@Entity
public class PatientInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private int children;
    private String profession;
    private String hobbits;
    private String doctor;
    private boolean mutuel;
    private String healthInsuranceNumber;
    @Enumerated(EnumType.STRING)
    private Hand hand;
    @Lob
    private String misc;

    public PatientInformation() {

    }

    public PatientInformation(PatientInformationDto dto) {
        this.update(dto);
    }

    public int getChildren() {
        return children;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getProfession() {
        return profession;
    }

    public String getHobbits() {
        return hobbits;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean isMutuel() {
        return mutuel;
    }

    public String getMisc() {
        return misc;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public String getDoctor() {
        return doctor;
    }

    public void update(PatientInformationDto dto) {
        if (dto != null) {
            this.children = dto.getChildren();
            this.doctor = dto.getDoctor();
            this.maritalStatus = dto.getMaritalStatus();
            this.profession = dto.getProfession();
            this.hobbits = dto.getHobbits();
            this.mutuel = dto.isMutuel();
            this.healthInsuranceNumber = dto.getHealthInsuranceNumber();
            this.hand = dto.getHand();
            this.misc = dto.getMisc();
        }
    }
}

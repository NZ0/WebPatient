package models.patient;


import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.DbJson;

import controllers.dto.PatientInformationDto;

import org.apache.commons.lang3.StringUtils;
import play.libs.Json;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zer0 on 30/12/2014.
 */
@Entity
public class PatientInformation extends Model {

    public static final String AVP = "avp";
    public static final String GYNECO = "gyneco";
    public static final String OHN = "ohn";
    public static final String OTHER = "other";
    public static final String TREATMENT = "treatment";
    public static final String SURGERY = "surgery";
    public static final String GASTROINTESTINAL = "gastrointestinal";
    public static final String CARDIO = "cardio";

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

    @DbJson
    @Column(name = "additional_information")
    private Map<String, String> additionalInformation = new HashMap<>();

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


    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
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
            //Unable to update the map created by ebean!!!! So we need a new one
            this.additionalInformation = new HashMap<>();
            this.additionalInformation.put(AVP, dto.getAvp());
            this.additionalInformation.put(GYNECO, dto.getGyneco());
            this.additionalInformation.put(OHN, dto.getOhn());
            this.additionalInformation.put(OTHER, dto.getOther());
            this.additionalInformation.put(CARDIO, dto.getCardio());
            this.additionalInformation.put(GASTROINTESTINAL, dto.getGastrointestinal());
            this.additionalInformation.put(SURGERY, dto.getSurgery());
            this.additionalInformation.put(TREATMENT, dto.getTreatment());
        }
    }
}

package models;

import com.avaje.ebean.Model;
import models.patient.Consultation;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Zer0 on 30/12/2014.
 */
@Entity
public class Bill extends Model {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;
    private String hash;
    private float price;
    private Date date;
    private String name;
    private String firstName;
    @Lob
    private String detail;
    
    private String address;
    private String town;
    private String practitionerName;
    private String practitionerFirstName;
    private String phone;
    private String header;
    private String footer;
    private String zipCode;

    private Long patient;

    @OneToOne(mappedBy = "bill", optional = true)
    private Consultation consultation;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    public static final Find<Long, Bill> find = new Find<Long, Bill>(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getPatient() {
        return patient;
    }

    public void setPatient(Long patient) {
        this.patient = patient;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setTown(final String town) {
        this.town = town;
    }

    public String getTown() {
        return this.town;
    }

    public void setPractitionerName(final String practitionerName) {
        this.practitionerName = practitionerName;
    }

    public String getPractitionerName() {
        return practitionerName;
    }

    public String getPractitionerFirstName() {
        return practitionerFirstName;
    }

    public void setPractitionerFirstName(String practitionerFirstName) {
        this.practitionerFirstName = practitionerFirstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public PaymentType getPayment() {
        return payment;
    }

    public void setPayment(PaymentType payment) {
        this.payment = payment;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}

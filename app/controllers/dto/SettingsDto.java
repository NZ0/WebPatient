package controllers.dto;

import models.config.ParameterName;
import models.config.Settings;
import play.data.validation.Constraints;

import javax.validation.Constraint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zero on 08/02/15.
 */
public class SettingsDto {
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String firstName;
    @Constraints.Required
    private String address;
    @Constraints.Required
    private String phone;
    @Constraints.Required
    private String billHeader;
    @Constraints.Required
    private String billFooter;
    @Constraints.Required
    private float price;
    @Constraints.Required
    private String town;
    @Constraints.Email
    private String email;

    @Constraints.Required
    private String zipCode;

    private int appointmentDuration;


    public SettingsDto() {}

    public SettingsDto(Collection<Settings> settings) {
        settings.forEach((setting) -> init(setting));
    }

    private void init(Settings setting) {
        switch (setting.getName()) {
            case BILL_DEFAULT_PRICE:
                this.price = Float.parseFloat(setting.getValue());
                break;
            case PRACTITIONER_NAME:
                this.name = setting.getValue();
                break;
            case PRACTITIONER_FIRSTNAME:
                this.firstName = setting.getValue();
                break;
            case PRACTITIONER_ADDRESS:
                this.address = setting.getValue();
                break;
            case PRACTITIONER_PHONE:
                this.phone = setting.getValue();
                break;
            case BILL_HEADER:
                this.billHeader = setting.getValue();
                break;
            case BILL_FOOTER:
                this.billFooter = setting.getValue();
                break;
            case PRACTITIONER_TOWN:
                this.town = setting.getValue();
                break;
            case PRACTITIONER_EMAIL:
                this.email = setting.getValue();
                break;
            case APPOINTMENT_DURATION:
                this.appointmentDuration = Integer.parseInt(setting.getValue());
                break;
            case PRACTITIONER_ZIP:
                this.zipCode = setting.getValue();
                break;
            default:
                throw new IllegalArgumentException("Setting not found");
        }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBillHeader() {
        return billHeader;
    }

    public void setBillHeader(String billHeader) {
        this.billHeader = billHeader;
    }

    public String getBillFooter() {
        return billFooter;
    }

    public void setBillFooter(String billFooter) {
        this.billFooter = billFooter;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAppointmentDuration() {
        return appointmentDuration;
    }

    public void setAppointmentDuration(int appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Map<ParameterName, String> asMap() {
        Map<ParameterName, String> map = new HashMap<>();
        map.put(ParameterName.PRACTITIONER_NAME, this.getName());
        map.put(ParameterName.PRACTITIONER_FIRSTNAME, this.getFirstName());
        map.put(ParameterName.PRACTITIONER_ADDRESS, this.getAddress());
        map.put(ParameterName.PRACTITIONER_PHONE, this.getPhone());
        map.put(ParameterName.BILL_DEFAULT_PRICE, String.valueOf(this.getPrice()));
        map.put(ParameterName.BILL_HEADER, this.getBillHeader());
        map.put(ParameterName.BILL_FOOTER, this.getBillFooter());
        map.put(ParameterName.PRACTITIONER_TOWN, this.getTown());
        map.put(ParameterName.APPOINTMENT_DURATION, String.valueOf(this.getAppointmentDuration()));
        map.put(ParameterName.PRACTITIONER_EMAIL, this.getEmail());
        map.put(ParameterName.PRACTITIONER_ZIP, this.getZipCode());
        return map;
    }
}

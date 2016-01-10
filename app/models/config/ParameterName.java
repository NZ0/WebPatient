package models.config;

import play.mvc.PathBindable;

/**
 * Created by zero on 08/02/15.
 */
public enum ParameterName implements PathBindable<ParameterName> {
    PRACTITIONER_NAME("name"), PRACTITIONER_FIRSTNAME("firstName"), PRACTITIONER_ADDRESS("address"), PRACTITIONER_TOWN("town"), PRACTITIONER_PHONE("phone"),
    PRACTITIONER_EMAIL("email"), BILL_HEADER("billHeader"), BILL_FOOTER("billFooter"), BILL_DEFAULT_PRICE("price"),
    APPOINTMENT_DURATION("appointmentDuration"), PRACTITIONER_ZIP("zipCode");

    private final String jsonName;

    private ParameterName(final String jsonName) {
        this.jsonName = jsonName;
    }

    @Override
    public ParameterName bind(String key, String txt) {
        return fromJson(txt);
    }

    @Override
    public String unbind(String key) {
        return key + "=" + this.jsonName;
    }

    @Override
    public String javascriptUnbind() {
        return this.jsonName;
    }

    public static ParameterName fromString(String name) {
        for (ParameterName value : ParameterName.values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Not found");
    }

    public static ParameterName fromJson(final String jsonName) {
        for (ParameterName value : ParameterName.values()) {
            if (value.jsonName.equals(jsonName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Not found");
    }
}

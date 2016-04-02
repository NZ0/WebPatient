package controllers.dto;

import models.PaymentType;
import play.data.validation.Constraints;

/**
 * Created by zero on 09/02/15.
 */
public class Billing {

    @Constraints.Required
    private long consultId;
    @Constraints.Required
    private float price;

    @Constraints.Required
    private PaymentType type;

    private String details;

    public long getConsultId() {
        return consultId;
    }

    public void setConsultId(long consultId) {
        this.consultId = consultId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }
}

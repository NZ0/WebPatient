package controllers.dto;

import play.data.validation.Constraints;

/**
 * Created by zero on 09/02/15.
 */
public class Billing {

    @Constraints.Required
    private long consultId;
    @Constraints.Required
    private float price;

    private String detail;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

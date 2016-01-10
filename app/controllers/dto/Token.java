package controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Created by Zer0 on 08/01/2016.
 */
public class Token {

    @JsonProperty("token")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

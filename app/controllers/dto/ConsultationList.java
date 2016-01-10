package controllers.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.patient.Consultation;
import play.data.format.Formats;
import utils.JsonDateSerializer;

import java.util.Collection;
import java.util.Date;

/**
 * Created by zero on 08/02/15.
 */
public class ConsultationList extends ListDto<ConsultationList.ConsultationDtoLite> {

    public static class ConsultationDtoLite {
        private long id;
        @Formats.DateTime(pattern = JsonDateSerializer.JSON_DATE_FORMAT)
        @JsonSerialize(using = JsonDateSerializer.class)
        private Date date;
        private String title;
        private boolean important;

        public ConsultationDtoLite(Consultation consultation) {
            this.id = consultation.getId();
            this.date = consultation.getDate();
            this.title = consultation.getTitle();
            this.important = consultation.isImportant();
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isImportant() {
            return important;
        }

        public void setImportant(boolean important) {
            this.important = important;
        }
    }
}

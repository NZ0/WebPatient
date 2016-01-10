package controllers;

import com.avaje.ebean.PagedList;
import controllers.dto.ConsultationDto;
import controllers.dto.ConsultationList;
import controllers.dto.ConsultationList.ConsultationDtoLite;
import models.patient.Consultation;
import models.patient.Patient;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by zero on 21/01/15.
 */
public class ConsultationController extends Controller {

    private static final int NB_ITEMS_PER_PAGE = 25;

    public static Result list(long patientId, Integer page) {
        Patient patient = Patient.find.byId(patientId);
        if (patient == null) {
            return notFound("patient not found");
        }
        if (page <= 0 || page == null) {
            page = 0;
        } else {
            page--;
        }

        PagedList<Consultation> listing = Consultation.find.orderBy("date DESC").where().eq("patient", patient).findPagedList(page, NB_ITEMS_PER_PAGE);
        List<Consultation> consultations = listing.getList();
        ConsultationList consultationList = new ConsultationList();
        consultations.forEach((consult) -> consultationList.getItems().add(new ConsultationDtoLite(consult)));
        consultationList.setTotalItems(Consultation.find.findRowCount());
        consultationList.setItemsPerPages(NB_ITEMS_PER_PAGE);
        return ok(Json.toJson(consultationList));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<ConsultationDto> formConsult = Form.form(ConsultationDto.class);
        formConsult = formConsult.bind(request().body().asJson());
        if (!formConsult.hasErrors()) {
            ConsultationDto consultDto = formConsult.get();
            Patient patient = Patient.find.byId(consultDto.getPatientId());
            if (patient != null) {
                Consultation consult = new Consultation(consultDto);
                patient.getConsultations().add(consult);
                patient.update();
                return ok(Json.toJson(new ConsultationDto(consult, patient.getId())));
            } else {
                return notFound();
            }
        } else {
            return badRequest();
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result edit(Long id) {
        Form<ConsultationDto> formConsult = Form.form(ConsultationDto.class);
        formConsult = formConsult.bind(request().body().asJson());
        if (!formConsult.hasErrors()) {
            ConsultationDto consultDto = formConsult.get();
            Consultation consult = Consultation.find.byId(id);
            if (consult != null) {
                consult.update(consultDto);
                consult.update();
                return ok();
            } else {
                return notFound();
            }
        } else {
            return badRequest();
        }
    }

    public static Result last(Long patientId) {
        Consultation consultation = Consultation.find.orderBy().desc("id").where("patient.id = " + patientId).setMaxRows(1).findUnique();
        if (consultation != null) {
            ConsultationDto dto = new ConsultationDto(consultation);
            return ok(Json.toJson(dto));
        } else {
            return notFound();
        }
    }


    public static Result show(long consult) {
        Consultation consultation = Consultation.find.byId(consult);
        if (consultation != null) {
            return ok(Json.toJson(new ConsultationDto(consultation)));
        } else {
            return notFound();
        }
    }
}

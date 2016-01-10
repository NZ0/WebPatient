package controllers;

import com.avaje.ebean.Expr;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.JsonNode;
import controllers.dto.PatientDto;
import controllers.dto.PatientList;
import models.patient.Patient;
import models.patient.PatientInformation;
import org.apache.commons.lang3.StringUtils;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zero on 14/01/15.
 */
public class PatientController extends Controller {

    private static final int NB_ITEM_PER_PAGE = 25;

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        JsonNode node = request().body().asJson();
        Form<PatientDto> form = Form.form(PatientDto.class);
        form = form.bind(node);

        if (!form.hasErrors()) {
            Patient patient = new Patient(form.get());
            patient.save();
            return ok(Json.toJson(patient));
        } else {
            return badRequest();
        }
    }

    public static Result show(Long id) {
        Patient patient = Patient.find.byId(id);
        if (patient == null) {
            return status(404, "patient not found");
        } else {
            return ok(Json.toJson(new PatientDto(patient)));
        }
    }

    public static Result list(Integer page) {
        if (page == null && page <= 0) {
            page = 0;
        } else {
            page--;
        }

        PagedList<Patient> listing = Patient.find.orderBy("name, firstName, id ASC").findPagedList(page, NB_ITEM_PER_PAGE);

        List<Patient> patients = listing.getList();
        PatientList list = new PatientList();
        patients.forEach((patient) -> list.getItems().add(new PatientDto(patient)));
        list.setItemsPerPages(NB_ITEM_PER_PAGE);
        list.setTotalItems(Patient.find.findRowCount());
        return ok(Json.toJson(list));
    }

    public static Result search(String name) {
        if (StringUtils.isNotEmpty(name)) {
            List<Patient> patients = Patient.find.where().or(Expr.ilike("name", name + "%"), Expr.ilike("firstName", name + "%")).setMaxRows(10).findList();
            List<PatientDto> patientDtos = new LinkedList<>();
            for (Patient patient : patients) {
                patientDtos.add(new PatientDto(patient));
            }
            return ok(Json.toJson(patientDtos));
        }
        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result edit(Long id) {
        Patient patient = Patient.find.byId(id);
        if (patient != null) {
            Form<PatientDto> form = Form.form(PatientDto.class);
            form = form.bind(request().body().asJson());
            if (!form.hasErrors()) {
                PatientDto dto = form.get();
                PatientInformation information;
                patient.update(dto);
                if (patient.getInformation() != null) {
                    information = patient.getInformation();
                } else {
                    information = new PatientInformation();
                    patient.setInformation(information);
                }
                information.update(dto.getInformation());
                patient.update();
                return ok(Json.toJson(new PatientDto(patient)));
            } else {
                return badRequest();
            }
        } else {
            return status(404, "patient not found");
        }
    }

    public static Result nbPatients() {
        int nb = Patient.find.findRowCount();
        return ok(Json.toJson(nb));
    }
}

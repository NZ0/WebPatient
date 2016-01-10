package controllers;

import com.avaje.ebean.PagedList;
import controllers.dto.BillDto;
import controllers.dto.BillList;
import controllers.dto.Billing;
import it.innove.play.pdf.PdfGenerator;
import models.Bill;
import models.config.ParameterName;
import models.config.Settings;
import models.patient.Consultation;
import models.patient.Patient;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Hashids;

import java.util.*;

/**
 * Created by zero on 08/02/15.
 */
public class BillController extends Controller {

    private static final String SALT = "F6y7mwB?5nni89bpOypRJexUCsqGVS_GrIo=a:ihN@^y`^qK4C>OQ";
    private static final Hashids HASHIDS = new Hashids(SALT, 8);
    private static final int NB_ITEMS_PER_PAGES = 50;

    private static final PdfGenerator PDF_GENERATOR = new PdfGenerator();

    static {
        PDF_GENERATOR.addTemporaryFonts(Arrays.asList("META-INF/resources/webjars/font-awesome/4.5.0/fonts/fontawesome-webfont.ttf"));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
        Form<Billing> form = new Form<Billing>(Billing.class);
        form = form.bind(request().body().asJson());
        if (!form.hasErrors()) {
            Billing billing = form.get();
            Consultation consult = Consultation.find.byId(billing.getConsultId());
            if (consult != null) {
                if (consult.getBill() == null) {
                    Patient patient = Patient.find.byId(consult.getPatientId());
                    Bill bill = new Bill();
                    bill.setDetail(billing.getDetail());
                    bill.setConsultation(consult.getId());
                    bill.setPatient(patient.getId());
                    bill.setDate(new Date());
                    bill.setFirstName(patient.getFirstName());
                    bill.setName(patient.getName());
                    bill.setPrice(billing.getPrice());
                    bill.setAddress(Settings.find.get(ParameterName.PRACTITIONER_ADDRESS).getValue());
                    bill.setTown(Settings.find.get(ParameterName.PRACTITIONER_TOWN).getValue());
                    bill.setFooter(Settings.find.get(ParameterName.BILL_FOOTER).getValue());
                    bill.setHeader(Settings.find.get(ParameterName.BILL_HEADER).getValue());
                    bill.setPractitionerFirstName(Settings.find.get(ParameterName.PRACTITIONER_FIRSTNAME).getValue());
                    bill.setPractitionerName(Settings.find.get(ParameterName.PRACTITIONER_NAME).getValue());
                    bill.setPhone(Settings.find.get(ParameterName.PRACTITIONER_PHONE).getValue());
                    bill.setZipCode(Settings.find.get(ParameterName.PRACTITIONER_ZIP).getValue());

                    bill.save();
                    bill.setHash(HASHIDS.encode(bill.getId()));
                    bill.update();

                    return ok(bill.getHash());
                } else {
                    return badRequest();
                }
            } else {
                return notFound();
            }
        } else {
            return badRequest();
        }
    }

    public static Result list(Integer page) {

        if (page == null && page <= 0) {
            page = 0;
        } else {
            page--;
        }
        PagedList<Bill> listing = Bill.find.orderBy().asc("id").findPagedList(page, NB_ITEMS_PER_PAGES);
        int maxPage = listing.getTotalPageCount();
        if (page > maxPage) {
            page = maxPage;
        }
        Collection<Bill> bills = listing.getList();
        Collection<BillDto> list = new ArrayList<>(bills.size());
        BillList dto = new BillList();
        bills.forEach((bill) -> dto.getItems().add(toDto(bill)));
        dto.setItemsPerPages(NB_ITEMS_PER_PAGES);
        dto.setTotalItems(Bill.find.findRowCount());
        return ok(Json.toJson(dto));
    }

    public static Result search(String id, Integer page) {
        if (page == null && page <= 0) {
            page = 0;
        } else {
            page--;
        }

        PagedList<Bill> bills = Bill.find.where().ilike("hash", id + "%").findPagedList(0, NB_ITEMS_PER_PAGES);
        BillList dtos = new BillList();
        bills.getList().forEach(bill -> dtos.getItems().add(toDto(bill)));
        dtos.setItemsPerPages(NB_ITEMS_PER_PAGES);
        dtos.setTotalItems(bills.getTotalRowCount());
        return ok(Json.toJson(dtos));
    }
    
    public static Result nbBills() {
        int nbBills = Bill.find.findRowCount();
        return ok(Json.toJson(nbBills));
    }

    public static Result get(String hash) {
        long[] ids = HASHIDS.decode(hash);
        if (ids.length == 1) {
            Bill bill = Bill.find.byId(ids[0]);
            if (bill != null) {
                return  PDF_GENERATOR.ok(views.html.bill.render(toDto(bill)), "http://localhost:9000");
            } else {
                return notFound();
            }

        } else {
            return badRequest();
        }
    }

    private static BillDto toDto(Bill bill) {
        BillDto dto = new BillDto();
        dto.setId(bill.getHash());
        dto.setPrice(bill.getPrice());
        dto.setDetails(bill.getDetail());
        dto.setPatientFirstName(bill.getFirstName());
        dto.setPatientName(bill.getName());
        dto.setDate(bill.getDate());
        dto.setPatientId(bill.getPatient());
        dto.setTown(bill.getTown());
        dto.setFooter(bill.getFooter());
        dto.setPractitionerName(bill.getPractitionerName());
        dto.setHeader(bill.getHeader());
        dto.setPhone(bill.getPhone());
        dto.setAddress(bill.getAddress());
        dto.setPractitionerFirstName(bill.getPractitionerFirstName());
        dto.setZipCode(bill.getZipCode());
        return dto;
    }

}

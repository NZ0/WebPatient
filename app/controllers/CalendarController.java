package controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Calendar;
import controllers.dto.Appointment;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zer0 on 16/09/2015.
 */
public class CalendarController extends Controller {

    private static final String APP_NAME = "Web Patient";
    private static final String CALENDAR_NAME = "Agenda Web Patient";
    private static final File DATA_STORE_DIR = new File("calendar");
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final String OAUTH_CALL_BACK = "http://localhost:9000/calendar/login/success";
    private static final String CLIENT_ID = "myclientid";
    private static final String CLIENT_SECRET = "mysecret";
    private static String calendarIdCache;

    static {
        try {
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Result isRegistered() {
        try {
            Credential credential = initFlow(false).loadCredential("user");
            if (credential == null) {
                return unauthorized();
            } else {
                return ok();
            }
        } catch (IOException | GoogleAuthException e) {
            return internalServerError(e.getLocalizedMessage());
        }
    }

    public static Result auth() {
        try {
            GoogleAuthorizationCodeFlow autCodeFlow = initFlow(true);
            return redirect(autCodeFlow.newAuthorizationUrl().setRedirectUri(OAUTH_CALL_BACK).build());
        } catch (GoogleAuthException e) {
            e.printStackTrace();
            return internalServerError(e.getMessage());
        }
    }

    public static Result authResult() {
        Http.Request request = request();
        try {
            String code = request.getQueryString("code");
            if (StringUtils.isNotEmpty(code)) {
                GoogleAuthorizationCodeFlow flow = initFlow(false);
                GoogleTokenResponse result = flow.newTokenRequest(code).setRedirectUri(OAUTH_CALL_BACK).execute();
                flow.createAndStoreCredential(result, "user");
                createDefaultCalendar();
                return redirect("/#/calendar");
            } else {
                return notFound();
            }
        } catch (IOException | GoogleAuthException e) {
            e.printStackTrace();
            return internalServerError(e.getMessage());
        }
    }

    private static void createDefaultCalendar() throws GoogleAuthException {
        try {
            com.google.api.services.calendar.Calendar service = getCalendarService();
            calendarIdCache = findCalendarId(service, CALENDAR_NAME, Optional.<String>empty());
            if (calendarIdCache == null) {
                Calendar calendar = new com.google.api.services.calendar.model.Calendar();
                calendar.setSummary(CALENDAR_NAME);
                calendar.setDescription("Agenda de vos rendez-vous.");
                Calendar result = service.calendars().insert(calendar).execute();
                calendarIdCache = result.getId();
            }
        } catch (IOException e) {
            throw new GoogleAuthException(e);
        }
    }

    public static Result list(String start, String end) {
        try {
            com.google.api.services.calendar.Calendar service = getCalendarService();
            String calendar = getCalendarId(service);
            com.google.api.services.calendar.Calendar.Events.List request = service.events().list(calendar);
            try {
                if (StringUtils.isNotEmpty(start)) {
                    request.setTimeMin(new DateTime(new SimpleDateFormat(DATE_PATTERN).parse(start).getTime()));
                } else {
                    request.setTimeMin(new DateTime(org.joda.time.DateTime.now().withTimeAtStartOfDay().getMillis()));
                }
                if (StringUtils.isNoneEmpty(end)) {
                    request.setTimeMax(new DateTime(new SimpleDateFormat(DATE_PATTERN).parse(end).getTime()));
                } else {
                    request.setTimeMax(new DateTime(org.joda.time.DateTime.now().withTimeAtStartOfDay().plusDays(5).getMillis()));
                }
            } catch (ParseException e) {
                return badRequest("Wrong time format");
            }
            Events data = request.execute();
            Collection<Appointment> result = new ArrayList<>(data.getItems().size());
            for (Event event : data.getItems()) {
                Appointment appointment = new Appointment();
                appointment.setAllDay(false);
                appointment.setStart(event.getStart().getDateTime().toStringRfc3339());
                appointment.setEnd(event.getEnd().getDateTime().toStringRfc3339());
                appointment.setId(event.getId());
                appointment.setTitle(event.getSummary());
                Event.ExtendedProperties extProps = event.getExtendedProperties();
                if (extProps != null && extProps.getPrivate() != null && extProps.getPrivate().containsKey("userId")) {
                    appointment.setUserId(extProps.getPrivate().get("userId"));
                }
                result.add(appointment);
            }
            return ok(Json.toJson(result));
        } catch (IOException | GoogleAuthException e) {
            return internalServerError(e.getMessage());
        }
    }
    @BodyParser.Of(BodyParser.Json.class)
    public static Result createEvent() {
        Appointment appointment = Json.fromJson(request().body().asJson(), Appointment.class);
        try {
            com.google.api.services.calendar.Calendar service = getCalendarService();
            String calendar = getCalendarId(service);
            Event event = createEvent(appointment);
            service.events().insert(calendar, event).execute();
            return ok();
        } catch (GoogleAuthException | IOException e) {
            return internalServerError(e.getMessage());
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateEvent() {
        Appointment appointment = Json.fromJson(request().body().asJson(), Appointment.class);
        try {
            com.google.api.services.calendar.Calendar service = getCalendarService();
            String calendar = getCalendarId(service);
            Event event = createEvent(appointment);
            service.events().update(calendar, event.getId(), event).execute();
            return ok();
        } catch (GoogleAuthException | IOException e) {
            return internalServerError(e.getMessage());
        }
    }

    public static Result deleteEvent(String id) {
        try {
            com.google.api.services.calendar.Calendar service = getCalendarService();
            String calendar = getCalendarId(service);
            service.events().delete(calendar, id).execute();
            return ok();
        } catch (IOException | GoogleAuthException e) {
            return internalServerError(e.getMessage());
        }
    }

    /**
     *
     * @return
     * @throws GoogleAuthException
     */
    private static GoogleAuthorizationCodeFlow initFlow(boolean force) throws GoogleAuthException {
        String approvalPrompt = "auto";
        if (force) {
            approvalPrompt = "force";
        }
        try {
            return new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    CLIENT_ID, CLIENT_SECRET, Collections.singleton(CalendarScopes.CALENDAR))
                    .setDataStoreFactory(DATA_STORE_FACTORY)
                    .setAccessType("offline")
                    .setApprovalPrompt(approvalPrompt).build();
        } catch (IOException | GeneralSecurityException e) {
            throw new GoogleAuthException(e);
        }
    }

    private static com.google.api.services.calendar.Calendar getCalendarService() throws GoogleAuthException {
        try {
            GoogleAuthorizationCodeFlow flow = initFlow(false);
            Credential credential = flow.loadCredential("user");
            return new com.google.api.services.calendar.Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                    .setApplicationName(APP_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            throw new GoogleAuthException(e);
        }
    }

    private static String getCalendarId(com.google.api.services.calendar.Calendar service) throws IOException {
        if (calendarIdCache == null) {
            calendarIdCache = findCalendarId(service, CALENDAR_NAME, Optional.<String>empty());
        }
        return calendarIdCache;
    }

    private static String findCalendarId(com.google.api.services.calendar.Calendar service, String calendarSummary, Optional<String> nextToken) throws IOException {
        com.google.api.services.calendar.Calendar.CalendarList.List request = service.calendarList().list();
        if (nextToken.isPresent()) {
            request.setPageToken(nextToken.get());
        }
        CalendarList calendarList = request.execute();
        for (CalendarListEntry item : calendarList.getItems()) {
            if (item.getSummary().equals(calendarSummary)) {
                return item.getId();
            }
        }
        if (StringUtils.isNotEmpty(calendarList.getNextPageToken())) {
            return findCalendarId(service, calendarSummary, Optional.of(calendarList.getNextPageToken()));
        }
        return null;
    }

    private static Event createEvent(Appointment appointment) {
        Event event = new Event();
        event.setStart(new EventDateTime().setDateTime(new DateTime(appointment.getStart())));
        event.setEnd(new EventDateTime().setDateTime(new DateTime(appointment.getEnd())));
        event.setSummary(appointment.getTitle());
        if (StringUtils.isNotEmpty(appointment.getUserId())) {
            Event.ExtendedProperties properties = new Event.ExtendedProperties();
            properties.setPrivate(new HashMap<>());
            properties.getPrivate().put("userId", appointment.getUserId());
            event.setExtendedProperties(properties);
        }

        if (StringUtils.isNotEmpty(appointment.getId())) {
            event.setId(appointment.getId());
        }
        return event;
    }
}

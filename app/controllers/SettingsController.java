package controllers;

import controllers.dto.SettingsDto;
import models.config.ParameterName;
import models.config.Settings;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zero on 08/02/15.
 */
public class SettingsController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result update() {
        Form<SettingsDto> form = new Form<SettingsDto>(SettingsDto.class);
        form = form.bind(request().body().asJson());
        if (!form.hasErrors()) {
            Map<ParameterName, String> dto = form.get().asMap();
            dto.forEach((key, value) -> updateSettingsFromJson(key, value));
            return ok();
        } else {
            return badRequest();
        }
    }
    private static void updateSettingsFromJson(final ParameterName field, final String value) {
        Settings settingToUpdate = Settings.find.get(field);
        if (settingToUpdate != null) {
            settingToUpdate.setValue(value);
            settingToUpdate.update();
        } else {
            settingToUpdate = new Settings();
            settingToUpdate.setName(field);
            settingToUpdate.setValue(value);
            settingToUpdate.save();
        }
    }

    public static Result get(ParameterName parameterName) {
        Settings parameter = Settings.find.where().eq("name", parameterName).findUnique();
        if (parameter != null) {
            return ok(Json.toJson(parameter));
        } else {
            return notFound();
        }
    }

    public static Result list() {
        Collection<Settings> settings = Settings.find.all();
        if (settings.size() > 0) {
            return ok(Json.toJson(new SettingsDto(settings)));
        } else {
            return notFound();
        }
    }
}

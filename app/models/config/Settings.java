package models.config;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by zero on 08/02/15.
 */
@Entity
public class Settings extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Constraints.Required
    @Enumerated(EnumType.STRING)
    @Column(unique =  true)
    private ParameterName name;

    private String value;

    public static final SettingsFinder find = new SettingsFinder();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParameterName getName() {
        return name;
    }

    public void setName(ParameterName name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class SettingsFinder extends Find<Long, Settings> {

        public Settings get(ParameterName name) {
            return this.where().eq("name", name).findUnique();
        }
    }
}

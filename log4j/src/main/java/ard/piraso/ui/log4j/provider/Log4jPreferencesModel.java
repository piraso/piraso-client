package ard.piraso.ui.log4j.provider;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Log4j preferences model
 */
public class Log4jPreferencesModel {

    private List<Child> preferences = new ArrayList<Child>();

    public List<Child> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Child> preferences) {
        this.preferences = preferences;
    }

    public void add(String logger, String description) {
        Validate.notEmpty(logger, "logger should not be empty");
        Validate.notEmpty(description, "description should not be empty");

        preferences.add(new Child(logger, description));
        Collections.sort(preferences);
    }

    public static final class Child implements Comparable<Child> {
        private String logger;

        private String description;

        public Child() {
        }

        public Child(String logger, String description) {
            this.description = description;
            this.logger = logger;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogger() {
            return logger;
        }

        public void setLogger(String logger) {
            this.logger = logger;
        }

        @Override
        public int compareTo(Child o) {
            return description.compareTo(o.description);
        }
    }
}

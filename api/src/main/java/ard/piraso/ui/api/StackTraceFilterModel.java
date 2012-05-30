package ard.piraso.ui.api;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * StackTrace filter model
 */
public class StackTraceFilterModel {

    private List<Child> filters = new ArrayList<Child>();

    public List<Child> getFilters() {
        return filters;
    }

    public void setFilters(List<Child> filters) {
        this.filters = filters;
    }

    public void add(String regex, boolean bold) {
        filters.add(new Child(regex, bold));
    }

    public boolean isMatch(String value) {
        if(StringUtils.isNotBlank(value)) {
            String valueToCompare = getValue(value);

            for(Child child : filters) {
                if(valueToCompare.matches(child.getRegex())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isBold(String value) {
        if(StringUtils.isNotBlank(value)) {
            String valueToCompare = getValue(value);
            for(Child child : filters) {
                if(child.isBold() && valueToCompare.matches(child.getRegex())) {
                    return true;
                }
            }
        }

        return false;
    }

    private String getValue(String str) {
        String value = StringUtils.trim(str);

        if(value.startsWith("at ")) {
            value = value.substring(3);
        }

        return value;
    }

    public static final class Child {
        private String regex;

        private boolean bold;

        public Child() {
        }

        public Child(String regex, boolean bold) {
            this.bold = bold;
            this.regex = regex;
        }

        public boolean isBold() {
            return bold;
        }

        public void setBold(boolean bold) {
            this.bold = bold;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }
    }
}

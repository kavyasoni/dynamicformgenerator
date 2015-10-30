package com.example.kavyasoni.dynamicformgenerator;

import android.text.TextUtils;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * Created by kavyasoni on 28/10/15.
 */
public class AttributeSchema {
    protected String label;
    protected String hint;
    protected String type;
    protected String value;
    protected int id;
    protected int priority;
    protected int min_limit;
    protected int max_limit;
    protected int view_type;
    protected List<Option> options;

    public Map<Integer, List<JsonObject>> getToggles() {
        return toggles;
    }

    public void setToggles(Map<Integer, List<JsonObject>> toggles) {
        this.toggles = toggles;
    }

    protected Map<Integer, List<JsonObject>> toggles;

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHint() {
        if (TextUtils.isEmpty(hint))
            return "";
        return hint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMin_limit() {
        return min_limit;
    }

    public void setMin_limit(int min_limit) {
        this.min_limit = min_limit;
    }

    public int getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(int max_limit) {
        this.max_limit = max_limit;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public class Option {
        private String id;
        private String label;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public class WidgetType {
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String STRING = "string";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String INT = "integer";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String MULTI_SELECT = "multi_select";
    }
}

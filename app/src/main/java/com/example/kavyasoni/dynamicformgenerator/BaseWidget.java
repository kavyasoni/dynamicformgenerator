package com.example.kavyasoni.dynamicformgenerator;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BaseWidget extends AttributeSchema {
    protected transient static String TAG = "BaseWidget";
    protected transient LinearLayout attributeLayout;
    protected transient WidgetToggleHandler widgetToggleHandler;
    protected transient HashMap<String, ArrayList<String>> _toggles;
    protected transient LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    protected transient Context context;
    protected transient View rootView;

    void initialiseView(Context context) {
        initialiseView(context, -1);
    }

    public void initialiseView(Context context, int res_view_id) {
        this.context = context;
        if (res_view_id != -1) {
            rootView = LayoutInflater.from(context).inflate(res_view_id, null);
        }
        defaultLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        attributeLayout = new LinearLayout(context);
        attributeLayout.setLayoutParams(defaultLayoutParams);
        attributeLayout.setOrientation(LinearLayout.VERTICAL);
        attributeLayout.setId(id);
        attributeLayout.setTag(this);
    }

    protected void setTogglesView(Map<Integer, List<JsonObject>> toggles) {
        if (toggles != null && !toggles.isEmpty() && attributeLayout != null) {
            for (Map.Entry<Integer, List<JsonObject>> toggle : toggles.entrySet()) {
                LinearLayout toggleLinearLayout = new LinearLayout(context);
                toggleLinearLayout.setLayoutParams(defaultLayoutParams);
                toggleLinearLayout.setOrientation(LinearLayout.VERTICAL);
                toggleLinearLayout.setId(toggle.getKey());
                for (JsonObject attributePropertiesJsonObject :
                        toggle.getValue()) {
                    if (attributePropertiesJsonObject.has("type")) {
                        toggleLinearLayout.addView(AttributeWidgetFactory.getAttributeWidget(context, attributePropertiesJsonObject).getView());
                    }
                }
                attributeLayout.addView(toggleLinearLayout);
            }
        }
    }

    protected int getInputType(String type) {
        if (type.equals(WidgetType.EMAIL)) {
            return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        } else if (type.equals(WidgetType.STRING)) {
            return InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
        } else if (type.equals(WidgetType.NAME)) {
            return InputType.TYPE_TEXT_FLAG_CAP_WORDS;
        } else if (type.equals(WidgetType.PASSWORD)) {
            return InputType.TYPE_TEXT_VARIATION_PASSWORD;
        } else if (type.equals(WidgetType.PHONE)) {
            return InputType.TYPE_CLASS_PHONE;
        } else if (type.equals(WidgetType.INT)) {
            return InputType.TYPE_CLASS_NUMBER;
        } else if (type.equals(WidgetType.DATE) || type.equals(WidgetType.TIME)) {
            return InputType.TYPE_CLASS_DATETIME;
        }
        return InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
    }

    public View getView() {
        return attributeLayout;
    }

    public void setVisibility(int value) {
        attributeLayout.setVisibility(value);
    }

    public boolean isValid() {
        return true;
    }

    /**
     * sets a handler for value changes
     *
     * @param handler
     */
    public void setToggleHandler(WidgetToggleHandler handler) {
        widgetToggleHandler = handler;
    }

}

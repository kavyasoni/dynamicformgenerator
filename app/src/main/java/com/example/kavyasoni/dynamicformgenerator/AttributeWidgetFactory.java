package com.example.kavyasoni.dynamicformgenerator;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kavyasoni on 28/10/15.
 */
public class AttributeWidgetFactory {
    public static <T extends BaseWidget> T getAttributeWidget(Context context, JSONObject attributePropertiesJsonObject) {
        if (attributePropertiesJsonObject.has("type")) {
            try {
                String type = attributePropertiesJsonObject.getString("type");
                if (type.equals(BaseWidget.WidgetType.EMAIL)) {
                    EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
                    editTextWidget.initialiseView(context);
                    return (T) editTextWidget;
                } else if (type.equals(BaseWidget.WidgetType.MULTI_SELECT)) {
                    MultiSelectWidget multiSelectWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), MultiSelectWidget.class);
                    multiSelectWidget.initialiseView(context);
                    return (T) multiSelectWidget;
                } else {
                    EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
                    editTextWidget.initialiseView(context);
                    return (T) editTextWidget;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T extends BaseWidget> T getAttributeWidget(Context context, JsonObject attributePropertiesJsonObject) {
        if (attributePropertiesJsonObject.has("type")) {
            try {
                String type = attributePropertiesJsonObject.getAsJsonPrimitive("type").getAsString();
                if (type.equals(BaseWidget.WidgetType.EMAIL)) {
                    EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
                    editTextWidget.initialiseView(context);
                    return (T) editTextWidget;
                } else if (type.equals(BaseWidget.WidgetType.MULTI_SELECT)) {
                    MultiSelectWidget multiSelectWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), MultiSelectWidget.class);
                    multiSelectWidget.initialiseView(context);
                    return (T) multiSelectWidget;
                } else {
                    EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
                    editTextWidget.initialiseView(context);
                    return (T) editTextWidget;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T extends BaseWidget> T getAttributeWidget(Context context, AttributeSchema attributeSchema) {
        String type = attributeSchema.getType();
        if (type.equals(BaseWidget.WidgetType.EMAIL)) {
            EditTextWidget editTextWidget = (EditTextWidget) attributeSchema;
            editTextWidget.initialiseView(context);
            return (T) editTextWidget;
        } else if (type.equals(BaseWidget.WidgetType.MULTI_SELECT)) {
            MultiSelectWidget multiSelectWidget = (MultiSelectWidget) attributeSchema;
            multiSelectWidget.initialiseView(context);
            return (T) multiSelectWidget;
        } else {
            EditTextWidget editTextWidget = (EditTextWidget) attributeSchema;
            editTextWidget.initialiseView(context);
            return (T) editTextWidget;
        }
    }
}

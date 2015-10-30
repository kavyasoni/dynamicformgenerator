package com.example.kavyasoni.dynamicformgenerator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String ATTRIBUTES_KEY = "attributes";
    private List<BaseWidget> widgetList;
    public static Map<Integer, BaseWidget> widgetMap;
    public static Map<Integer, BaseWidget> toggleMap;
    private View baseContainerLayout;
    private LinearLayout containerLinearLayout;
    private ScrollView scrollViewLayout;
    private final LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        baseContainerLayout = LayoutInflater.from(this).inflate(R.layout.content_main, null);
        scrollViewLayout = (ScrollView) baseContainerLayout.findViewById(R.id.scrollViewLayout);
        baseContainerLayout.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAtrributes();
            }
        });
        generateForm(Utils.parseFileToString(this, "schemas.json"));

//        ViewTreeObserver vtobs = baseContainerLayout.getViewTreeObserver();
//
//        vtobs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int width = layout.getMeasuredWidth();
//                int height = layout.getMeasuredHeight();
//                Log.v("SimpleTest", "Width : " + width);
//                Log.v("SimpleTest", "Height : " + height);
//            }
//        });
    }

    private void saveAtrributes() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (containerLinearLayout.getChildCount() > 0) {
            try {
                for (int i = 0; i < containerLinearLayout.getChildCount(); i++) {
                    LinearLayout childView = (LinearLayout) containerLinearLayout.getChildAt(i);
                    if (widgetMap.containsKey(childView.getId())) {
                        AttributeSchema attributeSchema = getAttributeSchemaWithToggleData(childView, (AttributeSchema) childView.getTag());
                        JSONObject attributeJsonObject = new JSONObject(Utils.convertToJSON(attributeSchema));
                        jsonArray.put(attributeJsonObject);
                    }
                }
                jsonObject.put("attributes", jsonArray);
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        Log.e(TAG, "SavejsonObject : " + jsonObject);
    }

    private AttributeSchema getAttributeSchemaWithToggleData(View childView, AttributeSchema attributeSchema) {

        if (attributeSchema.getToggles() != null && !attributeSchema.getToggles().isEmpty()) {
            for (Map.Entry<Integer, List<JsonObject>> toggle : attributeSchema.getToggles().entrySet()) {
                LinearLayout toggleView = (LinearLayout) childView.findViewById(toggle.getKey());
                if (toggleView != null) {
                    List<JsonObject> toggleJsonObjectList = new ArrayList<JsonObject>();
                    for (int j = 0; j < toggleView.getChildCount(); j++) {
                        View attrView = toggleView.getChildAt(j);
                        AttributeSchema attrSchema = getAttributeSchemaWithToggleData(attrView, (AttributeSchema) attrView.getTag());
                        JsonObject toggleJsonObject = (JsonObject) new JsonParser().parse(Utils.convertToJSON(attrSchema));
                        toggleJsonObjectList.add(toggleJsonObject);
                        attributeSchema.getToggles().put(toggle.getKey(), toggleJsonObjectList);
                    }
                }
            }
        }
        return attributeSchema;
    }


    public void generateForm(String data) {
        widgetList = new ArrayList<BaseWidget>();
        widgetMap = new HashMap<>();
        toggleMap = new HashMap<>();
        try {
            String name;
            BaseWidget widget;
            JSONObject property;
            JSONObject schema = new JSONObject(data);
            JSONArray attributesJsonArray = schema.getJSONArray(ATTRIBUTES_KEY);

            for (int i = 0; i < attributesJsonArray.length(); i++) {
                JSONObject attributeJsonObject = attributesJsonArray.getJSONObject(i);
                String attributeID = attributeJsonObject.getString("id");
                widget = AttributeWidgetFactory.getAttributeWidget(this, attributeJsonObject);
                if (widget == null)
                    continue;
                widgetList.add(widget);
                widgetMap.put(widget.getId(), widget);
            }
        } catch (JSONException e) {
            Log.i(TAG, e.getMessage());
        }

        // -- sort widgets on priority
        Collections.sort(widgetList, new PriorityComparison());

        // -- create the layout
//        baseContainerLayout = new LinearLayout(this);
//        baseContainerLayout.setOrientation(LinearLayout.VERTICAL);
//        baseContainerLayout.setLayoutParams(defaultLayoutParams);

//        scrollViewLayout = new ScrollView(this);
//        scrollViewLayout.setLayoutParams(defaultLayoutParams);

        containerLinearLayout = new LinearLayout(this);
        containerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        containerLinearLayout.setLayoutParams(defaultLayoutParams);
        for (int i = 0; i < widgetList.size(); i++) {
            containerLinearLayout.addView((View) widgetList.get(i).getView());
        }

        scrollViewLayout.addView(containerLinearLayout);
        setContentView(baseContainerLayout);
    }

    protected BaseWidget getWidget(JSONObject attributePropertiesJsonObject) {
        try {
            String type = attributePropertiesJsonObject.getString("type");
            if (type.equals(BaseWidget.WidgetType.EMAIL)) {
                EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
                editTextWidget.initialiseView(this);
                return editTextWidget;
            } else if (type.equals(BaseWidget.WidgetType.MULTI_SELECT)) {
                MultiSelectWidget multiSelectWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), MultiSelectWidget.class);
                multiSelectWidget.initialiseView(this);
                return multiSelectWidget;
            } else {
                EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
                editTextWidget.initialiseView(this);
                return editTextWidget;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        EditTextWidget editTextWidget = Utils.convertToObject(attributePropertiesJsonObject.toString(), EditTextWidget.class);
        editTextWidget.initialiseView(this);
        return editTextWidget;
    }

    class PriorityComparison implements Comparator<BaseWidget> {
        public int compare(BaseWidget item1, BaseWidget item2) {
            return item1.getPriority() > item2.getPriority() ? 1 : -1;
        }
    }
}

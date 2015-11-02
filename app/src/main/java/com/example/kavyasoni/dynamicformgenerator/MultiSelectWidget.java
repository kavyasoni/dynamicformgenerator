package com.example.kavyasoni.dynamicformgenerator;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenfrvr.hashtagview.HashtagView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kavyasoni on 28/10/15.
 */
public class MultiSelectWidget extends BaseWidget {
    protected transient HashtagView tagGroup;
    protected transient TextView labelTextView;
    protected transient LinearLayout rootViewLinearLayout;

    @Override
    public void initialiseView(Context context) {
        super.initialiseView(context, R.layout.multi_select_widget_layout);
        rootViewLinearLayout = (LinearLayout) rootView.findViewById(R.id.rootViewLinearLayout);
        labelTextView = (TextView) rootView.findViewById(R.id.labelTextView);
        labelTextView.setText(getLabel());
        List<Option> optionList = new ArrayList<>();
        optionList.addAll(options);
        tagGroup = (HashtagView) rootView.findViewById(R.id.tags);
        tagGroup.setData(optionList, Transformers.TAG_ITEM_DATA_TRANSFORM, Selectors.TAG_ITEM_DATA_SELECTOR);
        attributeLayout.addView(rootView);
        setTogglesView(getToggles());
        tagGroup.addOnTagSelectListener(new HashtagView.TagsSelectListener() {
            @Override
            public void onItemSelected(Object item, boolean selected) {
                Option option = (Option) item;
                if (selected) {
                    option.setValue(true);
                } else {
                    option.setValue(false);
                }
                options.add(option);
                value = Utils.convertToJSON(options);
                Log.e(TAG, value);
            }
        });
    }


    @Override
    public String getValue() {
        this.value = Utils.convertToJSON(options);
        return value;
    }

    @Override
    public void setValue(String value) {
//        selectedTags.add(value);
    }

    @Override
    public void setHint(String value) {
    }

    @Override
    public boolean isValid() {
        if (min_limit > 0 && options.isEmpty())
            return false;
        else {

        }
        return true;
    }
}

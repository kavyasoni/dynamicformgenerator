package com.example.kavyasoni.dynamicformgenerator;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by kavyasoni on 28/10/15.
 */
public class MultiSelectWidget extends BaseWidget {
    protected transient TagGroup tagGroup;
    protected transient Set<String> selectedTags;
    protected transient TextView labelTextView;
    protected transient LinearLayout rootViewLinearLayout;

    @Override
    public void initialiseView(Context context) {
        super.initialiseView(context, R.layout.multi_select_widget_layout);
        selectedTags = new HashSet<>();
        rootViewLinearLayout = (LinearLayout) rootView.findViewById(R.id.rootViewLinearLayout);
        labelTextView = (TextView) rootView.findViewById(R.id.labelTextView);
        labelTextView.setText(getLabel());
        tagGroup = (TagGroup) rootView.findViewById(R.id.tagGroup);
        tagGroup.setTags(getTagOptions());
        tagGroup.setOnTagClickListener(mTagClickListener);
        attributeLayout.addView(rootView);
        setTogglesView(getToggles());
    }

    private TagGroup.OnTagClickListener mTagClickListener = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(String tag) {
            selectedTags.add(tag);
            value = Arrays.toString(selectedTags.toArray());
        }
    };

    private List<String> getTagOptions() {
        List<String> tagStringList = new ArrayList<>();
        if (options != null) {
            for (Option option : options) {
                tagStringList.add(option.getLabel());
            }
        }
        return tagStringList;
    }

    @Override
    public String getValue() {
        this.value = Arrays.toString(selectedTags.toArray());
        return value;
    }

    @Override
    public void setValue(String value) {
        selectedTags.add(value);
    }

    @Override
    public void setHint(String value) {
    }

    @Override
    public boolean isValid() {
        if (min_limit > 0 && selectedTags.isEmpty())
            return false;
        else {

        }
        return true;
    }
}

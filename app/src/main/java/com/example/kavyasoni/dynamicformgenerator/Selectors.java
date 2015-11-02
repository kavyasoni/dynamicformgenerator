package com.example.kavyasoni.dynamicformgenerator;

import com.greenfrvr.hashtagview.HashtagView;

/**
 * Created by Austere on 11/2/15.
 */
public class Selectors {
    public static final HashtagView.DataSelector<AttributeSchema.Option> TAG_ITEM_DATA_SELECTOR = new HashtagView.DataSelector<AttributeSchema.Option>() {
        @Override
        public boolean preselect(AttributeSchema.Option item) {
            return item.isValue();
        }
    };
}

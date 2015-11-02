package com.example.kavyasoni.dynamicformgenerator;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.greenfrvr.hashtagview.HashtagView;

/**
 * Created by Austere on 11/2/15.
 */
public class Transformers {

    public static final HashtagView.DataTransform<AttributeSchema.Option> TAG_ITEM_DATA_TRANSFORM = new HashtagView.DataStateTransform<AttributeSchema.Option>() {
        @Override
        public CharSequence prepareSelected(AttributeSchema.Option item) {
            SpannableString spannableString = new SpannableString(item.getLabel());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FCFCFE")), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public CharSequence prepare(AttributeSchema.Option item) {
            SpannableString spannableString = new SpannableString(item.getLabel());
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#26BC1E")), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;

        }
    };


}

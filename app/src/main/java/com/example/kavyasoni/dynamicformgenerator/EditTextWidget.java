package com.example.kavyasoni.dynamicformgenerator;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextWidget extends BaseWidget {
    protected transient TextView labelTextView;
    protected transient EditText inputEditText;

    @Override
    public void initialiseView(Context context) {
        super.initialiseView(context, R.layout.edit_text_widget_layout);
        labelTextView = (TextView) rootView.findViewById(R.id.labelTextView);
        labelTextView.setText(getLabel());
        inputEditText = (EditText) rootView.findViewById(R.id.inputEditText);
        inputEditText.setHint(getHint());
        if (!TextUtils.isEmpty(value)) {
            inputEditText.setText(value);
        }
        attributeLayout.addView(rootView);
        inputEditText.setInputType(getInputType(type));
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                value = inputEditText.getText().toString();
            }
        });
        setTogglesView(getToggles());
    }

    @Override
    public String getValue() {
        this.value = inputEditText.getText().toString();
        return value;
    }

    @Override
    public void setValue(String value) {
        inputEditText.setText(value);
    }

    @Override
    public void setHint(String value) {
        inputEditText.setHint(value);
    }

    @Override
    public boolean isValid() {
        if (min_limit > 0 && TextUtils.isEmpty(inputEditText.getText()))
            return false;
        else {
            if (type.equals(WidgetType.EMAIL)) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(getValue()).matches();
            } else if (getValue().length() < min_limit || getValue().length() > max_limit) {
                return false;
            }
        }
        return true;
    }

}

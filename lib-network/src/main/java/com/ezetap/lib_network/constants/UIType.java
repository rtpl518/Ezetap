package com.ezetap.lib_network.constants;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({UIType.LABEL, UIType.LABEL_VALUE, UIType.EDITTEXT, UIType.IMAGEVIEW, UIType.BUTTON})
public @interface UIType {
    String LABEL = "label";
    String LABEL_VALUE = "label_value";
    String EDITTEXT = "edittext";
    String IMAGEVIEW = "imageview";
    String BUTTON = "button";
}

package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.properties.FormsPropertyHandler;
import com.bincsoft.forms.properties.IFormsProperty;

public class FormsGaugePropertyHandler extends FormsPropertyHandler {
    /**
     * Gets the property of the implementation class.
     * @return
     * @see IFormsProperty
     */
    @Override
    public IFormsProperty getFormsProperty() {
        return FormsGaugeProperty.valueOf(getClass().getName());
    }
}

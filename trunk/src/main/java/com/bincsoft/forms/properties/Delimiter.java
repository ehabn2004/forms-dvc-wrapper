package com.bincsoft.forms.properties;

import com.bincsoft.forms.BincsoftBean;

public class Delimiter extends FormsPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            bean.setDelimiter(sParams);
            log("SET_DELIMITER: Delimiter value is now '" + bean.getDelimiter() + "'");
        }
        return true;
    }

    @Override
    public IFormsProperty getFormsProperty() {
        return super.getFormsProperty();
    }
}

package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import java.util.logging.Level;

public class DeprecatedProperty extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        log(Level.WARNING, "Property is deprecated, don't use it!");
        return true;
    }
}

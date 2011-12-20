package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class Deprecated implements IFormsGraphProperty {
    public Deprecated() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        System.out.println("Property is deprecated, don't use it!");
        return true;
    }
}

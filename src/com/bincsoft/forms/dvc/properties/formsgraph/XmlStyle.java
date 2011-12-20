package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.util.CustomStyle;

public class XmlStyle implements IFormsGraphProperty {
    public XmlStyle() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("SET_XML_STYLE: Setting style from string...");
        try {
            CustomStyle style = new CustomStyle(sParams);
            graph.getGraph().setStyle(style);
        } catch (Exception ex) {
            graph.debugMessage("SET_XML_STYLE: " + ex);
        }
        graph.debugMessage("SET_XML_STYLE: Done!");
        return true;
    }
}

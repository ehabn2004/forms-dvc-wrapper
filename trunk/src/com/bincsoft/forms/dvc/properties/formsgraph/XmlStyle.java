package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.util.CustomStyle;

public class XmlStyle extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("SET_XML_STYLE: Setting style from string...");
        try {
            CustomStyle style = new CustomStyle(sParams);
            graph.getGraph().setStyle(style);
        } catch (Exception ex) {
            log("SET_XML_STYLE: " + ex);
        }
        log("SET_XML_STYLE: Done!");
        return true;
    }
}

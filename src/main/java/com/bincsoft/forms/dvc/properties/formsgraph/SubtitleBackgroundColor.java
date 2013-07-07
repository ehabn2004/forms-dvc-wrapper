package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;

import java.awt.Color;

public class SubtitleBackgroundColor extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                log("SET_SUBTITLE_BACKGROUND: setting " + sParams + " as a new background color");
                graph.getGraph().getDataviewSubtitle().setBackground(col);
            } else {
                log("SET_SUBTITLE_BACKGROUND: " + sParams +
                                   " passed is not a valid color name or RGB value");
            }
        } else {
            log("SET_SUBTITLE_BACKGROUND: no color specified");
        }
        return true;
    }
}

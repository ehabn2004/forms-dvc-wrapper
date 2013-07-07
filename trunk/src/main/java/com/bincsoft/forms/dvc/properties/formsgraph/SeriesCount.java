package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import oracle.dss.util.EdgeOutOfRangeException;

public class SeriesCount extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        // retrieve graph column count for further usage
        log("SERIES COUNT requested");
        // if rows shown as series then row number = column number
        try {
            if (graph.isShowGraphAsSeries()) {
                graph.returnSeriesCount(graph.getGraph().getRowCount());
            } else {
                graph.returnSeriesCount(graph.getGraph().getColumnCount());
            }
        } catch (EdgeOutOfRangeException ex) {
            log("SERIES_COUNT: " + ex);
        }
        return true;
    }
}

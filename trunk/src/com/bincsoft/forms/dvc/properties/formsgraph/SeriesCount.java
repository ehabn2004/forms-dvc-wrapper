package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import oracle.dss.util.EdgeOutOfRangeException;

public class SeriesCount implements IFormsGraphProperty {
    public SeriesCount() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        // retrieve graph column count for further usage
        graph.debugMessage("SERIES COUNT requested");
        // if rows shown as series then row number = column number
        try {
            if (graph.isShowGraphAsSeries()) {
                graph.returnSeriesCount(graph.getGraph().getRowCount());
            } else {
                graph.returnSeriesCount(graph.getGraph().getColumnCount());
            }
        } catch (EdgeOutOfRangeException ex) {
            graph.debugMessage("SERIES_COUNT: " + ex);
        }
        return true;
    }
}

package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.ColorCodeRegistry;

import java.awt.Color;

import java.util.StringTokenizer;

import oracle.dss.util.DataDirector;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SeriesOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


public class SeriesColor extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            // separate series name from string
            StringTokenizer st = new StringTokenizer(sParams, graph.getDelimiter());

            // at least two arguments must be found
            if (st.countTokens() >= 2) {

                String strColor = sParams.substring(sParams.indexOf(graph.getDelimiter()) + 1);
                String seriesName = sParams.substring(0, sParams.indexOf(graph.getDelimiter()));

                Color col = ColorCodeRegistry.getColorCode(strColor);
                if (col != null) {
                    log("SET_SERIES_COLOR: setting " + strColor + " as a new series color");

                    int series_count = 0;
                    try {
                        series_count = graph.getGraph().getColumnCount();
                    } catch (EdgeOutOfRangeException ex) {
                        log("SET_SERIES_COLOR: " + ex);
                    }

                    for (int i = 0; i < series_count; i++) {
                        String shortLabel = "";
                        try {
                            shortLabel =
                                    (String)graph.getGraph().getDataAccessSliceLabel(DataDirector.COLUMN_EDGE, i, MetadataMap.METADATA_LONGLABEL);
                        } catch (EdgeOutOfRangeException ex) {
                            log("SET_SERIES_COLOR: " + ex);
                        } catch (SliceOutOfRangeException ex) {
                            log("SET_SERIES_COLOR: " + ex);
                        }

                        if (seriesName.trim().equalsIgnoreCase(shortLabel.trim())) {
                            try {
                                graph.getGraph().getSeries().setColor(col, i);

                                log("SET_SERIES_COLOR: Series " + seriesName + " found");
                                break;
                            } catch (SeriesOutOfRangeException ex) {
                                log("SET_SERIES_COLOR: " + ex);
                                return true;
                            }
                        }
                    }
                    return true;
                } else {
                    log("SET_SERIES_COLOR: " + sParams +
                                       " does not contain a valid color name or RGB value");
                }
            } else {
                log("SET_SERIES_COLOR: no color specified");
            }
        } else {
            log("SET_SERIES_COLOR: not enough arguments");
        }
        return true;
    }
}

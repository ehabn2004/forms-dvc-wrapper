package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import java.util.StringTokenizer;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SeriesOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


public class SeriesMarkerType extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            // separate series name from string
            StringTokenizer st =
                new StringTokenizer(sParams, graph.getDelimiter());
            // at least two arguments must be found
            if (st.countTokens() >= 2) {
                String seriesType =
                    sParams.substring(sParams.indexOf(graph.getDelimiter()) +
                                      1);
                String seriesName =
                    sParams.substring(0, sParams.indexOf(graph.getDelimiter())).trim();

                int series_type;
                if ("MT_AREA".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_AREA;
                else if ("MT_BAR".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_BAR;
                else if ("MT_MARKER".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_MARKER;
                else if ("MT_DEFAULT".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_DEFAULT;
                else {
                    log("Property SET_SERIES_MARKER_TYPE: " +
                                       sParams +
                                       " does not contain a valid marker type");
                    return true;
                }

                int series_count = 0;

                try {
                    series_count = graph.getGraph().getColumnCount();
                } catch (EdgeOutOfRangeException ex) {
                    log("SET_SERIES_MARKER_TYPE: " + ex);
                    return true;
                }

                for (int i = 0; i < series_count; i++) {
                    String shortLabel = "";
                    try {
                        shortLabel =
                                (String)graph.getGraph().getDataAccessSliceLabel(DataDirector.COLUMN_EDGE,
                                                                                 i,
                                                                                 MetadataMap.METADATA_LONGLABEL);
                    } catch (SliceOutOfRangeException ex) {
                        log("SET_SERIES_MARKER_TYPE: " + ex);
                        return true;
                    } catch (EdgeOutOfRangeException ex) {
                        log("SET_SERIES_MARKER_TYPE: " + ex);
                        return true;
                    }
                    if (seriesName.equalsIgnoreCase(shortLabel.trim())) {
                        try {
                            graph.getGraph().getSeries().setMarkerType(series_type,
                                                                       i);
                            log("Property SET_SERIES_MARKER_TYPE: Series '" +
                                               seriesName + "' found");
                            break;
                        } catch (SeriesOutOfRangeException sour) {
                            log("Property SET_SERIES_MARKER_TYPE: Series out of range exception");
                            return true;
                        }
                    }
                }
                return true;
            } else {
                log("SET_SERIES_MARKER_TYPE: not enough arguments specified in " +
                                   sParams);
                return true;
            }
        } else {
            log("SET_SERIES_MARKER_TYPE: no argument specified");
            return true;
        }
    }
}

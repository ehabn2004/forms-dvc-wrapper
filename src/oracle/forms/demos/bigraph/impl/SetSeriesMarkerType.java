package oracle.forms.demos.bigraph.impl;

import java.util.StringTokenizer;

import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SeriesOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class SetSeriesMarkerType implements IFGPropImpl {
    public static final ID propertyId =
        ID.registerProperty("SET_SERIES_MARKER_TYPE");

    public SetSeriesMarkerType() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() > 0) {
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
                    graph.DebugMessage("Property SET_SERIES_MARKER_TYPE: " +
                                       sParams +
                                       " does not contain a valid marker type");
                    return true;
                }

                int series_count = 0;

                try {
                    series_count = graph.getGraph().getColumnCount();
                } catch (EdgeOutOfRangeException ex) {
                    graph.DebugMessage("SET_SERIES_MARKER_TYPE: " + ex);
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
                        graph.DebugMessage("SET_SERIES_MARKER_TYPE: " + ex);
                        return true;
                    } catch (EdgeOutOfRangeException ex) {
                        graph.DebugMessage("SET_SERIES_MARKER_TYPE: " + ex);
                        return true;
                    }
                    if (seriesName.equalsIgnoreCase(shortLabel.trim())) {
                        try {
                            graph.getGraph().getSeries().setMarkerType(series_type,
                                                                       i);
                            graph.DebugMessage("Property SET_SERIES_MARKER_TYPE: Series '" +
                                               seriesName + "' found");
                            break;
                        } catch (SeriesOutOfRangeException sour) {
                            graph.DebugMessage("Property SET_SERIES_MARKER_TYPE: Series out of range exception");
                            return true;
                        }
                    }
                }
                return true;
            } else {
                graph.DebugMessage("SET_SERIES_MARKER_TYPE: not enough arguments specified in " +
                                   sParams);
                return true;
            }
        } else {
            graph.DebugMessage("SET_SERIES_MARKER_TYPE: no argument specified");
            return true;
        }
    }
}

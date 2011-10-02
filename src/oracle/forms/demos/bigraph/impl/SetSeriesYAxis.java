package oracle.forms.demos.bigraph.impl;

import java.util.StringTokenizer;

import oracle.dss.util.DataDirector;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SeriesOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class SetSeriesYAxis implements IFGPropImpl {
    public static final ID propertyId =
        ID.registerProperty("SET_SERIES_Y_AXIS");

    public SetSeriesYAxis() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if (sParams.length() > 0) {
            try {
                // separate series name from string
                StringTokenizer st =
                    new StringTokenizer(sParams, graph.getDelimiter());
                // at least two arguments must be found
                if (st.countTokens() >= 2) {
                    String seriesYAxis =
                        sParams.substring(sParams.indexOf(graph.getDelimiter()) +
                                          1);
                    String seriesName =
                        sParams.substring(0, sParams.indexOf(graph.getDelimiter())).trim();
                    int series_count = graph.getGraph().getColumnCount();
                    graph.DebugMessage("SET_SERIES_Y_AXIS: Looking for a series named '" +
                                       seriesName + "'...");
                    for (int i = 0; i < series_count; i++) {
                        String shortLabel =
                            (String)graph.getGraph().getDataAccessSliceLabel(DataDirector.COLUMN_EDGE,
                                                                             i,
                                                                             MetadataMap.METADATA_LONGLABEL);
                        if (seriesName.equalsIgnoreCase(shortLabel.trim())) {
                            // m_graph.getSeries().setAssignedToY2(!m_graph.getSeries().isAssignedToY2(i),i);
                            graph.getGraph().getSeries().setAssignedToY2("Y2".equalsIgnoreCase(seriesYAxis),
                                                                         i);
                            graph.DebugMessage("SET_SERIES_Y_AXIS: Series '" +
                                               seriesName + "' found");
                            break;
                        }
                    }
                    return true;
                } else {
                    graph.DebugMessage("SET_SERIES_Y_AXIS: not enough arguments specified in " +
                                       sParams);
                    return true;
                }
            } catch (EdgeOutOfRangeException ex) {
                graph.DebugMessage("SET_SERIES_Y_AXIS: " + ex);
                return true;
            } catch (SeriesOutOfRangeException ex) {
                graph.DebugMessage("SET_SERIES_Y_AXIS: " + ex);
                return true;
            } catch (SliceOutOfRangeException ex) {
                graph.DebugMessage("SET_SERIES_Y_AXIS: " + ex);
                return true;
            }
        } else {
            graph.DebugMessage("SET_SERIES_Y_AXIS: no argument specified");
            return true;
        }
    }
}

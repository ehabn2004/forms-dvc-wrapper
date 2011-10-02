package oracle.forms.demos.bigraph.impl;

import java.util.StringTokenizer;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class ExplodePieSlice implements IFGPropImpl {
    public static final ID propertyId =
        ID.registerProperty("EXPLODE_PIESLICE");

    public ExplodePieSlice() {
        super();
    }

    /**
     * Moves the selected slice number away from the rest
     * Forms property: EXPLODE_PIESLICE
     * @param sParams
     * @return
     */
    public boolean handleProperty(String sParams, FormsGraph graph) {
        // _object contains a delimited String containing values, where
        // the second value must be between 0 and 100. The first should not exceed
        // the number of graph columns available or be negative

        if (sParams.length() == 0) {
            // go read the manual !
            graph.DebugMessage("EXPLODE_PIESLICE requires attribute passed. please refer to the" +
                               " documentation on how to use this property.");
        } else {
            // some exceptions may be raised on the way, so we prep for it
            try {
                StringTokenizer st =
                    new StringTokenizer(sParams, graph.getDelimiter());
                String[] val = new String[2];
                int tokenLength = st.countTokens();
                if (tokenLength != 2) {
                    // go read the manual !
                    graph.DebugMessage("EXPLODE_PIESLICE requires attribute passed. please refer to the" +
                                       " documentation on how to use this property.");
                } else {
                    val[0] = (String)st.nextElement();
                    val[1] = (String)st.nextElement();

                    graph.DebugMessage("EXPLODE_PIESLICE: Attributes passed: " +
                                       sParams);
                    graph.DebugMessage("EXPLODE_PIESLICE: After untokenizing String: series=" +
                                       val[0] + " and explode rate=" + val[1]);

                    int Series = 0;
                    int ExpRate = 0;

                    Series = (new Double(val[0])).intValue();
                    ExpRate = (new Double(val[1])).intValue();

                    // if you get here the there wasn't any numeric exceptions check if series is below or over Graph range and adjust if necessary
                    Series =
                            (Series < 0 ? 0 : Series > (graph.getGraph().getSeriesObjectCount() -
                                                        1) ?
                                              (graph.getGraph().getSeriesObjectCount() -
                                               1) : Series);
                    // check explode rate between 0 and 100, if not set
                    // to edge values
                    ExpRate =
                            (ExpRate < 0 ? 0 : (ExpRate > 100 ? 100 : ExpRate));

                    // graphSeries = new Series((CommonGraph)m_graph);
                    // graphSeries.setPieSliceExplode(ExpRate,Series);
                    graph.getGraph().getSeries().setPieSliceExplode(ExpRate,
                                                                    Series);
                }
            } catch (NumberFormatException nfe) {
                graph.DebugMessage("EXPLODE_PIESLICE: Not a valid integer format passed");
                graph.DebugMessage(nfe.getMessage());
            }

            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return true;
    }
}

package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

import java.util.StringTokenizer;

public class ExplodePieSlice extends FormsGraphPropertyHandler {

    /**
     * Moves the selected slice number away from the rest
     * Forms property: EXPLODE_PIESLICE
     * @param sParams
     * @return
     */
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        // _object contains a delimited String containing values, where
        // the second value must be between 0 and 100. The first should not exceed
        // the number of graph columns available or be negative

        if (sParams.length() == 0) {
            // go read the manual !
            log("EXPLODE_PIESLICE requires attribute passed. please refer to the" +
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
                    log("EXPLODE_PIESLICE requires attribute passed. please refer to the" +
                                       " documentation on how to use this property.");
                } else {
                    val[0] = (String)st.nextElement();
                    val[1] = (String)st.nextElement();

                    log("EXPLODE_PIESLICE: Attributes passed: " +
                                       sParams);
                    log("EXPLODE_PIESLICE: After untokenizing String: series=" +
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
                log("EXPLODE_PIESLICE: Not a valid integer format passed");
                log(nfe.getMessage());
            }

            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return true;
    }
}

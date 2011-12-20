package com.bincsoft.forms.dvc;

import com.bincsoft.forms.dvc.properties.formsgraph.ReturnValues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Calendar;

import javax.swing.Timer;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.dss.dataView.SeriesComponentHandle;
import oracle.dss.dataView.ViewMouseEvent;
import oracle.dss.dataView.ViewMouseListener;
import oracle.dss.graph.Graph;
import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataMap;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;


/**
 * mViewMouseListener implements ViewMouseListener, handling mouse events
 * in the BI Graph
 */
public class GraphViewMouseListener implements ViewMouseListener, ActionListener {
    Graph m_graph = null;
    FormsGraph mfg = null;
    LocalRelationalData mlrd = null;

    private ViewMouseEvent objEvent = null;
    private Timer objTimer = null;

    public GraphViewMouseListener(Graph g, FormsGraph fg, LocalRelationalData lrd) {
        m_graph = g;
        mfg = fg;
        mlrd = lrd;
    }

    /**
     * If mouse interaction is enabled in Forms then the following values are returned in a single
     * delimited string.
     * <p>
     * Label of the row:                for the Emp table this would be e.g. the employees name ("Smith")
     * </p><p>
     * Label of the column:             for the Emp table this would be e.g. Sal
     * </p><p>
     * Value of the graph:              the value is returned in 00.00 format
     * </p><p>
     * Column number of the bar in the
     * graph:                           showing e.g. "Sal" and "Commission" will return
     *                                  1 for "Sal" and 2 for "Commission"
     * </p><p>
     * Row number of the bar in the
     * graph:                           Showing e.g. Salary for "Smith", "Jones" and "King" will return
     *                                  1 for "Smtith", 2 for "Jones" and 3 form "King"
     * </p><p>
     * The string values are separated by the actual delimiter used, the default delimiter
     * is ",".
     * </p>
     *
     */

    public void actionPerformed(ActionEvent evt) {
        // Handle the click event
        Calendar rightNow = Calendar.getInstance();
        debugMessage("mouseClicked() - Timer - Timer fired: " + rightNow.getTimeInMillis());

        int iColNum = 0;
        int iRowNum = 0;

        try {
            debugMessage("mouseClicked() - Timer - No. of clicks: " + objEvent.getClickCount());
            debugMessage("mouseClicked() - Timer - paramString: " + objEvent.paramString());

            ComponentHandle ch = objEvent.getComponentHandle();
            Object graphData = null;
            String rowLabel = "", columnLabel = "";

            // if the graph type is of PIE_BAR then clicking on one slice
            // drills down to the details - shown in the bar. In case of a
            // PIE_BAR the pie is not of type data component, but series c
            // component

            if (ch instanceof SeriesComponentHandle) {
                if ((m_graph.getGraphType() == Graph.PIE_BAR) || (m_graph.getGraphType() == Graph.RING_BAR)) {
                    debugMessage("mouseClicked() - Timer - detect Mouse Click onto Series component");
                    m_graph.setPieBarSeries(((SeriesComponentHandle)ch).getSeries());
                }
            }

            if (ch instanceof DataComponentHandle) {
                iColNum = ((DataComponentHandle)ch).getColumn();
                iRowNum = ((DataComponentHandle)ch).getRow();

                debugMessage("mouseClicked() - Timer - Number of column in graph: " + (iColNum + 1));
                debugMessage("mouseClicked() - Timer - Number of row in graph: " + (iRowNum + 1));

                graphData =
                        m_graph.getGraphModel().getDataAccess().getValue(iRowNum, iColNum, DataMap.DATA_UNFORMATTED);
                debugMessage("mouseClicked() - Timer - Data value clicked on: " + graphData.toString());

                columnLabel =
                        (String)m_graph.getDataAccessSliceLabel(DataDirector.COLUMN_EDGE, iColNum, MetadataMap.METADATA_LONGLABEL);
                debugMessage("mouseClicked() - Timer - Column Name clicked on: " + columnLabel);

                rowLabel =
                        (String)m_graph.getDataAccessSliceLabel(DataDirector.ROW_EDGE, iRowNum, MetadataMap.METADATA_LONGLABEL);
                debugMessage("mouseClicked() - Timer - Row Name clicked on: " + rowLabel);

                /*
              * ********************************
              * Dispatch graph information to
              * Forms
              *********************************/

                String sDelimiter = mfg.getDelimiter();
                String GraphInfo = "";

                /*
               * Determine the data returned by this mouse click
               */
                if (mfg.getReturnValueSelection() == ReturnValues.ALL_DATA) {
                    String dataObject = null;

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (mfg.isShowGraphAsSeries()) {
                        dataObject = rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString();
                    } else {
                        dataObject = columnLabel + sDelimiter + rowLabel + sDelimiter + graphData.toString();
                    }
                    dataObject = mlrd.getPrimaryKey(dataObject, sDelimiter);

                    GraphInfo =
                            dataObject != null ? rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString() +
                            sDelimiter + dataObject :
                            rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString();

                    //GraphInfo =rowLabel+sDelimiter+columnLabel+sDelimiter+graphData.toString();
                    debugMessage("mouseClicked() - Timer - Dispatch value '" + GraphInfo + "' to Forms");
                    mfg.dispatchMouseAction(GraphInfo);
                } else if (mfg.getReturnValueSelection() == ReturnValues.DATA_LABEL) {
                    debugMessage("mouseClicked() - Timer - Mouse Click returns rowLabel");

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (mfg.isShowGraphAsSeries()) {
                        GraphInfo = columnLabel;
                        debugMessage("mouseClicked() - Timer - Dispatch value '" + GraphInfo + "' to Forms");
                    } else {
                        GraphInfo = rowLabel;
                        debugMessage("mouseClicked() - Timer - Dispatch value '" + GraphInfo + "'' to Forms");
                    }
                    mfg.dispatchMouseAction(GraphInfo);
                } else if (mfg.getReturnValueSelection() == ReturnValues.DATA_COLUMN) {
                    debugMessage("mouseClicked() - Timer - Mouse Click returns columnLabel");

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (mfg.isShowGraphAsSeries()) {
                        GraphInfo = rowLabel;
                        debugMessage("mouseClicked() - Timer - Dispatch value '" + GraphInfo + "' to Forms");

                    } else {
                        GraphInfo = columnLabel;
                        debugMessage("mouseClicked() - Timer - Dispatch value '" + GraphInfo + "' to Forms");
                    }

                    mfg.dispatchMouseAction(GraphInfo);
                } else if (mfg.getReturnValueSelection() == ReturnValues.DATA_VALUE) {
                    debugMessage("mouseClicked() - Timer - Mouse Click returns rowValue");
                    GraphInfo = graphData.toString();
                    debugMessage("mouseClicked() - Timer - Dispatch value '" + GraphInfo + "' to Forms");
                    mfg.dispatchMouseAction(GraphInfo);
                }
                //  return primary key only
                else if (mfg.getReturnValueSelection() == ReturnValues.DATA_PRIMARY_KEY) {
                    String dataObject = null;

                    debugMessage("mouseClicked() - Timer - Mouse Click returns Primary Key");

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (mfg.isShowGraphAsSeries()) {
                        dataObject = rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString();
                    } else {
                        dataObject = columnLabel + sDelimiter + rowLabel + sDelimiter + graphData.toString();
                    }
                    GraphInfo = mlrd.getPrimaryKey(dataObject, sDelimiter);
                    debugMessage("mouseClicked() - Timer - Dispatch value " + GraphInfo + " to Forms");
                    mfg.dispatchMouseAction(GraphInfo);
                } else {
                    debugMessage("mouseClicked() - Timer - Mouse Click returns no value");
                    // no value as none is selected for returnValueSelection
                }
            }
        } catch (ColumnOutOfRangeException coore) {
            coore.printStackTrace();
        } catch (RowOutOfRangeException roore) {
            roore.printStackTrace();
        } catch (SliceOutOfRangeException soore) {
            soore.printStackTrace();
        } catch (EdgeOutOfRangeException eoore) {
            eoore.printStackTrace();
        }
        debugMessage("mouseClicked() - Timer - Completed");
    }

    public void mouseClicked(ViewMouseEvent p0) {
        debugMessage("mouseClicked(): " + p0.getClickCount());
        objEvent = p0;
        Calendar rightNow = Calendar.getInstance();
        if (objTimer == null) {
            debugMessage("mouseClicked() - Starting timer: " + rightNow.getTimeInMillis());
            objTimer = new Timer(500, this);
            objTimer.setRepeats(false);
            objTimer.start();
        } else {
            debugMessage("mouseClicked() - Restarting timer: " + rightNow.getTimeInMillis());
            objTimer.restart();
        }
        debugMessage("mouseClicked(): Completed");
    }

    public void mousePressed(ViewMouseEvent p0) {
        debugMessage("mousePressed()");
    }

    public void mouseReleased(ViewMouseEvent p0) {
        debugMessage("mouseReleased()");
    }

    public void mouseEntered(ViewMouseEvent p0) {
        //debugMessage("mouseEntered()");
    }

    public void mouseExited(ViewMouseEvent p0) {
        //debugMessage("mouseExited()");
    }

    private void debugMessage(String dm) {
        mfg.debugMessage(dm);
    }
}

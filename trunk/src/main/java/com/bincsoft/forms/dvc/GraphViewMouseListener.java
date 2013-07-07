package com.bincsoft.forms.dvc;


import com.bincsoft.forms.dvc.FormsGraph;
import com.bincsoft.forms.dvc.properties.formsgraph.ReturnValues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Logger log = Logger.getLogger(getClass().getName());
    private FormsGraph formsGraph = null;

    private ViewMouseEvent objEvent = null;
    private Timer objTimer = null;

    public GraphViewMouseListener(FormsGraph fg) {
        formsGraph = fg;
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
    @Override
    public void actionPerformed(ActionEvent evt) {
        // Handle the click event
        Calendar rightNow = Calendar.getInstance();
        log.log(Level.FINE, "mouseClicked() - Timer - Timer fired: " + rightNow.getTimeInMillis());

        int iColNum = 0;
        int iRowNum = 0;

        try {
            log.log(Level.FINE, "mouseClicked() - Timer - No. of clicks: " + objEvent.getClickCount());
            log.log(Level.FINE, "mouseClicked() - Timer - paramString: " + objEvent.paramString());

            ComponentHandle ch = objEvent.getComponentHandle();
            Object graphData = null;
            String rowLabel = "", columnLabel = "";

            // if the graph type is of PIE_BAR then clicking on one slice
            // drills down to the details - shown in the bar. In case of a
            // PIE_BAR the pie is not of type data component, but series c
            // component

            if (ch instanceof SeriesComponentHandle) {
                if ((formsGraph.getGraph().getGraphType() == Graph.PIE_BAR) || (formsGraph.getGraph().getGraphType() == Graph.RING_BAR)) {
                    log.log(Level.FINE, "mouseClicked() - Timer - detect Mouse Click onto Series component");
                    formsGraph.getGraph().setPieBarSeries(((SeriesComponentHandle)ch).getSeries());
                }
            }

            if (ch instanceof DataComponentHandle) {
                iColNum = ((DataComponentHandle)ch).getColumn();
                iRowNum = ((DataComponentHandle)ch).getRow();

                log.log(Level.FINE, "mouseClicked() - Timer - Number of column in graph: " + (iColNum + 1));
                log.log(Level.FINE, "mouseClicked() - Timer - Number of row in graph: " + (iRowNum + 1));

                graphData =
                        formsGraph.getGraph().getGraphModel().getDataAccess().getValue(iRowNum, iColNum, DataMap.DATA_UNFORMATTED);
                log.log(Level.FINE, "mouseClicked() - Timer - Data value clicked on: " + graphData.toString());

                columnLabel =
                        (String)formsGraph.getGraph().getDataAccessSliceLabel(DataDirector.COLUMN_EDGE, iColNum, MetadataMap.METADATA_LONGLABEL);
                log.log(Level.FINE, "mouseClicked() - Timer - Column Name clicked on: " + columnLabel);

                rowLabel =
                        (String)formsGraph.getGraph().getDataAccessSliceLabel(DataDirector.ROW_EDGE, iRowNum, MetadataMap.METADATA_LONGLABEL);
                log.log(Level.FINE, "mouseClicked() - Timer - Row Name clicked on: " + rowLabel);

                /*
              * ********************************
              * Dispatch graph information to
              * Forms
              *********************************/

                String sDelimiter = formsGraph.getDelimiter();
                String graphInfo = "";

                /*
               * Determine the data returned by this mouse click
               */
                if (formsGraph.getReturnValueSelection() == ReturnValues.ALL_DATA) {
                    String dataObject = null;

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (formsGraph.isShowGraphAsSeries()) {
                        dataObject = rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString();
                    } else {
                        dataObject = columnLabel + sDelimiter + rowLabel + sDelimiter + graphData.toString();
                    }
                    dataObject = formsGraph.getLocalRelationalData().getPrimaryKey(dataObject, sDelimiter, formsGraph.isShowGraphAsSeries());

                    graphInfo =
                            dataObject != null ? rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString() +
                            sDelimiter + dataObject :
                            rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString();

                    //GraphInfo =rowLabel+sDelimiter+columnLabel+sDelimiter+graphData.toString();
                    log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value '" + graphInfo + "' to Forms");
                    formsGraph.dispatchMouseAction(graphInfo);
                } else if (formsGraph.getReturnValueSelection() == ReturnValues.DATA_LABEL) {
                    log.log(Level.FINE, "mouseClicked() - Timer - Mouse Click returns rowLabel");

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (formsGraph.isShowGraphAsSeries()) {
                        graphInfo = columnLabel;
                        log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value '" + graphInfo + "' to Forms");
                    } else {
                        graphInfo = rowLabel;
                        log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value '" + graphInfo + "'' to Forms");
                    }
                    formsGraph.dispatchMouseAction(graphInfo);
                } else if (formsGraph.getReturnValueSelection() == ReturnValues.DATA_COLUMN) {
                    log.log(Level.FINE, "mouseClicked() - Timer - Mouse Click returns columnLabel");

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (formsGraph.isShowGraphAsSeries()) {
                        graphInfo = rowLabel;
                        log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value '" + graphInfo + "' to Forms");

                    } else {
                        graphInfo = columnLabel;
                        log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value '" + graphInfo + "' to Forms");
                    }

                    formsGraph.dispatchMouseAction(graphInfo);
                } else if (formsGraph.getReturnValueSelection() == ReturnValues.DATA_VALUE) {
                    log.log(Level.FINE, "mouseClicked() - Timer - Mouse Click returns rowValue");
                    graphInfo = graphData.toString();
                    log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value '" + graphInfo + "' to Forms");
                    formsGraph.dispatchMouseAction(graphInfo);
                }
                //  return primary key only
                else if (formsGraph.getReturnValueSelection() == ReturnValues.DATA_PRIMARY_KEY) {
                    String dataObject = null;

                    log.log(Level.FINE, "mouseClicked() - Timer - Mouse Click returns Primary Key");

                    // if the group (column data) is shown as row data (series) then the rowlabels and column
                    // labels needs to be swapped
                    if (formsGraph.isShowGraphAsSeries()) {
                        dataObject = rowLabel + sDelimiter + columnLabel + sDelimiter + graphData.toString();
                    } else {
                        dataObject = columnLabel + sDelimiter + rowLabel + sDelimiter + graphData.toString();
                    }
                    graphInfo = formsGraph.getLocalRelationalData().getPrimaryKey(dataObject, sDelimiter, formsGraph.isShowGraphAsSeries());
                    log.log(Level.FINE, "mouseClicked() - Timer - Dispatch value " + graphInfo + " to Forms");
                    formsGraph.dispatchMouseAction(graphInfo);
                } else {
                    log.log(Level.FINE, "mouseClicked() - Timer - Mouse Click returns no value");
                    // no value as none is selected for returnValueSelection
                }
            }
        } catch (ColumnOutOfRangeException e) {
        	log.log(Level.SEVERE, "actionPerformed", e);
        } catch (RowOutOfRangeException e) {
        	log.log(Level.SEVERE, "actionPerformed", e);
        } catch (SliceOutOfRangeException e) {
        	log.log(Level.SEVERE, "actionPerformed", e);
        } catch (EdgeOutOfRangeException e) {
        	log.log(Level.SEVERE, "actionPerformed", e);
        }
        log.log(Level.FINE, "mouseClicked() - Timer - Completed");
    }

    @Override
    public void mouseClicked(ViewMouseEvent p0) {
        log.log(Level.FINE, "mouseClicked(): " + p0.getClickCount());
        objEvent = p0;
        Calendar rightNow = Calendar.getInstance();
        if (objTimer == null) {
            log.log(Level.FINE, "mouseClicked() - Starting timer: " + rightNow.getTimeInMillis());
            objTimer = new Timer(500, this);
            objTimer.setRepeats(false);
            objTimer.start();
        } else {
            log.log(Level.FINE, "mouseClicked() - Restarting timer: " + rightNow.getTimeInMillis());
            objTimer.restart();
        }
        log.log(Level.FINE, "mouseClicked(): Completed");
    }

    @Override
    public void mousePressed(ViewMouseEvent p0) {
        log.log(Level.FINE, "mousePressed()");
    }

    @Override
    public void mouseReleased(ViewMouseEvent p0) {
        log.log(Level.FINE, "mouseReleased()");
    }

    public void mouseEntered(ViewMouseEvent p0) {
        //log.log(Level.FINE, "mouseEntered()");
    }

    public void mouseExited(ViewMouseEvent p0) {
        //log.log(Level.FINE, "mouseExited()");
    }
}

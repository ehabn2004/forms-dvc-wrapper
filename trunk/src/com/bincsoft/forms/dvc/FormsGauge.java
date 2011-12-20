package com.bincsoft.forms.dvc;

import com.bincsoft.forms.BincsoftBean;

import java.awt.Color;

import java.util.ArrayList;

import oracle.dss.dataView.DataviewConstants;
import oracle.dss.dataView.ViewMouseEvent;
import oracle.dss.dataView.ViewMouseListener;
import oracle.dss.gauge.DataSpecification;
import oracle.dss.gauge.Gauge;
import oracle.dss.gauge.GaugeAttributes;
import oracle.dss.gauge.GaugeConstants;

import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;
import oracle.forms.ui.VBean;


public class FormsGauge extends BincsoftBean {
    private Gauge m_gauge = null;
    private GaugeDataStore dataStore = null;
    private String sDelimiter = ",";
    private ViewMouseListener mouseListener = null;

    // For creating a bean with multiple gauges
    protected static final ID pSetDataColumns = ID.registerProperty("SET_DATA_COLUMNS"); // Not implemented
    protected static final ID pSetColumnLabels = ID.registerProperty("SET_COLUMN_LABELS"); // Not implemented
    protected static final ID pAddDataRow = ID.registerProperty("ADD_DATA_ROW"); // Not implemented
    protected static final ID pAddDataToGauge = ID.registerProperty("ADD_DATA_TO_GAUGE"); // Not implemented


    // For creating a bean with one gauge
    protected static final ID pAddGaugeData =
        ID.registerProperty("ADD_GAUGE_DATA"); // <Label>, <Initial value>, <Minimum value>, <Maximum value>
    protected static final ID pAddThresholds = ID.registerProperty("ADD_THRESHOLDS");
    protected static final ID pSetThresholdColors = ID.registerProperty("SET_THRESHOLD_COLORS");
    protected static final ID pShowGauge = ID.registerProperty("SHOW_GAUGE");

    protected static final ID pSetValue = ID.registerProperty("SET_VALUE"); // <Value as double>

    protected static final ID pSetGaugeType = ID.registerProperty("SET_GAUGE_TYPE");
    protected static final ID pSetIndicatorType =
        ID.registerProperty("SET_INDICATOR_TYPE"); // LINE (Default), NEEDLE, FILL
    protected static final ID pSetMetricLabelPosition =
        ID.registerProperty("SET_METRIC_LABEL_POS"); // BELOW_GAUGE, INSIDE_GAUGE, INSIDE_GAUGE_LEFT, INSIDE_GAUGE_RIGHT, NONE, WITH_BOTTOM_LABEL (Default)
    protected static final ID pSetMetricLabelNumberType =
        ID.registerProperty("SET_METRIC_LABEL_NUMBER_TYPE"); // NUMBER (Default), PERCENT
    protected static final ID pSetUseThresholdFillColor =
        ID.registerProperty("SET_USE_THRESHOLD_FILL_COLOR"); // TRUE, FALSE (Default)
    protected static final ID pSetBottomLabelPosition =
        ID.registerProperty("SET_BOTTOM_LABEL_POS"); // BELOW_GAUGE (Default), INSIDE_GAUGE, NONE
    protected static final ID pSetBottomLabelText = ID.registerProperty("SET_BOTTOM_LABEL_TEXT");
    protected static final ID pSetTopLabelPosition =
        ID.registerProperty("SET_TOP_LABEL_POS"); // ABOVE_GAUGE (Default), INSIDE_GAUGE, NONE
    protected static final ID pSetTopLabelText = ID.registerProperty("SET_TOP_LABEL_TEXT");
    protected static final ID pSetThresholdsBorderColor = ID.registerProperty("SET_THRESHOLDS_BORDER_COLOR");
    protected static final ID pSetTickLabelContent =
        ID.registerProperty("SET_TICK_LABEL_CONTENT"); // TC_NONE, TC_MIN_MAX, TC_INCREMENTS, TC_THRESHOLD, TC_METRIC

    protected static final ID pSetDelimiter = ID.registerProperty("SET_DELIMITER");
    protected static final ID pGetDelimiter = ID.registerProperty("GET_DELIMITER"); // Not implemented

    protected static final ID eGaugeAction = ID.registerProperty("GAUGE_ACTION");
    protected static final ID pGaugeInfo = ID.registerProperty("GAUGE_INFO");

    public FormsGauge() {
        super();

        m_gauge = new Gauge();
        dataStore = new GaugeDataStore(this);

        // Set initial gauge type
        m_gauge.setGaugeType(GaugeConstants.DIAL);

        // Set white background
        m_gauge.getGaugeBackground().getSFX().setFillType(DataviewConstants.FT_COLOR);

        // Add a mouse listener
        mouseListener = new ViewMouseListener() {
                public void mouseClicked(ViewMouseEvent e) {
                    debugMessage("mouseClicked()");
                    dispatchMouseAction(dataStore.getLabel() + sDelimiter + dataStore.getCurrentValue());
                }

                public void mousePressed(ViewMouseEvent e) {
                }

                public void mouseReleased(ViewMouseEvent e) {
                }

                public void mouseEntered(ViewMouseEvent e) {
                }

                public void mouseExited(ViewMouseEvent e) {
                }
            };

        m_gauge.addViewMouseListener(mouseListener);

        /*================= Begin Test Data =================*/

        // Set initial gauge data
        /*Object[] specs = {DataSpecification.METRIC,
				DataSpecification.THRESHOLD,
				DataSpecification.THRESHOLD,
				DataSpecification.THRESHOLD};

		Object[] row1 = {"", "",
				new Double(0),
				new Double(0),
				new Double(0),
				new Double(0)};*/

        // add the arrays to a List
        //ArrayList dataRows = new ArrayList();
        //dataRows.add(row1);

        // pass the List to this method
        //m_gauge.setTabularData(specs, dataRows);

        /*================= End Test Data =================*/

        // The gauge is shown when the data is set
        m_gauge.setVisible(false);
    }
    
    private String getVersion() {
        return "1.0.3";
    }

    public void init(IHandler handler) {
        super.init(handler);
        super.add(m_gauge);
        System.out.println(getClass().getName() + " version '" + getVersion() + "' started, " +
                           m_gauge.getClass().getName() + " version '" + m_gauge.getVersion() + "'.");
    }

    private void addDataToGauge() {
    }

    public boolean setProperty(ID _ID, Object _object) {
        try {
            /**
		   * =================== Setting gauge data ==================
		   */

            /**
		   * ADD_GAUGE_DATA
		   */
            if (_ID == pAddGaugeData) {
                String[] aParams = ((String)_object).split(sDelimiter);
                debugMessage("ADD_GAUGE_DATA: Setting the following values...");
                debugMessage("ADD_GAUGE_DATA: Label '" + aParams[0] + "'.");
                debugMessage("ADD_GAUGE_DATA: Current value '" + aParams[1] + "'.");
                debugMessage("ADD_GAUGE_DATA: Min value '" + aParams[2] + "'.");
                debugMessage("ADD_GAUGE_DATA: Max value '" + aParams[3] + "'.");
                dataStore.reset();
                if (aParams.length == 4) {
                    dataStore.setLabel(aParams[0]);
                    dataStore.setCurrentValue(new Double(aParams[1]).doubleValue());
                    dataStore.setMinValue(new Double(aParams[2]).doubleValue());
                    dataStore.setMaxValue(new Double(aParams[3]).doubleValue());
                } else
                    debugMessage("ADD_GAUGE_DATA: Incorrect no. of parameters, need 4 - <Label>, <Current value>, <Min value>, <Max value>.");
            }

            /**
       * ADD_THRESHOLDS
       */
            else if (_ID == pAddThresholds) {
                String[] aParams = ((String)_object).split(sDelimiter);
                debugMessage("ADD_THRESHOLDS: Received " + aParams.length + " thresholds.");
                for (int i = 0; i < aParams.length; i++) {
                    dataStore.addThreshold(new Double(aParams[i]).doubleValue());
                }
            }

            /**
       * SET_THRESHOLD_COLORS
       */
            else if (_ID == pSetThresholdColors) {
                String[] aParams = ((String)_object).split(sDelimiter);
                debugMessage("SET_THRESHOLD_COLORS: Received " + aParams.length + " colors.");
                for (int i = 0; i < aParams.length; i++) {
                    dataStore.addThresholdColor(ColorCodeRegistry.getColorCode(aParams[i]));
                }
            }

            /**
       * SHOW_GAUGE
       */
            else if (_ID == pShowGauge) {
                debugMessage("SHOW_GAUGE: Label '" + dataStore.getLabel() + "'.");
                debugMessage("SHOW_GAUGE: Current value '" + dataStore.getCurrentValue() + "'.");
                debugMessage("SHOW_GAUGE: Min value '" + dataStore.getMinValue() + "'.");
                debugMessage("SHOW_GAUGE: Max value '" + dataStore.getMaxValue() + "'.");
                debugMessage("SHOW_GAUGE: Threshold count '" + dataStore.getThresholds().size() + "'.");
                boolean bSuccess = setDataAndShowGauge();
                debugMessage("SHOW_GAUGE: Gauge replied '" + bSuccess + "' when setting data.");
            }
            /**
		                       * =================== Setting gauge data (Method 2) ==================
		                       */

            /**
		                       * SET_DATA_COLUMNS
		                       */
            else if (_ID == pSetDataColumns) {
                debugMessage("SET_DATA_COLUMNS is not implemented.");
                /*String[] aParams = ((String)_object).split(sDelimiter);
		                        debugMessage("SET_DATA_COLUMNS: Received '" + aParams.length + "' parameters.");
		                        alDataRowLabels.clear();
		                        alDataRows.clear(); // We need to clear the data array when setting new columns
		                        ArrayList<Object> alSpec = new ArrayList<Object>();
		                        ArrayList<Object> alColLbls = new ArrayList<Object>();
		                        for (int i=0; i<aParams.length; i++) {
		                          if (aParams[i].equalsIgnoreCase("LABEL"))
		                            ; // Do nothing because this is a required column
		                          else if (aParams[i].equalsIgnoreCase("METRIC")) {
		                            alSpec.add(DataSpecification.METRIC);
		                            alColLbls.add("Value");
		                          }
		                          else if (aParams[i].equalsIgnoreCase("MINIMUM")) {
		                            alSpec.add(DataSpecification.MINIMUM);
		                            alColLbls.add("Min");
		                          }
		                          else if (aParams[i].equalsIgnoreCase("MAXIMUM")) {
		                            alSpec.add(DataSpecification.MAXIMUM);
		                            alColLbls.add("Max");
		                          }
		                          else if (aParams[i].equalsIgnoreCase("THRESHOLD")) {
		                            alSpec.add(DataSpecification.THRESHOLD);
		                            alColLbls.add("Threshold" + i);
		                          }
		                          else
		                            debugMessage("SET_DATA_COLUMNS: Could not understand column type '" + aParams[i] + "'!");
		            }
		            aDataColumns = alSpec.toArray();
		            aDataColumnLabels = alColLbls.toArray();*/
            }

            /**
		           * SET_DATA_COLUMN_LABELS
		           */
            else if (_ID == pSetColumnLabels) {
                debugMessage("SET_COLUMN_LABELS is not implemented.");
                /*String[] aParams = ((String)_object).split(sDelimiter);
		                        debugMessage("SET_COLUMN_LABELS: Received '" + aParams.length + "' parameters.");
		                        aDataColumnLabels = aParams;*/
            }

            /**
		           * ADD_DATA_ROW
		           */
            else if (_ID == pAddDataRow) {
                debugMessage("ADD_DATA_ROW is not implemented.");
                /*String[] aParams = ((String)_object).split(sDelimiter);
		                        debugMessage("ADD_DATA_ROW: Adding a row with '" + aParams.length + "' columns...");
		                        for (int i=0; i<aParams.length; i++)
		                        {
		                          if (i!=0) {
		                          ArrayList<Object> al = new ArrayList<Object>();
		                          al.add(aParams[i]);
		                          alDataRows.add(al);
		                          }
		                          else
		                             alDataRowLabels.add(aParams[i]);
		                        }*/
            }

            /**
		           * ADD_DATA_TO_GAUGE
		           */
            else if (_ID == pAddDataToGauge) {
                debugMessage("ADD_DATA_TO_GAUGE is not implemented.");
                /*debugMessage("ADD_DATA_TO_GAUGE: Adding data to gauge...");
		                        Object[][] a = new Object[alDataRows.size()][];
		                        for (int i=0; i<alDataRows.size(); i++) {
		                          a[i] = ((ArrayList)alDataRows.get(i)).toArray();
		            }
		                        m_gauge.setGridData(aDataColumns, aDataColumnLabels, alDataRowLabels.toArray(), a);
		                        m_gauge.setVisible(true);*/
            }


            /**
			 * SET_VALUE
			 */
            else if (_ID == pSetValue) {
                debugMessage("SET_VALUE - setting data for display...");
                dataStore.setCurrentValue(new Double((String)_object).doubleValue());
                boolean bSuccess = setDataAndShowGauge();
                debugMessage("SET_VALUE: Gauge replied '" + bSuccess + "' when setting data.");
                return true;
            }

            /**
		   * =================== Setting gauge properties ==================
		   */

            /**
			 * SET_GAUGE_TYPE
			 */
            else if (_ID == pSetGaugeType) {
                debugMessage("SET_GAUGE_TYPE - Setting gauge type to '" + (String)_object + "'.");
                if (((String)_object).equalsIgnoreCase("DIAL"))
                    m_gauge.setGaugeType(GaugeConstants.DIAL);
                else if (((String)_object).equalsIgnoreCase("STATUSMETER"))
                    m_gauge.setGaugeType(GaugeConstants.STATUSMETER);
                else if (((String)_object).equalsIgnoreCase("VERTICALSTATUSMETER"))
                    m_gauge.setGaugeType(GaugeConstants.VERTICALSTATUSMETER);
                else if (((String)_object).equalsIgnoreCase("LED"))
                    m_gauge.setGaugeType(GaugeConstants.LED);
                else
                    debugMessage("SET_GAUGE_TYPE - Failed to set gauge type '" + (String)_object + "'.");
                return true;
            }

            /**
			 * SET_INDICATOR_TYPE
			 */
            else if (_ID == pSetIndicatorType) {
                debugMessage("SET_INDICATOR_TYPE - Setting indicator type to '" + (String)_object + "'.");
                if (((String)_object).equalsIgnoreCase("LINE"))
                    m_gauge.getIndicator().setType(GaugeConstants.IT_LINE);
                else if (((String)_object).equalsIgnoreCase("NEEDLE"))
                    m_gauge.getIndicator().setType(GaugeConstants.IT_NEEDLE);
                else if (((String)_object).equalsIgnoreCase("FILL"))
                    m_gauge.getIndicator().setType(GaugeConstants.IT_FILL);
                else
                    debugMessage("SET_INDICATOR_TYPE - Failed to set indicator type '" + (String)_object + "'.");
                return true;
            }

            /**
			 * SET_METRIC_LABEL_POS
			 */
            else if (_ID == pSetMetricLabelPosition) {
                debugMessage("SET_METRIC_LABEL_POS - Setting metric label position to '" + (String)_object + "'.");
                if (((String)_object).equalsIgnoreCase("BELOW_GAUGE"))
                    m_gauge.getMetricLabel().setPosition(GaugeConstants.LP_BELOW_GAUGE);
                else if (((String)_object).equalsIgnoreCase("INSIDE_GAUGE"))
                    m_gauge.getMetricLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE);
                else if (((String)_object).equalsIgnoreCase("INSIDE_GAUGE_LEFT"))
                    m_gauge.getMetricLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE_LEFT);
                else if (((String)_object).equalsIgnoreCase("INSIDE_GAUGE_RIGHT"))
                    m_gauge.getMetricLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE_RIGHT);
                else if (((String)_object).equalsIgnoreCase("NONE"))
                    m_gauge.getMetricLabel().setPosition(GaugeConstants.LP_NONE);
                else if (((String)_object).equalsIgnoreCase("WITH_BOTTOM_LABEL"))
                    m_gauge.getMetricLabel().setPosition(GaugeConstants.LP_WITH_BOTTOM_LABEL);
                else
                    debugMessage("SET_METRIC_LABEL_POS - Failed to set metric label position to '" + (String)_object +
                                 "'.");
                return true;
            }

            /**
			 * SET_METRIC_LABEL_NUMBER_TYPE
			 */
            else if (_ID == pSetMetricLabelNumberType) {
                debugMessage("SET_METRIC_LABEL_NUMBER_TYPE - Setting metric label number type to '" + (String)_object +
                             "'.");
                if (((String)_object).equalsIgnoreCase("NUMBER"))
                    m_gauge.getMetricLabel().setNumberType(GaugeConstants.NT_NUMBER);
                else if (((String)_object).equalsIgnoreCase("PERCENT"))
                    m_gauge.getMetricLabel().setNumberType(GaugeConstants.NT_PERCENT);
                else
                    debugMessage("SET_METRIC_LABEL_NUMBER_TYPE - Failed to set metric label number type to '" +
                                 (String)_object + "'.");
                return true;
            }

            /**
			 * SET_DELIMITER
			 */
            else if (_ID == pSetDelimiter) {
                debugMessage("SET_DELIMITER: Trying to set delimiter value to '" + (String)_object + "'.");
                sDelimiter = _object == null ? sDelimiter : (String)_object;
                return true;
            }

            /**
       * SET_USE_THRESHOLD_FILL_COLOR
       */
            else if (_ID == pSetUseThresholdFillColor) {
                debugMessage("SET_USE_THRESHOLD_FILL_COLOR: " + ((String)_object).equalsIgnoreCase("TRUE"));
                m_gauge.getPlotArea().setUseThresholdFillColor(((String)_object).equalsIgnoreCase("TRUE"));
                return true;
            }

            /**
       * SET_BOTTOM_LABEL_POS
       */
            else if (_ID == pSetBottomLabelPosition) {
                debugMessage("SET_BOTTOM_LABEL_POS - Setting bottom label position to '" + (String)_object + "'.");
                if (((String)_object).equalsIgnoreCase("BELOW_GAUGE"))
                    m_gauge.getBottomLabel().setPosition(GaugeConstants.LP_BELOW_GAUGE);
                else if (((String)_object).equalsIgnoreCase("INSIDE_GAUGE"))
                    m_gauge.getBottomLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE);
                else if (((String)_object).equalsIgnoreCase("NONE"))
                    m_gauge.getBottomLabel().setPosition(GaugeConstants.LP_NONE);
                else
                    debugMessage("SET_BOTTOM_LABEL_POS - Failed to set bottom label position to '" + (String)_object +
                                 "'.");
                return true;
            }

            /**
       * SET_BOTTOM_LABEL_TEXT
       */
            else if (_ID == pSetBottomLabelText) {
                debugMessage("SET_BOTTOM_LABEL_TEXT: " + (String)_object);
                m_gauge.getBottomLabel().setText((String)_object);
                return true;
            }

            /**
       * SET_TOP_LABEL_POS
       */
            else if (_ID == pSetTopLabelPosition) {
                debugMessage("SET_TOP_LABEL_POS - Setting top label position to '" + (String)_object + "'.");
                if (((String)_object).equalsIgnoreCase("ABOVE_GAUGE"))
                    m_gauge.getTopLabel().setPosition(GaugeConstants.LP_ABOVE_GAUGE);
                else if (((String)_object).equalsIgnoreCase("INSIDE_GAUGE"))
                    m_gauge.getTopLabel().setPosition(GaugeConstants.LP_INSIDE_GAUGE);
                else if (((String)_object).equalsIgnoreCase("NONE"))
                    m_gauge.getTopLabel().setPosition(GaugeConstants.LP_NONE);
                else
                    debugMessage("SET_TOP_LABEL_POS - Failed to set top label position to '" + (String)_object + "'.");
                return true;
            }

            /**
       * SET_TOP_LABEL_TEXT
       */
            else if (_ID == pSetTopLabelText) {
                debugMessage("SET_TOP_LABEL_TEXT: " + (String)_object);
                m_gauge.getTopLabel().setText((String)_object);
                return true;
            }

            /**
			 * SET_THRESHOLDS_BORDER_COLOR
			 */
            else if (_ID == pSetThresholdsBorderColor) {
                debugMessage("SET_THRESHOLDS_BORDER_COLOR: Setting border color to '" + (String)_object + "' for '" +
                             dataStore.getThresholds().size() + "' thresholds...");
                for (int i = 0; i < dataStore.getThresholds().size(); i++) {
                    m_gauge.getThreshold().setBorderColor(i, ColorCodeRegistry.getColorCode((String)_object));
                }
                return true;
            }

            /**
       * SET_TICK_LABEL_CONTENT
       */
            else if (_ID == pSetTickLabelContent) {
                String[] aParams = ((String)_object).split(sDelimiter);
                debugMessage("SET_TICK_LABEL_CONTENT: " + (String)_object);
                int iContent = 0;
                for (int i = 0; i < aParams.length; i++) {
                    if (aParams[i].equalsIgnoreCase("NONE"))
                        iContent |= GaugeAttributes.TC_NONE;
                    if (aParams[i].equalsIgnoreCase("METRIC"))
                        iContent |= GaugeAttributes.TC_METRIC;
                    if (aParams[i].equalsIgnoreCase("MIN_MAX"))
                        iContent |= GaugeAttributes.TC_MIN_MAX;
                    if (aParams[i].equalsIgnoreCase("INCREMENTS"))
                        iContent |= GaugeAttributes.TC_INCREMENTS;
                    if (aParams[i].equalsIgnoreCase("THRESHOLD"))
                        iContent |= GaugeAttributes.TC_THRESHOLD;
                }
                if (iContent != 0)
                    m_gauge.getTickLabel().setContent(iContent);
                return true;
            }

            else {
                return super.setProperty(_ID, _object);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        } finally {
            // do something here
        }
        return true;
    }

    /**
     * Sets data to the gauge and displays it.
     * TODO: Support for multiple gauges
     */
    private boolean setDataAndShowGauge() {
        ArrayList<Object> alSpecs = new ArrayList<Object>(); // Will hold dataspecifications
        ArrayList<Object> alColumnLabels = new ArrayList<Object>(); // Will hold column labels
        alSpecs.add(DataSpecification.METRIC); // Add current value dataspecification
        alColumnLabels.add("Value"); // Add current value column label
        alSpecs.add(DataSpecification.MINIMUM); // Add minimum value dataspecification
        alColumnLabels.add("Min"); // Add minimum value column label
        alSpecs.add(DataSpecification.MAXIMUM); // Add maximum value dataspecification
        alColumnLabels.add("Max"); // Add maximum value column label

        // Add all column labels for specified thresholds
        for (int i = 0; i < dataStore.getThresholds().size(); i++) {
            alSpecs.add(DataSpecification.THRESHOLD);
            alColumnLabels.add("Threshold" + i + 1);
        }

        // Add gauge data values
        ArrayList<Object[]> alData = new ArrayList<Object[]>(); // Will hold all data values
        alData.add(new Object[] { dataStore.getCurrentValue() }); // Add current value
        alData.add(new Object[] { dataStore.getMinValue() }); // Add minimum value
        alData.add(new Object[] { dataStore.getMaxValue() }); // Add Maximum value

        // Add all specified threshold values
        for (int i = 0; i < dataStore.getThresholds().size(); i++) {
            alData.add(new Object[] { dataStore.getThresholds().get(i) });
        }

        // Convert data ArrayList to multi-dimensional Object array
        Object[][] a = new Object[alData.size()][];
        for (int i = 0; i < alData.size(); i++) {
            a[i] = alData.get(i);
        }

        Object[] aLabels = new Object[] { dataStore.getLabel() }; // Create the array holding the gauge label

        // Putting it all together and send the data to the gauge
        boolean bSuccess = m_gauge.setGridData(alSpecs.toArray(), alColumnLabels.toArray(), aLabels, a);
        m_gauge.setVisible(bSuccess); // Show the gauge if the data was set successfully

        // If the data was set successfully, set the specified threshold colors
        if (bSuccess) {
            for (int i = 0; i < dataStore.getThresholdColors().size(); i++) {
                m_gauge.getThreshold().setFillColor(i, (Color)dataStore.getThresholdColors().get(i));
            }
        }
        return bSuccess;
    }

    protected void dispatchMouseAction(String msg) {

        debugMessage("dispatchMouseAction(): " + msg);
        dispatchCustomEvent(pGaugeInfo, msg, eGaugeAction);
    }
}

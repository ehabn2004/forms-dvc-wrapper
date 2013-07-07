package com.bincsoft.forms.dvc;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.properties.formsgauge.FormsGaugeProperty;

import java.awt.Color;

import java.util.ArrayList;
import java.util.logging.Logger;

import oracle.dss.dataView.DataviewConstants;
import oracle.dss.dataView.ViewMouseEvent;
import oracle.dss.dataView.ViewMouseListener;
import oracle.dss.gauge.DataSpecification;
import oracle.dss.gauge.Gauge;
import oracle.dss.gauge.GaugeConstants;

import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;


/**
 * Wrapper class for oracle.dss.gauge.Gauge which enables gauges in Oracle Forms.
 */
public class FormsGauge extends BincsoftBean {
    private Logger log = Logger.getLogger(getClass().getName());
    private Gauge gauge = null;
    private GaugeDataStore dataStore = null;
    private String sDelimiter = ",";
    private ViewMouseListener mouseListener = null;

    protected static final ID GAUGE_ACTION = ID.registerProperty("GAUGE_ACTION");
    protected static final ID GAUGE_INFO = ID.registerProperty("GAUGE_INFO");

    public FormsGauge() throws ClassNotFoundException {
        super();

        gauge = new Gauge();
        dataStore = new GaugeDataStore();

        // Set initial gauge type
        gauge.setGaugeType(GaugeConstants.DIAL);

        // Set white background
        gauge.getGaugeBackground().getSFX().setFillType(DataviewConstants.FT_COLOR);

        // Add a mouse listener
        mouseListener = new ViewMouseListener() {
                public void mouseClicked(ViewMouseEvent e) {
                    log("mouseClicked()");
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

        gauge.addViewMouseListener(mouseListener);
        
        // Register all property handlers
        registerProperties(FormsGaugeProperty.values());

        // The gauge is shown when the data is set
        gauge.setVisible(false);
    }

    @Override
    public void init(IHandler handler) {
        super.init(handler);
        super.add(gauge);
        log.info(getClass().getName() + " version '" + getVersion() + "' started, " +
                           gauge.getClass().getName() + " version '" + gauge.getVersion() + "'.");
    }

    @Override
    public boolean setProperty(ID id, Object object) {
        return super.setProperty(id, object);
    }

    /**
     * Sets data to the gauge and displays it.
     * TODO: Support for multiple gauges
     */
    public boolean setDataAndShowGauge() {
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
        boolean bSuccess = gauge.setGridData(alSpecs.toArray(), alColumnLabels.toArray(), aLabels, a);
        gauge.setVisible(bSuccess); // Show the gauge if the data was set successfully

        // If the data was set successfully, set the specified threshold colors
        if (bSuccess) {
            for (int i = 0; i < dataStore.getThresholdColors().size(); i++) {
                gauge.getThreshold().setFillColor(i, (Color)dataStore.getThresholdColors().get(i));
            }
        }
        return bSuccess;
    }

    protected void dispatchMouseAction(String msg) {
        log("dispatchMouseAction(): " + msg);
        dispatchCustomEvent(GAUGE_INFO, msg, GAUGE_ACTION);
    }
    
    public String getDelimiter() {
        return sDelimiter;
    }
    
    public Gauge getGauge() {
        return gauge;
    }
    
    @Override
    public Object getWrappedObject() {
        return getGauge();
    }
    
    public GaugeDataStore getDataStore() {
        return dataStore;
    }
    
    private void addTestData() {
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
        //gauge.setTabularData(specs, dataRows);

        /*================= End Test Data =================*/
    }
}

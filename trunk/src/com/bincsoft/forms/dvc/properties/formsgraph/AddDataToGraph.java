package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.Utils;
import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.image.BufferedImage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import oracle.forms.ui.ExtendedFrame;


public class AddDataToGraph extends FormsGraphPropertyHandler implements PropertyChangeListener {
    private ExtendedFrame frmOwnerFrame = null;
    private FormsGraph formsGraph = null;
    private boolean bHasFocus = false;

    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        this.formsGraph = graph;
        log("ADD_DATA_TO_GRAPH: setting data for display");
        ArrayList al = graph.getLocalRelationalData().getRelationalData();
        log("ADD_DATA_TO_GRAPH: Size of arraylist: " + al.size());
        // handle no data passed
        if (al.size() == 0) {
            log("ADD_DATA_TO_GRAPH: decide on the response in the UI");
            graph.noGraphDataFoundHandler();
        } else {
            // if the graph component previously have been removed from
            // the Graph then it needs to be added back there
            if (graph.isRecoverGraphToVBean()) {
                log("ADD_DATA_TO_GRAPH: remove component that was added instead of the graph");
                graph.removeGraph();
                log("ADD_DATA_TO_GRAPH: add graph for display");
                graph.addGraph();
                log("ADD_DATA_TO_GRAPH: reset flag");
                graph.setRecoverGraphToVBean(false);
            }
            graph.getGraph().setVisible(true);
            graph.getGraph().setTabularData(al);
            // m_graph.setLocalRelationalData(al);

            if (!graph.isFocusListenerAdded()) {
                // Add a keyboardfocuslistener to fix heavy/lightweight problem
                frmOwnerFrame = Utils.getInstance().getOwnerWindow(graph);
                KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                focusManager.addPropertyChangeListener(this);
                graph.setFocusListenerAdded(true);
            }
        }
        log("ADD_DATA_TO_GRAPH: finished");
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        //glassPaneSolution(e);
        screenshotSolution(e);
    }

    private void glassPaneSolution(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("permanentFocusOwner") && e.getNewValue() != null &&
            e.getNewValue() instanceof Component) {
            log(String.format("%s received/lost focus", frmOwnerFrame.getTitle()));
            boolean bFocused = Utils.getInstance().isChildFocusOwner(frmOwnerFrame, (Component)e.getNewValue());
            formsGraph.getGlassPanel().setVisible(!bFocused);
        } else {
            log(e.getPropertyName());
        }
    }

    private void screenshotSolution(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("permanentFocusOwner") && e.getNewValue() != null &&
            e.getNewValue() instanceof Component && frmOwnerFrame != null) {
            boolean bGainedFocus = Utils.getInstance().isChildFocusOwner(frmOwnerFrame, (Component)e.getNewValue());
            if (bGainedFocus) {
                if (!bHasFocus) {
                    formsGraph.removeGraphScreenshot();
                    bHasFocus = true;
                }
            } else {
                if (bHasFocus && formsGraph.getInternalFrame() != null &&
                    formsGraph.getInternalFrame().getHeight() != 0 && formsGraph.getInternalFrame().getWidth() != 0) {
                    try {
                        BufferedImage image = new BufferedImage(formsGraph.getInternalFrame().getWidth(), formsGraph.getInternalFrame().getHeight(), BufferedImage.TYPE_INT_RGB);
                        formsGraph.getInternalFrame().paint(image.createGraphics());
                        formsGraph.setGraphScreenshot(image);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    bHasFocus = false;
                }
            }
        }
    }
}

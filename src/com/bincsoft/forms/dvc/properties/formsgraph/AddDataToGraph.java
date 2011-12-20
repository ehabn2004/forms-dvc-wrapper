package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;

import java.awt.Component;
import java.awt.KeyboardFocusManager;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import oracle.forms.ui.ExtendedFrame;

public class AddDataToGraph implements IFormsGraphProperty, PropertyChangeListener {
    private ExtendedFrame frmOwnerFrame = null;
    private FormsGraph graph = null;

    public AddDataToGraph() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        this.graph = graph;
        graph.debugMessage("ADD_DATA_TO_GRAPH: setting data for display");
        ArrayList al = graph.getLocalRelationalData().getRelationalData();
        graph.debugMessage("ADD_DATA_TO_GRAPH: Size of arraylist: " +
                           al.size());
        // handle no data passed
        if (al.size() == 0) {
            graph.debugMessage("ADD_DATA_TO_GRAPH: decide on the response in the UI");
            graph.noGraphDataFoundHandler();
        } else {
            // if the graph component previously have been removed from
            // the Graph then it needs to be added back there
            if (graph.isRecoverGraphToVBean()) {
                graph.debugMessage("ADD_DATA_TO_GRAPH: remove component that was added insetad of the graph");
                graph.removeGraph();
                graph.debugMessage("ADD_DATA_TO_GRAPH: add graph for display");
                graph.addGraph();
                graph.debugMessage("ADD_DATA_TO_GRAPH: reset flag");
                graph.setRecoverGraphToVBean(false);
            }
            graph.getGraph().setVisible(true);
            graph.getGraph().setTabularData(al);
            // m_graph.setLocalRelationalData(al);

            if (!graph.isFocusListenerAdded()) {
                // Add a keyboardfocuslistener to fix heavy/lightweight problem
                frmOwnerFrame = graph.getOwnerWindow(graph);
                KeyboardFocusManager focusManager =
                    KeyboardFocusManager.getCurrentKeyboardFocusManager();
                focusManager.addPropertyChangeListener(this);
                graph.setFocusListenerAdded(true);
            }
        }
        graph.debugMessage("ADD_DATA_TO_GRAPH: finished");
        return true;
    }

    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("focusOwner") &&
            e.getNewValue() != null && e.getNewValue() instanceof Component) {
            graph.getGlassPanel().setVisible(!graph.isChildFocusOwner(frmOwnerFrame,
                                                                      (Component)e.getNewValue()));
        }
    }
}

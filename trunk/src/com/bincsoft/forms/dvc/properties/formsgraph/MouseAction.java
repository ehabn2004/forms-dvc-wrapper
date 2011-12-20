package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;
import com.bincsoft.forms.dvc.GraphViewMouseListener;

public class MouseAction implements IFormsGraphProperty {
    public MouseAction() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        if ("TRUE".equalsIgnoreCase(sParams)) {
            /*
      * only one instance of the mouse view listsner is allowed
      * for this object. If there exists an instance of this
      * listener type, then all other registration attempts are
      * ignored
      */

            if (graph.getViewListenerCount() == 0) {
                graph.setViewMouseListener(new GraphViewMouseListener(graph.getGraph(),
                                                                  graph,
                                                                  graph.getLocalRelationalData()));
                graph.debugMessage("MOUSEACTION: Adding mouse listener");
                graph.getGraph().addViewMouseListener(graph.getViewMouseListener());
                int iCount = graph.getViewListenerCount();
                graph.setViewListenerCount(++iCount);
            }
        } else if ("FALSE".equalsIgnoreCase(sParams)) {
            if (graph.getViewListenerCount() != 0) {
                graph.debugMessage("MOUSEACTION: Removing mouse listener");
                graph.getGraph().removeViewMouseListener(graph.getViewMouseListener());
                graph.setViewListenerCount(0);
            }
        } else {
            // ignore
            graph.debugMessage("Property MOUSEACTION: " + sParams +
                               " passed but TRUE or FALSE required. Ignoring command!");
        }
        return true;
    }
}

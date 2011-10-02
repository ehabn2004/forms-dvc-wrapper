package oracle.forms.demos.bigraph.impl;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.demos.bigraph.GraphViewMouseListener;
import oracle.forms.properties.ID;

public class MouseAction implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("MOUSEACTION");

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
                graph.DebugMessage("MOUSEACTION: Adding mouse listener");
                graph.getGraph().addViewMouseListener(graph.getViewMouseListener());
                int iCount = graph.getViewListenerCount();
                graph.setViewListenerCount(++iCount);
            }
        } else if ("FALSE".equalsIgnoreCase(sParams)) {
            if (graph.getViewListenerCount() != 0) {
                graph.DebugMessage("MOUSEACTION: Removing mouse listener");
                graph.getGraph().removeViewMouseListener(graph.getViewMouseListener());
                graph.setViewListenerCount(0);
            }
        } else {
            // ignore
            graph.DebugMessage("Property MOUSEACTION: " + sParams +
                               " passed but TRUE or FALSE required. Ignoring command!");
        }
        return true;
    }
}

package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.GraphViewMouseListener;

public class MouseAction extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if ("TRUE".equalsIgnoreCase(sParams)) {
            /*
            * only one instance of the mouse view listsner is allowed
            * for this object. If there exists an instance of this
            * listener type, then all other registration attempts are
            * ignored
            */
            if (graph.getViewListenerCount() == 0) {
                graph.setViewMouseListener(new GraphViewMouseListener(graph));
                log("MOUSEACTION: Adding mouse listener");
                graph.getGraph().addViewMouseListener(graph.getViewMouseListener());
                int iCount = graph.getViewListenerCount();
                graph.setViewListenerCount(++iCount);
            }
        } else if ("FALSE".equalsIgnoreCase(sParams)) {
            if (graph.getViewListenerCount() != 0) {
                log("MOUSEACTION: Removing mouse listener");
                graph.getGraph().removeViewMouseListener(graph.getViewMouseListener());
                graph.setViewListenerCount(0);
            }
        } else {
            // ignore
            log("Property MOUSEACTION: " + sParams + " passed but TRUE or FALSE required. Ignoring command!");
        }
        return true;
    }
}

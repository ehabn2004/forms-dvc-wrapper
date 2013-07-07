package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;

public class AddGraphToBean extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("GRAPH: adding to container");
        if (graph.getSeparateFrame() != null) {
            graph.getSeparateFrame().setVisible(false);
            graph.getSeparateFrame().getContentPane().remove(0);
            graph.getOwnerFrame().add(graph);
            graph.validate();
            graph.setSeparateFrame(null);
        }
        return true;
    }
}

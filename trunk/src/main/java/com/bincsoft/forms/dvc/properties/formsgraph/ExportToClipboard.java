package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.FormsGraphTransferable;

import java.awt.Toolkit;

public class ExportToClipboard extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("EXPORT_CLIPBOARD: Exporting...");
        log("EXPORT_CLIPBOARD: m_graph.getParent() h=" +
                           graph.getGraph().getParent().getSize().getHeight() + " w=" +
                           graph.getGraph().getParent().getSize().getWidth());
        graph.getGraph().setImageSize(graph.getGraph().getParent().getSize());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new FormsGraphTransferable(graph), null);
        log("EXPORT_CLIPBOARD: m_graph h=" + graph.getGraph().getImageSize().getHeight() + " w=" +
                           graph.getGraph().getImageSize().getWidth());
        log("EXPORT_CLIPBOARD: Done!");
        return true;
    }
}

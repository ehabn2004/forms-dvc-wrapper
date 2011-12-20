package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;

import com.bincsoft.forms.dvc.FormsGraphTransferable;

import java.awt.Toolkit;

public class ExportToClipboard implements IFormsGraphProperty {
    public ExportToClipboard() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
        graph.debugMessage("EXPORT_CLIPBOARD: Exporting...");
        graph.debugMessage("EXPORT_CLIPBOARD: m_graph.getParent() h=" +
                           graph.getGraph().getParent().getSize().getHeight() + " w=" +
                           graph.getGraph().getParent().getSize().getWidth());
        graph.getGraph().setImageSize(graph.getGraph().getParent().getSize());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new FormsGraphTransferable(graph), null);
        graph.debugMessage("EXPORT_CLIPBOARD: m_graph h=" + graph.getGraph().getImageSize().getHeight() + " w=" +
                           graph.getGraph().getImageSize().getWidth());
        graph.debugMessage("EXPORT_CLIPBOARD: Done!");
        return true;
    }
}

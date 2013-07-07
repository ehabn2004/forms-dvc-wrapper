package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;
import com.bincsoft.forms.BincsoftBean;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class GraphInFrame extends FormsGraphPropertyHandler {
    private String _windowTitle = "Forms - BI Graph"; // title of separate frame
    private FormsGraph _graph = null;
    
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        _graph = graph;
        log("GRAPH_IN_FRAME: adding to frame");
        int iWidth = 0;
        int iHeight = 0;
        if (sParams != null && !sParams.equals("")) {
            String saParams[] = sParams.split("\\|");
            if (saParams.length > 0)
                _windowTitle = saParams[0];
            if (saParams.length > 1) {
                try {
                    String saWndSize[] = saParams[1].split("x");
                    iWidth = Integer.parseInt(saWndSize[0]);
                    iHeight = Integer.parseInt(saWndSize[1]);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        graph.setSeparateFrame(new JFrame(_windowTitle));
        graph.getSeparateFrame().getContentPane().add(graph.getGraph());
        if (iWidth != 0 && iHeight != 0)
            graph.getSeparateFrame().setSize(iWidth, iHeight);
        else
            graph.getSeparateFrame().setSize(1024, 768);
        
        graph.getSeparateFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (_graph.getSeparateFrame() != null) {
                    _graph.getSeparateFrame().setVisible(false);
                    _graph.getSeparateFrame().getContentPane().remove(0);
                    _graph.getOwnerFrame().add(_graph.getGraph());
                    _graph.validate();
                    _graph.setSeparateFrame(null);
                }
            }
        });
        graph.getSeparateFrame().setVisible(true);
        return true;
    }
}

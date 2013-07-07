package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;
import com.bincsoft.forms.dvc.FormsGraph;

import com.bincsoft.forms.dvc.GraphText;

import java.io.InputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.dss.graph.Graph;
import oracle.dss.graph.GraphConstants;
import oracle.dss.util.CustomStyle;
import oracle.dss.util.format.BaseViewFormat;


public class ResetGraph extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        super.handleProperty(sParams, bean);
        log("RESET_GRAPH");
        //m_graph.resetToDefault(GraphConstants.RESET_EVERYTHING);
        graph.getGraph().resetToDefault(GraphConstants.RESET_XML_PROPERTIES);
        setDefaults(graph);
        //m_graph.addViewMouseListener(instanceVMListener);
        //m_graph.addViewMouseMotionListener(mouseMotionListener);
        return true;
    }
    
    /**
     * Sets default values on graph including style
     */
    public static void setDefaults(FormsGraph graph) {
        setDefaultStyle(graph);
        // show rows as in the relational world
        graph.getGraph().setDataRowShownAsASeries(false);
        // show Y1 value in full length
        graph.getGraph().getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
        // have the Bean managing the layout of the Graph
        graph.getGraph().setAutoLayout(Graph.AL_ALWAYS);
        // set default font
        graph.getGraph().setFontName(GraphText.DEFAULT_FONT.getFontName());
        // the graph is first shown when data is provided
        graph.getGraph().setVisible(false);
    }
    
    /**
     * Methods for finding and setting XML styles
     */
    public static void setDefaultStyle(FormsGraph graph) {
        Logger log = Logger.getLogger(ResetGraph.class.getName());
        log.log(Level.FINE, "setDefaultStyle()");
        InputStream is = graph.getGraph().getClass().getResourceAsStream(graph.getDefaultXmlStyle());
        try {
            //System.out.println(convertStreamToString(is));
            CustomStyle style = new CustomStyle(DvcHelper.convertStreamToString(is));
            is.close();
            graph.getGraph().setStyle(style);
        } catch (Exception ex) {
            log.log(Level.FINE, "setDefaultStyle(): " + ex);
        }
    }
}

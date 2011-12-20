package com.bincsoft.forms.dvc;

import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.properties.formsgraph.IFormsGraphProperty;
import com.bincsoft.forms.dvc.properties.formsgraph.ResetGraph;
import com.bincsoft.forms.dvc.properties.formsgraph.ReturnValues;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import oracle.dss.dataView.ViewMouseMotionListener;
import oracle.dss.graph.Graph;
import oracle.dss.util.DefaultErrorHandler;

import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;
import oracle.forms.ui.ExtendedFrame;
import oracle.forms.ui.VBean;


public class FormsGraph extends BincsoftBean {
    private Graph m_graph = null; // Instance of the DVC graph
    private String sDelimiter = ","; // default delimiter for the data passed to the graph
    private int mGraphType = Graph.BAR_VERT_CLUST; // default graph type is a simple vertical bar graph
    private int mViewListenerCount = 0; // only one view listener shall get implemented
    private int mSeparateFrameXPos = 10; // xPos of the separate Graph frame
    private int mSeparateFrameYPos = 10; // yPos of the separate Graph frame
    private boolean recoverGraphToVBean = false; // this flag is set when the Graph component in the Forms Bean component is replaced by a panel due to no data available in the graph
    private boolean showGraphAsSeries = false;    
    private String sNoDataFoundMessage = "Graph Contains No Data!"; // message shown when graph does not contain data
    private int returnValueSelection = ReturnValues.NO_DATA;
    private GraphViewMouseListener instanceVMListener = null; // reacts to mouse events performed on a graph
    private ViewMouseMotionListener mouseMotionListener = null; // Used for 3D manipulation using the mouse
    private LocalRelationalData lrd = null;
    private JTextPane pnlNoDataFound = new JTextPane();
    private JFrame jf = null;
    private JPanel glass = null;
    private boolean bAddedFocusListener = false;
    
    public final String DEFAULT_XML_STYLE = "/com/bincsoft/forms/dvc/style_default.xml";

    /**
     * Custom item events
     */
    protected static final ID pDelimiterInfo = ID.registerProperty("DELIMITER_INFO"); // Used when dispatching delimiter
    protected static final ID pGraphInfo = ID.registerProperty("GRAPH_INFO"); // Used when dispatching     
    protected static final ID pGetDelimiter = ID.registerProperty("GET_DELIMITER");
    protected static final ID pSeriesCount = ID.registerProperty("COLUMNCOUNT");
    protected static final ID pTest = ID.registerProperty("TEST");
    protected static final ID eGetSeriesCount = ID.registerProperty("RETURNED_COLUMN_NUMBER");
    protected static final ID eGraphAction = ID.registerProperty("GRAPH_ACTION");
    protected static final ID eGetDelimiter = ID.registerProperty("CURRENT_DELIMITER");
    
    private HashMap hmPropertyHandlers = new HashMap();
    
    public FormsGraph() {
        super();
        
        // Instantiate the BI Bean Graph
        m_graph = new Graph();

        // Enable 3D manipulation using the mouse
        /*mouseMotionListener = new ViewMouseMotionListener() {
                public void mouseMoved(ViewMouseEvent evt) {
                }

                public void mouseDragged(ViewMouseEvent evt) {
                    m_graph.setDepthRadius(15);
                    m_graph.setDepthAngle(evt.getX());
                    m_graph.setPieDepth(evt.getY());
                    m_graph.setPieRotation(evt.getX());
                    //System.out.println(evt.getX());
                }
            };

        m_graph.addViewMouseMotionListener(mouseMotionListener);*/

        ResetGraph.setDefaults(this); // Set default values

        // disable debug information sent from the graph
        DefaultErrorHandler deh = new DefaultErrorHandler();
        deh.setDebugMode(DefaultErrorHandler.SHOW_NONE);
        m_graph.addErrorHandler(deh);

        // instantiate the data store for this graph
        lrd = new LocalRelationalData(this);

        /*
         * Test Data allowing you to run this Java Bean in Forms without
         * providing Data Forms. use this data for developing new features and
         * functionality or for tracing problems
         */

        /*lrd.addRelationalDataRow("Europe,Sales,2000,1",sDelimiter);
         lrd.addRelationalDataRow("Europe,Provisions,2000,2",sDelimiter);
         lrd.addRelationalDataRow("USA,Sales,4000,3",sDelimiter);
         lrd.addRelationalDataRow("USA,Provisions,5000,4",sDelimiter);
         lrd.addRelationalDataRow("Europe,Netto,2200.12,5",sDelimiter);
         lrd.addRelationalDataRow("USA,Netto,30000,6",sDelimiter);
         lrd.addRelationalDataRow("USA,Brutto,7000,7",sDelimiter);
         lrd.addRelationalDataRow("Europe,Brutto,5000,8",sDelimiter);
                
         m_graph.setTabularData(lrd.getRelationalData());
         m_graph.setVisible(true);*/

        /* Test Data End */

        // ************ set mouse interaction to on **********
        instanceVMListener = new GraphViewMouseListener(m_graph, this, lrd);
        mViewListenerCount = 1;
        // ***************************************************

        m_graph.addViewMouseListener(instanceVMListener);
        
        // Register all property handlers
        registerPropertyHandlers();
    }
    
    private String getVersion() {
        return "1.0.3";
    }
    
    /**
     * Will try to load the specified handler class so exceptions occur at startup rather than at runtime
     * @param sProperty
     * @param sHandler
     * @throws ClassNotFoundException
     */
    private void registerPropertyHandler(String sProperty, String sHandler) {
        ClassLoader cl = FormsGraph.class.getClassLoader();
        try {
            cl.loadClass(IFormsGraphProperty.class.getPackage().getName() + "." + sHandler);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        hmPropertyHandlers.put(sProperty, sHandler);
        debugMessage(String.format("Registering handler '%s' for property '%s'", sHandler, sProperty));
        ID.registerProperty(sProperty);
    }
    
    private void registerPropertyHandlers() {
        registerPropertyHandler("DISPLAY_INDEX_LINE", "Deprecated");
        registerPropertyHandler("SET_DEBUG_PREFIX", "Deprecated");
        registerPropertyHandler("SET_FRAME_HEIGHT_WIDTH", "Deprecated");
        registerPropertyHandler("SHOW_FRAME", "Deprecated"); // Use GRAPH_IN_FRAME
        
        registerPropertyHandler("ADD_DATA_TO_GRAPH", "AddDataToGraph");
        registerPropertyHandler("ADD_INDEX_LINE", "AddIndexLine");
        registerPropertyHandler("ADD_REFERENCE_OBJECT", "AddReferenceObject");
        registerPropertyHandler("ADD_ROWDATA", "AddRowData");
        registerPropertyHandler("ALIGN_TITLE_TEXT", "AlignTitleText");
        registerPropertyHandler("ENABLE_TOOLTIPS", "EnableTooltips");
        registerPropertyHandler("EXPLODE_PIESLICE", "ExplodePieSlice");
        registerPropertyHandler("HIDE_AXIS", "HideAxis");
        registerPropertyHandler("MAX_SCALE_Y_AXIS", "MaxScaleYAxis");
        registerPropertyHandler("MIN_SCALE_Y_AXIS", "MinScaleYAxis");
        registerPropertyHandler("MAX_SCALE_Y2_AXIS", "MaxScaleY2Axis");
        registerPropertyHandler("MIN_SCALE_Y2_AXIS", "MinScaleY2Axis");
        registerPropertyHandler("MOUSE_ACTION", "MouseAction");
        registerPropertyHandler("POSITION_LEGEND", "PositionLegendArea");
        registerPropertyHandler("ROTATE_X_LABEL", "RotateXLabel");
        registerPropertyHandler("SCROLLBAR", "ScrollBar");
        registerPropertyHandler("SET_SCALED_LOGARITHMIC", "ScaledLogarithmic");
        registerPropertyHandler("SET_SERIES_MARKER_TYPE", "SeriesMarkerType");
        registerPropertyHandler("SET_SERIES_Y_AXIS", "SeriesYAxis");
        registerPropertyHandler("SHOW_LABELS", "ShowLabels");
        registerPropertyHandler("CLEAR_GRAPH", "ClearGraph");
        registerPropertyHandler("ENABLE_MARKERTEXT", "EnableMarkerText");
        registerPropertyHandler("ENABLE_GRADIENT", "EnableGradient");
        registerPropertyHandler("EXPORT_CLIPBOARD", "ExportToClipboard");
        registerPropertyHandler("FRAME_POS", "FramePos");
        registerPropertyHandler("SHOW_GRAPH_IN_FRAME", "GraphInFrame");
        registerPropertyHandler("SHOW_GRAPH", "AddGraphToBean");
        registerPropertyHandler("HIDE_FRAME", "HideFrame");
        registerPropertyHandler("GRAPHTYPE", "GraphType");
        registerPropertyHandler("HIDE_GRAPH", "HideGraph");
        registerPropertyHandler("MODIFY_ROW_DATA", "ModifyData");
        registerPropertyHandler("REMOVE_DATA", "RemoveData");
        registerPropertyHandler("HIDE_TITLE", "RemoveTitle");
        registerPropertyHandler("HIDE_SUBTITLE", "RemoveSubTitle");
        registerPropertyHandler("HIDE_FOOTER", "RemoveFooter");
        registerPropertyHandler("HIDE_X_TITLE", "RemoveXTitle");
        registerPropertyHandler("HIDE_Y_TITLE", "RemoveYTitle");
        registerPropertyHandler("RETURN_VALUES_ON_CLICK", "ReturnValues");
        registerPropertyHandler("RESET_GRAPH", "ResetGraph");
        registerPropertyHandler("SET_BACKGROUND", "BackgroundColor");
        registerPropertyHandler("SET_DELIMITER", "Delimiter");
        registerPropertyHandler("SET_LINEGRAPH_MARKERS", "LineGraphMarkers");
        registerPropertyHandler("SET_GRAPH_BASELINE", "BaseLine");
        registerPropertyHandler("SET_GRAPHIC_ANTIALIASING", "Antialiasing");
        registerPropertyHandler("SET_DEPTH", "Depth");
        registerPropertyHandler("SET_TITLE", "Title");
        registerPropertyHandler("SET_NO_DATA_FOUND", "NoDataFoundMessage");
        registerPropertyHandler("SET_LEGEND_BORDER", "LegendBorder");
        registerPropertyHandler("SET_SUBTITLE", "Subtitle");
        registerPropertyHandler("SET_FOOTER", "Footnote");
        registerPropertyHandler("SET_PLOT_AREA_COLOR", "PlotAreaColor");
        registerPropertyHandler("SET_TITLE_BACKGROUND", "TitleBackgroundColor");
        registerPropertyHandler("SET_SUBTITLE_BACKGROUND", "SubtitleBackgroundColor");
        registerPropertyHandler("SET_FOOTER_BACKGROUND", "FooterBackgroundColor");
        registerPropertyHandler("SET_SERIES_WIDTH", "SeriesWidth");
        registerPropertyHandler("SET_SERIES_COLOR", "SeriesColor");
        registerPropertyHandler("SET_X_LABEL", "XLabel");
        registerPropertyHandler("SET_X_VALUE_FONT", "XValueFont");
        registerPropertyHandler("SET_Y_LABEL", "YLabel");
        registerPropertyHandler("SET_Y2_LABEL", "Y2Label");
        registerPropertyHandler("SET_Y1_NUMBER_TYPE", "Y1NumType");
        registerPropertyHandler("SET_Y1_SCALE_DOWN", "Y1ScaleDown");
        registerPropertyHandler("SET_Y_DECIMAL_DIGITS", "YDecimalDigits");
        registerPropertyHandler("SET_Y2_DECIMAL_DIGITS", "Y2DecimalDigits");
        registerPropertyHandler("SET_STYLE", "Style");
        registerPropertyHandler("SET_XML_STYLE", "XmlStyle");
        registerPropertyHandler("SHOW_PIE_LABELS", "ShowPieLabels");
        registerPropertyHandler("SHOW_GRID", "ShowGrid");
        registerPropertyHandler("COLUMNCOUNT", "SeriesCount");
        registerPropertyHandler("SHOW_COLUMNS_AS_ROWS", "ShowColumnsAsRows");
        registerPropertyHandler("SHOW_LEGEND", "ShowLegendArea");
        
        debugMessage(String.format("Registered %s property handlers", hmPropertyHandlers.size()));
    }

    /**
     * Implementation of IView interface. Init is called when the bean is first
     * instantiated
     *
     * @see oracle.forms.ui.IView
     */
    public void init(IHandler handler) {
        super.init(handler);

        // Create the glasspane which will stop mouse events
        glass = new JPanel();
        glass.setOpaque(false);
        glass.addMouseListener(new MouseAdapter() {
        });
        glass.addMouseMotionListener(new MouseMotionAdapter() {
        });
        glass.addKeyListener(new KeyAdapter() {
        });
        glass.setFocusCycleRoot(true);

        // Create the internal frame which will house the graph and the glasspane
        JInternalFrame internalFrame = new JInternalFrame();
        BasicInternalFrameUI internalFrameUI = (BasicInternalFrameUI)internalFrame.getUI();
        internalFrameUI.setNorthPane(null);
        internalFrame.setBorder(BorderFactory.createEmptyBorder());
        internalFrame.setVisible(true);
        internalFrame.add(m_graph);
        internalFrame.setGlassPane(glass);
        super.add(internalFrame);

        System.out.println(String.format("%s version '%s' started, %s version '%s'.", getClass().getName(), getVersion(), m_graph.getClass().getName(), m_graph.getVersion()));
    }

    /**
     * Implementation of IView interface which sets a requested property to a given value
     *
     * @param _ID  property to be set.
     * @param _object value of the property id.
     * @return true if the property could be set, false otherwise.
     * @see oracle.forms.ui.IView
     */
    @Override
    public boolean setProperty(ID _ID, Object _object) {
        String sParams = _object == null ? "" : _object.toString();

        if (hmPropertyHandlers.containsKey(_ID.toString())) {
            String sHandler = IFormsGraphProperty.class.getPackage().getName() + "." + (String)hmPropertyHandlers.get(_ID.toString());
            debugMessage(String.format("Found handler %s for property %s", sHandler, _ID.toString()));
            ClassLoader cl = FormsGraph.class.getClassLoader();
            try {
                Class cls = cl.loadClass(sHandler);
                IFormsGraphProperty prop = (IFormsGraphProperty)cls.newInstance();
                return prop.handleProperty(sParams, this);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        
        if (_ID == pGetDelimiter) {
            return getDelimiter(sParams);
        }

        else if (_ID == pTest) {
            return test(sParams);
        }

        return super.setProperty(_ID, _object);
    }
    
    /**
     * Handles get actions from Forms
     * @param _ID
     * @return
     */
    @Override
    public Object getProperty(ID _ID) {
        if (_ID == pGetDelimiter) {
            return getDelimiter();
        }
        else if (_ID == pSeriesCount) {
            try {
                // retrieve graph column count for further usage
                debugMessage("SERIES COUNT requested");
                // if rows shown as series then row number = column number
                if (showGraphAsSeries) {
                    return new Integer(m_graph.getRowCount());
                } else {
                    return new Integer(m_graph.getColumnCount());
                }
            } catch (Exception e) {
                debugMessage("====SERIES COUNT Error====");
                debugMessage(e.getMessage());
                debugMessage("==========================");
                debugMessage("returning 0");
                // show 0 if no column can be retrieved
                return new Integer(0);
            }
        } else {
            return super.getProperty(_ID);
        }
    }    

    /**
     * Forms property: GET_DELIMITER
     * @param sParams
     * @return
     */
    private boolean getDelimiter(String sParams) {
        // raise custom event to pass the current delimiter to Forms
        dispatchDelimiter();
        return true;
    }

    /**
     * Used for testing.
     * Forms property: TEST
     * @param sParams
     * @return
     */
    private boolean test(String sParams) {
        debugMessage("TEST");
        // Do some tests here
        debugMessage("TEST: Done!");
        return true;
    }

    /**
     * Retrieves the int value contained in the attribute
     * passed with set_custom_property(), defining the Graph type. If no integer
     * value is found, then the existing value stored in mGraphType is returned.
     * @param type
     * @return
     */
    public int getInternalGraphType(Object type) {
        int iGraphType = 9999999;
        try {
            // call graphTypeAnalyzer.analyze() to determine the int
            // representation of the graph.
            // 0 is returned if no matching BI Graph is registerd for Forms
            iGraphType = GraphTypeRegistry.getTypeForString((String)type);

            debugMessage("getInternalGraphType(): type returned for " + type + " = " + iGraphType);

            if (iGraphType == 9999999) {
                iGraphType = mGraphType;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // set default
            iGraphType = mGraphType;
        } finally {}
        return iGraphType;
    }

    public void dispatchMouseAction(String msg) {
        dispatchCustomEvent(pGraphInfo, msg, eGraphAction);
    }

    private void dispatchDelimiter() {
        dispatchCustomEvent(pDelimiterInfo, getDelimiter(), eGetDelimiter);
    }

    public void returnSeriesCount(int ret) {
        dispatchCustomEvent(pGraphInfo, Integer.toString(ret), eGetSeriesCount);
    }

    public Object[] getParamsFromString(String sIn) {
        // remove empty tokens in beginning and end
        sIn = DvcHelper.handleTokenNullvaluesInStartAndEnd(sIn, getDelimiter());
        StringTokenizer tokens = new StringTokenizer(sIn, getDelimiter());
        debugMessage("getParamsFromString() received " + tokens.countTokens() + " tokens");
        Object[] tokensArray = new Object[tokens.countTokens()];
        for (int i = 0; i < tokens.countTokens(); i++) {
            tokensArray[i] = tokens.nextElement();
        }
        return tokensArray;
    }    

    /**
     * Handles what to do when no data is provided to the graph
     */
    public void noGraphDataFoundHandler() {
        // remove Graph from container
        super.remove(0);
        // Create message panel
        getNoDataFoundPanel().setVisible(true);
        getNoDataFoundPanel().setText(getNoDataFoundMessage());
        getNoDataFoundPanel().setSize(new Dimension(super.getWidth(), super.getHeight()));
        // Set message panel color to color of graph
        getNoDataFoundPanel().setSelectionColor(m_graph.getBackground());
        getNoDataFoundPanel().setRequestFocusEnabled(false);
        getNoDataFoundPanel().setVerifyInputWhenFocusTarget(false);
        super.add(getNoDataFoundPanel());
        recoverGraphToVBean = true;
    }

    /**
     * Finds and returns the instance of ExtendedFrame which holds the supplied Component.
     * @param c
     * @return
     */
    public ExtendedFrame getOwnerWindow(Component c) {
        while (c.getParent() != null) {
            if (c instanceof ExtendedFrame) {
                return (ExtendedFrame)c;
            } else
                c = c.getParent();
        }
        return null;
    }

    /**
     * Finds out if the ExtendedFrame which parameter comp exists in is the same as the ExtendedFrame which currently has focus.
     * @param frmOwnerFrame
     * @param comp
     * @return
     */
    public boolean isChildFocusOwner(ExtendedFrame frmOwnerFrame, Component comp) {
        ExtendedFrame frame = getOwnerWindow(comp);
        if (frame != null) {
            //System.out.println("Found parent frame");
            return frame == frmOwnerFrame;
        }
        return false;
    }
    
    /**
     * Setters and getters
     */
    
    public Graph getGraph() {
        return m_graph;
    }

    public LocalRelationalData getLocalRelationalData() {
        return lrd;
    }

    public void setRecoverGraphToVBean(boolean b) {
        recoverGraphToVBean = b;
    }

    public boolean isRecoverGraphToVBean() {
        return recoverGraphToVBean;
    }

    public void setFocusListenerAdded(boolean b) {
        bAddedFocusListener = b;
    }

    public boolean isFocusListenerAdded() {
        return bAddedFocusListener;
    }

    public void removeGraph() {
        super.remove(0);
    }

    public void addGraph() {
        super.add(m_graph);
    }

    public JPanel getGlassPanel() {
        return glass;
    }

    public int getViewListenerCount() {
        return mViewListenerCount;
    }

    public void setViewListenerCount(int iCount) {
        mViewListenerCount = iCount;
    }

    public GraphViewMouseListener getViewMouseListener() {
        return instanceVMListener;
    }

    public void setViewMouseListener(GraphViewMouseListener vml) {
        instanceVMListener = vml;
    }

    public int getReturnValueSelection() {
        return returnValueSelection;
    }
    
    public void setReturnValueSelection(int selection) {
        returnValueSelection = selection;
    }

    public boolean isShowGraphAsSeries() {
        return showGraphAsSeries;
    }
    
    public void setShowGraphAsSeries(boolean b) {
        showGraphAsSeries = b;
    }
    
    public int getSeparateFrameXPos() {
        return mSeparateFrameXPos;
    }
    
    public void setSeparateFrameXPos(int x) {
        mSeparateFrameXPos = x;
    }
    
    public int getSeparateFrameYPos() {
        return mSeparateFrameYPos;
    }
    
    public void setSeparateFrameYPos(int y) {
        mSeparateFrameYPos = y;
    }
    
    public int getGraphType() {
        return mGraphType;
    }
    
    public void setGraphType(int type) {
        mGraphType = type;
    }
    
    public JFrame getSeparateFrame() {
        return jf;
    }
    
    public void setSeparateFrame(JFrame frame) {
        jf = frame;
    }
    
    public JInternalFrame getOwnerFrame() {
        return (JInternalFrame)super.getComponent(0);
    }
    
    public JTextPane getNoDataFoundPanel() {
        return pnlNoDataFound;
    }
    
    public String getNoDataFoundMessage() {
        return sNoDataFoundMessage;
    }
    
    public void setNoDataFoundMessage(String s) {
        sNoDataFoundMessage = s;
    }
    
    /**
     * Returns the actual string used to separate values passed in
     * a string. There is a need, e.g. for color values passed for a title
     * string, to change the delimiter.
     *
     * @return String
     */
    public String getDelimiter() {
        return sDelimiter;
    }
    
    public void setDelimiter(String s) {
        sDelimiter = s;
    }
    
    /**
     * Debugging
     * @param dm
     */    
    public void debugMessage(String dm) {
        super.debugMessage(dm);
    }
}

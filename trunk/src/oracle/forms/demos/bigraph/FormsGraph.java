/**
 * ======================================================
 * Oracle Forms Wrapper for Data Visualization Components
 * ======================================================
 *
 * Author: Bincsoft
 * based on the work by Frank Nimphius (version: 9.0.4 - January 2005)
 *
 * FormsGraph.java Version 1.0.2 - December 12th 2010
 */

package oracle.forms.demos.bigraph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import oracle.dss.dataView.ViewMouseEvent;
import oracle.dss.dataView.ViewMouseMotionListener;
import oracle.dss.dataView.managers.ViewFormat;
import oracle.dss.graph.BaseGraphComponent;
import oracle.dss.graph.Graph;
import oracle.dss.graph.GraphConstants;
import oracle.dss.graph.Series;
import oracle.dss.util.CustomStyle;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DefaultErrorHandler;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SeriesOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;
import oracle.dss.util.format.BaseViewFormat;

import oracle.forms.demos.bigraph.impl.AddDataToGraph;
import oracle.forms.demos.bigraph.impl.AddIndexLine;
import oracle.forms.demos.bigraph.impl.AddRowData;
import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;
import oracle.forms.ui.CustomEvent;
import oracle.forms.ui.ExtendedFrame;
import oracle.forms.ui.VBean;


public class FormsGraph extends VBean {
    private String sDelimiter = ","; // default delimiter for the data passed to the graph
    private int mChart_height = 400; // default value for chart height.
    private int mChart_width = 400; // default value for chart width.
    private int mGraphType = Graph.BAR_VERT_CLUST; // default graph type is a simple vertical bar graph
    private int mViewListenerCount = 0; // only one view listener shall get implemented
    private int mSeparateFrameXPos = 10; // xPos of the separate Graph frame
    private int mSeparateFrameYPos = 10; // yPos of the separate Graph frame
    private boolean recoverGraphToVBean = false; // this flag is set when the Graph component in the Forms Bean component is replaced by a panel due to no data available in the graph
    protected boolean showGraphAsSeries = false;
    private boolean bDebugMode = false; // debug true/false
    private String mWindowTitle = "Forms - BI Graph"; // title of separate frame
    private String noDataFoundTxt = "Graph Contains No Data!"; // message shown when graph does not contain data
    private String debugPrefix = "";
    private IHandler mHandler;
    private Graph m_graph = null;
    private Series graphSeries = null; // handler to the graph series
    private GraphViewMouseListener instanceVMListener = null; // reacts to mouse events performed on a graph
    private ViewMouseMotionListener mouseMotionListener = null;
    private LocalRelationalData lrd = null;
    private JTextPane NoDataFoundPanel = new JTextPane();
    private ImageIcon emptyGraphImage = null;
    private boolean AddGraphToBean = true;

    // identifier used to define the data returned by a mouseclick on the Graph
    public final int allData = 0;
    public final int dataLabel = 1;
    public final int dataColumn = 2;
    public final int dataValue = 3;
    public final int dataPrimKey = 4;
    public final int noData = 5;

    // by default returns null
    protected int returnValueSelection = noData;

    protected static final ID pAddDataToGraph = ID.registerProperty("ADD_DATA_TO_GRAPH");
    protected static final ID pAddIndexLine = ID.registerProperty("ADD_INDEX_LINE");
    protected static final ID pAddReferenceObject = ID.registerProperty("ADD_REFERENCE_OBJECT");
    protected static final ID pAddRowData = ID.registerProperty("ADD_ROWDATA");
    protected static final ID pAlignTitleText = ID.registerProperty("ALIGN_TITLE_TEXT");
    protected static final ID pClearGraph = ID.registerProperty("CLEAR_GRAPH");
    protected static final ID pDebug = ID.registerProperty("DEBUG");
    protected static final ID pDelimiterInfo = ID.registerProperty("DELIMITER_INFO");
    protected static final ID pDisplayIndexLine = ID.registerProperty("DISPLAY_INDEX_LINE");
    protected static final ID pEnableTooltips = ID.registerProperty("ENABLE_TOOLTIPS");
    protected static final ID pEnableMarkerText = ID.registerProperty("ENABLE_MARKERTEXT");
    protected static final ID pEnableGradient = ID.registerProperty("ENABLE_GRADIENT");
    protected static final ID pExplodePieSlice = ID.registerProperty("EXPLODE_PIESLICE");
    protected static final ID pExportToClipboard = ID.registerProperty("EXPORT_CLIPBOARD");
    protected static final ID pFramePos = ID.registerProperty("FRAME_POS");
    protected static final ID pGetDelimiter = ID.registerProperty("GET_DELIMITER");
    protected static final ID pGraphType = ID.registerProperty("GRAPHTYPE");
    protected static final ID pGraphInFrame = ID.registerProperty("SHOW_GRAPH_IN_FRAME"); // displays Graph in separate frame
    protected static final ID pGraph = ID.registerProperty("SHOW_GRAPH"); // displays Graph in the Forms Bean Container
    protected static final ID pGraphInfo = ID.registerProperty("GRAPH_INFO");
    protected static final ID pHideAxis = ID.registerProperty("HIDE_AXIS");
    protected static final ID pHideFrame = ID.registerProperty("HIDE_FRAME");
    protected static final ID pHideGraph = ID.registerProperty("HIDE_GRAPH");
    protected static final ID pMouseAction = ID.registerProperty("MOUSEACTION");
    protected static final ID pModifyData = ID.registerProperty("MODIFY_ROW_DATA");
    protected static final ID pMaxScaleYAxis = ID.registerProperty("MAX_SCALE_Y_AXIS");
    protected static final ID pMinScaleYAxis = ID.registerProperty("MIN_SCALE_Y_AXIS");
    protected static final ID pMaxScaleY2Axis = ID.registerProperty("MAX_SCALE_Y2_AXIS");
    protected static final ID pMinScaleY2Axis = ID.registerProperty("MIN_SCALE_Y2_AXIS");
    protected static final ID pPositionLegendArea = ID.registerProperty("POSITION_LEGEND");
    protected static final ID pRotateXLabel = ID.registerProperty("ROTATE_X_LABEL");
    protected static final ID pRemoveData = ID.registerProperty("REMOVE_DATA");
    protected static final ID pRemoveTitle = ID.registerProperty("HIDE_TITLE");
    protected static final ID pRemoveSubTitle = ID.registerProperty("HIDE_SUBTITLE");
    protected static final ID pRemoveFooter = ID.registerProperty("HIDE_FOOTER");
    protected static final ID pRemoveXTitle = ID.registerProperty("HIDE_X_TITLE");
    protected static final ID pRemoveYTitle = ID.registerProperty("HIDE_Y_TITLE");
    protected static final ID pReturnValues = ID.registerProperty("RETURN_VALUES_ON_CLICK");
    protected static final ID pResetGraph = ID.registerProperty("RESET_GRAPH");

    protected static final ID pSetBackgroundColor = ID.registerProperty("SET_BACKGROUND");
    protected static final ID pSetDelimiter = ID.registerProperty("SET_DELIMITER");
    protected static final ID pSetLineGraphMarkers = ID.registerProperty("SET_LINEGRAPH_MARKERS");
    protected static final ID pSetScaledLogarithmic = ID.registerProperty("SET_SCALED_LOGARITHMIC");
    protected static final ID pSetBaseline = ID.registerProperty("SET_GRAPH_BASELINE");
    protected static final ID pSetGraphicAntialiasing = ID.registerProperty("SET_GRAPHIC_ANTIALIASING");
    protected static final ID pSetSeriesMarkerType = ID.registerProperty("SET_SERIES_MARKER_TYPE");
    protected static final ID pSetDepth = ID.registerProperty("SET_DEPTH");
    protected static final ID pSetDebugPrefix = ID.registerProperty("SET_DEBUG_PREFIX");
    protected static final ID pSetFrameWidthAndHeight = ID.registerProperty("SET_FRAME_HEIGHT_WIDTH");
    protected static final ID pSetTitle = ID.registerProperty("SET_TITLE");
    protected static final ID pSetNoDataFoundMessage = ID.registerProperty("SET_NO_DATA_FOUND");
    protected static final ID pSetLegendBorder = ID.registerProperty("SET_LEGEND_BORDER");
    protected static final ID pSetSubTitle = ID.registerProperty("SET_SUBTITLE");
    protected static final ID pSetFootnote = ID.registerProperty("SET_FOOTER");
    protected static final ID pSetPlotAreaColor = ID.registerProperty("SET_PLOT_AREA_COLOR");
    protected static final ID pSetTitleBackgroundColor = ID.registerProperty("SET_TITLE_BACKGROUND");
    protected static final ID pSetSubTitleBackgroundColor = ID.registerProperty("SET_SUBTITLE_BACKGROUND");
    protected static final ID pSetFooterBackgroundColor = ID.registerProperty("SET_FOOTER_BACKGROUND");
    protected static final ID pSetSeriesWidth = ID.registerProperty("SET_SERIES_WIDTH");
    protected static final ID pSetSeriesColor = ID.registerProperty("SET_SERIES_COLOR");
    protected static final ID pSetXLabel = ID.registerProperty("SET_X_LABEL");
    protected static final ID pSetXValueFont = ID.registerProperty("SET_X_VALUE_FONT");
    protected static final ID pSetYLabel = ID.registerProperty("SET_Y_LABEL");
    protected static final ID pSetY2Label = ID.registerProperty("SET_Y2_LABEL");
    protected static final ID pSetY1NumType = ID.registerProperty("SET_Y1_NUMBER_TYPE");
    protected static final ID pSetY1ScaleDown = ID.registerProperty("SET_Y1_SCALE_DOWN");
    protected static final ID pSetSeriesYAxis = ID.registerProperty("SET_SERIES_Y_AXIS");
    protected static final ID pSetYDecimalDigits = ID.registerProperty("SET_Y_DECIMAL_DIGITS");
    protected static final ID pSetY2DecimalDigits = ID.registerProperty("SET_Y2_DECIMAL_DIGITS");
    protected static final ID pSetStyle = ID.registerProperty("SET_STYLE");
    protected static final ID pSetXmlStyle = ID.registerProperty("SET_XML_STYLE");

    protected static final ID pShowPieLabels = ID.registerProperty("SHOW_PIE_LABELS");
    protected static final ID pShowGrid = ID.registerProperty("SHOW_GRID");
    protected static final ID pScrollBar = ID.registerProperty("SCROLLBAR");
    protected static final ID pSeriesCount = ID.registerProperty("COLUMNCOUNT");
    protected static final ID pShowColumnsAsRows = ID.registerProperty("SHOW_COLUMNS_AS_ROWS");
    protected static final ID pShowFrame = ID.registerProperty("SHOW_FRAME");
    protected static final ID pShowLabels = ID.registerProperty("SHOW_LABELS");
    protected static final ID pShowLegend = ID.registerProperty("SHOW_LEGEND");

    protected static final ID pTest = ID.registerProperty("TEST");

    /**
     * Custom item events
     */
    protected static final ID eGetSeriesCount = ID.registerProperty("RETURNED_COLUMN_NUMBER");
    protected static final ID eGraphAction = ID.registerProperty("GRAPH_ACTION");
    protected static final ID eGetDelimiter = ID.registerProperty("CURRENT_DELIMITER");

    // =========================
    // END OF FORMS PROPERTIES
    // =========================
    private JFrame jf = null;
    private JPanel glass = null;
    private String sVersion = "1.0.3";
    
    private boolean bAddedFocusListener = false;
    
    private HashMap hmPropertyHandlers = new HashMap();
    
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

    public FormsGraph() {

        super();
        // instantiate the BI Bean Graph

        m_graph = new Graph();

        mouseMotionListener = new ViewMouseMotionListener() {
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

        m_graph.addViewMouseMotionListener(mouseMotionListener);

        setDefaults(); // Set default values

        // disable debug information sent from the graph
        DefaultErrorHandler deh = new DefaultErrorHandler();
        deh.setDebugMode(DefaultErrorHandler.SHOW_NONE);
        m_graph.addErrorHandler(deh);
        debugPrefix = this.getClass().getName();

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
        
        hmPropertyHandlers.put(AddDataToGraph.propertyId, new AddDataToGraph());
        hmPropertyHandlers.put(AddIndexLine.propertyId, new AddIndexLine());
        hmPropertyHandlers.put(AddRowData.propertyId, new AddRowData());
    }

    /**
     * Implementation of IView interface. Init is called when the bean is first
     * instanciated
     *
     * @see oracle.forms.ui.IView
     */
    public void init(IHandler handler) {
        mHandler = handler;
        super.init(handler);

        // Create the glasspane which will stop mouse events
        glass = new JPanel();
        glass.setOpaque(false);
        glass.addMouseListener(new MouseAdapter() { });
        glass.addMouseMotionListener(new MouseMotionAdapter() { });
        glass.addKeyListener(new KeyAdapter() { });
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

        System.out.println("oracle.forms.demos.bigraph.FormsGraph version '" + sVersion + "' started, oracle.dss.graph.Graph version '" + m_graph.getVersion() + "'.");
    }

    /**
     * =========================================================================
     * Long silly if-else...
     * =========================================================================
     */
    
    

    /**
     * Implementation of IView interface which sets a requested property to a given value
     *
     * @param _ID  property to be set.
     * @param _object value of the property id.
     * @return true if the property could be set, false otherwise.
     * @see oracle.forms.ui.IView
     */
    public boolean setProperty(ID _ID, Object _object) {
        String sParams = _object == null ? "" : _object.toString();
        if (hmPropertyHandlers.containsKey(_ID)) {
            IFGPropImpl handler = (IFGPropImpl)hmPropertyHandlers.get(_ID);
            DebugMessage("PropertyHandler was found: " + _ID);
            return handler.handleProperty(sParams, this);
        } else if (_ID == pClearGraph) {
            return clearGraph(sParams);
        } else if (_ID == pDebug) {
            return debug(sParams);
        } else if (_ID == pDisplayIndexLine) {
            return displayIndexLine(sParams);
        } else if (_ID == pEnableMarkerText) {
            return enableMarkerText(sParams);
        } else if (_ID == pEnableGradient) {
            return enableGradient(sParams);
        } else if (_ID == pExportToClipboard) {
            return exportToClipboard(sParams);
        } else if (_ID == pFramePos) {
            return framePos(sParams);
        } else if (_ID == pGetDelimiter) {
            return getDelimiter(sParams);
        } else if (_ID == pGraph) {
            return graph(sParams);
        } else if (_ID == pGraphInFrame) {
            return graphInFrame(sParams);
        } else if (_ID == pGraphType) {
            return graphType(sParams);
        } else if (_ID == pHideGraph) {
            return hideGraph(sParams);
        } else if (_ID == pHideFrame) {
            return hideFrame(sParams);
        } else if (_ID == pModifyData) {
            return modifyData(sParams);
        } else if (_ID == pRemoveData) {
            return removeData(sParams);
        } else if (_ID == pRemoveTitle) {
            return removeTitle(sParams);
        } else if (_ID == pRemoveSubTitle) {
            return removeSubTitle(sParams);
        } else if (_ID == pRemoveFooter) {
            return removeFooter(sParams);
        } else if (_ID == pRemoveXTitle) {
            return removeXTitle(sParams);
        } else if (_ID == pRemoveYTitle) {
            return removeYTitle(sParams);
        } else if (_ID == pResetGraph) {
            return resetGraph(sParams);
        } else if (_ID == pReturnValues) {
            return returnValues(sParams);
        } else if (_ID == pSetLegendBorder) {
            return setLegendBorder(sParams);
        } else if (_ID == pSeriesCount) {
            return seriesCount(sParams);
        } else if (_ID == pSetBackgroundColor) {
            return setBackgroundColor(sParams);
        } else if (_ID == pSetPlotAreaColor) {
            return setPlotAreaColor(sParams);
        } else if (_ID == pSetTitleBackgroundColor) {
            return setTitleBackgroundColor(sParams);
        } else if (_ID == pSetSubTitleBackgroundColor) {
            return setSubtitleBackgroundColor(sParams);
        } else if (_ID == pSetSeriesColor) {
            return setSeriesColor(sParams);
        } else if (_ID == pSetSeriesWidth) {
            return setSeriesWidth(sParams);
        } else if (_ID == pSetFooterBackgroundColor) {
            return setFooterBackgroundColor(sParams);
        } else if (_ID == pSetDepth) {
            return setDepth(sParams);
        } else if (_ID == pSetDelimiter) {
            return setDelimiter(sParams);
        } else if (_ID == pSetDebugPrefix) {
            return setDebugPrefix(sParams);
        } else if (_ID == pSetFrameWidthAndHeight) {
            return setFrameWidthAndHeight(sParams);
        } else if (_ID == pSetTitle) {
            return setTitle(sParams);
        } else if (_ID == pSetNoDataFoundMessage) {
            return setNoDataFoundMessage(sParams);
        } else if (_ID == pSetSubTitle) {
            return setSubtitle(sParams);
        } else if (_ID == pSetFootnote) {
            return setFooter(sParams);
        } else if (_ID == pSetXLabel) {
            return setXLabel(sParams);
        } else if (_ID == pSetXValueFont) {
            return setXValueFont(sParams);
        } else if (_ID == pSetYLabel) {
            return setYLabel(sParams);
        } else if (_ID == pSetY2Label) {
            return setY2Label(sParams);
        } else if (_ID == pSetY1NumType) {
            return setY1NumType(sParams);
        } else if (_ID == pSetY1ScaleDown) {
            return setY1ScaleDown(sParams);
        } else if (_ID == pSetLineGraphMarkers) {
            return setLineGraphMarkers(sParams);
        } else if (_ID == pSetBaseline) {
            return setBaseline(sParams);
        } else if (_ID == pSetGraphicAntialiasing) {
            return setGraphicAntialiasing(sParams);
        } else if (_ID == pSetYDecimalDigits) {
            return setYDecimalDigits(sParams);
        } else if (_ID == pSetY2DecimalDigits) {
            return setY2DecimalDigits(sParams);
        } else if (_ID == pSetStyle) {
            return setStyle(sParams);
        } else if (_ID == pSetXmlStyle) {
            return setXmlStyle(sParams);
        } else if (_ID == pShowFrame) {
            return showFrame(sParams);
        } else if (_ID == pShowColumnsAsRows) {
            return showColumnsAsRows(sParams);
        } else if (_ID == pShowGrid) {
            return showGrid(sParams);
        } else if (_ID == pShowLegend) {
            return showLegend(sParams);
        } else if (_ID == pShowPieLabels) {
            return showPieLabels(sParams);
        }

        else if (_ID == pTest) {
            return test(sParams);
        }

        else {
            return super.setProperty(_ID, _object);
        }
    }

    /**
     * ===========================================================================
     * Implementations of Forms calls
     * ===========================================================================
     */

    /**
     * Forms property: DISPLAY_INDEX_LINE
     * @param sParams
     * @return
     */
    @Deprecated
    private boolean displayIndexLine(String sParams) {
        DebugMessage("DISPLAY_INDEX_LINE: Method deprecated, don't use it!");
        return true;
    }

    /**
     * Forms property: CLEAR_GRAPH
     * @param sParams
     * @return
     */
    private boolean clearGraph(String sParams) {
        lrd.clearGraphData();
        m_graph.destroyReferenceObjects(GraphConstants.ALL);
        m_graph.setDepthRadius(m_graph.getDepthRadius());
        m_graph.setDepthAngle(m_graph.getDepthAngle());
        return true;
    }

    /**
     * Forms property: DEBUG
     * @param sParams
     * @return
     */
    private boolean debug(String sParams) {
        bDebugMode = sParams.equalsIgnoreCase("TRUE");
        return true;
    }

    /**
     * Defines the x and y position of the sperarate graph frame
     * Forms property: FRAME_POS
     * @param sParams
     * @return
     */
    private boolean framePos(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("FRAME_POS: trying to set position to " + sParams);
            StringTokenizer st = new StringTokenizer(sParams, sDelimiter);
            int tokenLength = st.countTokens();
            String firstToken = "", secondToken = "";
            int xPos = 0, yPos = 0;
            try {
                if (tokenLength > 0) {
                    firstToken = (String)st.nextElement();
                    xPos = new Double(firstToken).intValue();
                    mSeparateFrameXPos = xPos;
                }
                if (tokenLength > 1) {
                    secondToken = (String)st.nextElement();
                    yPos = new Double(secondToken).intValue();
                    mSeparateFrameYPos = yPos;
                }

            } catch (NumberFormatException nfe) {
                DebugMessage("FRAME_POS Exception: Data passed for x and y postion could not be converted to int");
            } catch (Exception e) {
                DebugMessage("FRAME_POS: Exception" + e.getMessage());
            }
        } else {
            DebugMessage("FRAME_POS: No positional attributes passed - Ignoring command");
        }
        return true;
    }

    /**
     * Adds the Graph component to the Bean Container.
     * Forms property: GRAPH
     * @param sParams
     * @return
     */
    private boolean graph(String sParams) {
        DebugMessage("GRAPH: adding to container");
        if (jf != null) {
            jf.setVisible(false);
            jf.getContentPane().remove(0);
            ((JInternalFrame)super.getComponent(0)).add(m_graph);
            super.validate();
            jf = null;
        }
        return true;
    }

    /**
     * Creates a separate frame for the external Graph window.
     * Forms property: GRAPH_IN_FRAME
     * @param sParams
     * @return
     */
    private boolean graphInFrame(String sParams) {
        DebugMessage("GRAPH_IN_FRAME: adding to frame");
        int iWidth = 0;
        int iHeight = 0;
        if (sParams != null && !sParams.equals("")) {
            String saParams[] = sParams.split("\\|");
            if (saParams.length > 0)
                mWindowTitle = saParams[0];
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
        jf = new JFrame(mWindowTitle);
        jf.getContentPane().add(m_graph);
        if (iWidth != 0 && iHeight != 0)
            jf.setSize(iWidth, iHeight);
        else
            jf.setSize(1024, 768);
        jf.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    jf.setVisible(false);
                    graph("");
                }
            });
        jf.setVisible(true);
        return true;
    }

    /**
     * Forms property: GRAPH_TYPE
     * @param sParams
     * @return
     */
    private boolean graphType(String sParams) {
        DebugMessage("GRAPH_TYPE: trying to set graph type");
        mGraphType = (sParams.equals("") ? mGraphType : getInternalGraphType(sParams));
        m_graph.setGraphType(mGraphType);
        return true;
    }

    /**
     * Forms property: HIDE_GRAPH
     * @param sParams
     * @return
     */
    private boolean hideGraph(String sParams) {
        DebugMessage("HIDE_GRAPH");
        m_graph.setVisible(false);
        // in case that the graph was replaced by the No data found panel, indicating that no data was provided for the graph
        NoDataFoundPanel.setVisible(false);
        return true;
    }

    /**
     * Forms property: HIDE_FRAME
     * @param sParams
     * @return
     */
    private boolean hideFrame(String sParams) {
        DebugMessage("HIDE_FRAME: hiding");
        graph("");
        return true;
    }

    /**
     * Forms property: HIDE_TITLE
     * @param sParams
     * @return
     */
    private boolean removeTitle(String sParams) {
        DebugMessage("HIDE_TITLE: hiding");
        m_graph.getDataviewTitle().setVisible(false);
        return true;
    }

    /**
     * Forms property: HIDE_SUBTITLE
     * @param sParams
     * @return
     */
    private boolean removeSubTitle(String sParams) {
        DebugMessage("HIDE_SUBTITLE: hiding");
        m_graph.getDataviewSubtitle().setVisible(false);
        return true;
    }

    /**
     * Forms property HIDE_FOOTER
     * @param sParams
     * @return
     */
    private boolean removeFooter(String sParams) {
        DebugMessage("HIDE_FOOTER: hiding");
        m_graph.getDataviewFootnote().setVisible(false);
        return true;
    }

    /**
     * Forms property: HIDE_X_TITLE
     * @param sParams
     * @return
     */
    private boolean removeXTitle(String sParams) {
        m_graph.getO1Title().setVisible(false);
        DebugMessage("HIDE_X_TITLE: set visible to false");
        return true;
    }

    /**
     * Forms property: HIDE_Y_TITLE
     * @param sParams
     * @return
     */
    private boolean removeYTitle(String sParams) {
        m_graph.getY1Title().setVisible(false);
        DebugMessage("HIDE_Y_TITLE: set visible to false");
        return true;
    }

    /**
     * Forms property: MODIFY_DATA
     * @param sParams
     * @return
     */
    private boolean modifyData(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("MODIFY_DATA: Trying to update row in graph");
            if (lrd.ModifyData(sParams, sDelimiter)) {
                // refresh graph data
                // m_graph.setLocalRelationalData(lrd.getRelationalData());
                m_graph.setTabularData(lrd.getRelationalData());
            } else {
                DebugMessage("MODIFY_DATA: No matching column/row found in graph. Statement ignored");
            }
        } else {
            DebugMessage("MODIFY_DATA: No values passed with request. Statement ignored");
        }
        return true;
    }

    /**
     * define the type of data returned when clicking on a graph
     * allowed values are "ALL", "ROWLABEL", "COLUMNLABEL","CELLVALUE","PRIMARY_KEY"
     * Forms property: RETURN_VALUES
     * @param sParams
     * @return
     */
    private boolean returnValues(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("RETURN VALUES: trying to set return value ....");
            if ("ALL".equalsIgnoreCase(sParams)) {
                returnValueSelection = allData;
                DebugMessage("RETURN VALUES: ... to ALL");
            } else if ("ROWLABEL".equalsIgnoreCase(sParams)) {
                returnValueSelection = dataLabel;
                DebugMessage("RETURN VALUES: ... to ROWLABEL");
            } else if ("COLUMNLABEL".equalsIgnoreCase(sParams)) {
                returnValueSelection = dataColumn;
                DebugMessage("RETURN VALUES: ... to COLUMNLABEL");
            } else if ("VALUE".equalsIgnoreCase(sParams)) {
                returnValueSelection = dataValue;
                DebugMessage("RETURN VALUES: ... to VALUE");
            } else if ("PRIMARY_KEY".equalsIgnoreCase(sParams)) {
                returnValueSelection = dataPrimKey;
                DebugMessage("RETURN VALUES: ... to PRIMARY_KEY");
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                returnValueSelection = noData;
                DebugMessage("RETURN VALUES: ... to NONE");
            } else {
                DebugMessage("Property RETURN_VALUES: wrong attribute passed. Statement ignored");
                DebugMessage("allowed: \"ALL\",\"ROWLABEL\",\"COLUMNLABEL\",\"CELLVALUE\"or \"PRIMARY_KEY\"");
            }

        } else {
            DebugMessage("Property RETURN_VALUES: no attribute passed. Statement ignored");
        }
        return true;
    }

    /**
     * Forms property: SET_LEGEND_BORDER
     * @param sParams
     * @return
     */
    private boolean setLegendBorder(String sParams) {
        String bgColor = "";
        String brdColor = "";
        Color bgC = null;
        Color brdC = null;

        if (!sParams.equals("")) {
            String _sObject = sParams;
            _sObject = handleTokenNullvaluesInStartAndEnd(_sObject);
            StringTokenizer st = new StringTokenizer(_sObject, sDelimiter);
            int ct = st.countTokens();
            DebugMessage("SET_LEGEND_BORDER: Token count = " + ct);
            for (int i = 0; i < ct; i++) {
                // get/set colors for border and background
                switch (i) {
                case 0:
                    brdColor = (String)st.nextElement();
                    if ("TRANSPARENT".equalsIgnoreCase(brdColor)) {
                        m_graph.getLegendArea().setBorderTransparent(true);
                    } else {
                        DebugMessage("SET_LEGEND_BORDER: Border color value = " +
                                     brdColor);
                        brdC = ColorCodeRegistry.getColorCode(brdColor);
                        m_graph.getLegendArea().setBorderColor(brdC);
                    }
                    break;
                case 1:
                    bgColor = (String)st.nextElement();
                    if ("TRANSPARENT".equalsIgnoreCase(bgColor)) {
                        m_graph.getLegendArea().setFillTransparent(true);
                    } else {
                        DebugMessage("SET_LEGEND_BORDER: Background color value = " +
                                     bgColor);
                        bgC = ColorCodeRegistry.getColorCode(bgColor);
                        m_graph.getLegendArea().setFillColor(bgC);
                    }
                    break;
                default:
                }
            }
        } else {
            DebugMessage("SET_LEGEND_BORDER: No value passed for setting the border color");
        }
        return true;
    }

    /**
     * Forms property: SERIES_COUNT
     * @param sParams
     * @return
     */
    private boolean seriesCount(String sParams) {
        // retrieve graph column count for further usage
        DebugMessage("SERIES COUNT requested");
        // if rows shown as series then row number = column number
        try {
            if (showGraphAsSeries) {
                returnSeriesCount(m_graph.getRowCount());
            } else {
                returnSeriesCount(m_graph.getColumnCount());
            }
        } catch (EdgeOutOfRangeException ex) {
            DebugMessage("SERIES_COUNT: " + ex);
        }
        return true;
    }
    
    /**
     * The background color is passed either in a comma delimited string of RBG vlues in a range of (o...255) , or as a color name, e.g. red
     * Forms property: SET_BACKGROUND_COLOR
     * @param sParams
     * @return
     */
    private boolean setBackgroundColor(String sParams) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                DebugMessage("SET_BACKGROUND_COLOR: setting " + sParams +
                             " as a new background color");
                m_graph.getGraphBackground().setFillColor(col);
                m_graph.getGraphBackground().getSFX().setFillType(BaseGraphComponent.FT_COLOR);
            } else {
                DebugMessage("SET_BACKGROUND_COLOR: " + sParams +
                             " passed is not a valid color name or RGB value");
            }
            return true;
        } else {
            DebugMessage("SET_BACKGROUND: no color specified");
            return true;
        }
    }
    
    /**
     * The background color of the Plot areais passed either in a comma delimited string of RBG vlues in a range of (o...255) , or as a color name, e.g. red
     * Forms property: SET_PLOT_AREA_COLOR
     * @param sParams
     * @return
     */
    private boolean setPlotAreaColor(String sParams) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                DebugMessage("SET_PLOT_AREA_COLOR: setting " + sParams +
                             " as a new plot area color");
                m_graph.getPlotArea().setFillColor(col);
            } else {
                DebugMessage("SET_PLOT_AREA_COLOR: " + sParams +
                             " passed is not a valid color name or RGB value");
            }
            return true;
        } else {
            DebugMessage("SET_PLOT_AREA_COLOR: no color specified");
            return true;
        }
    }

    /**
     * Forms property: SET_TITLE_BACKGROUND
     * @param sParams
     * @return
     */
    private boolean setTitleBackgroundColor(String sParams) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                DebugMessage("SET_TITLE_BACKGROUND: setting " + sParams +
                             " as a new background color");
                m_graph.getDataviewTitle().setBackground(col);
            } else {
                DebugMessage("SET_TITLE_BACKGROUND: " + sParams +
                             " passed is not a valid color name or RGB value");
            }
            return true;
        } else {
            DebugMessage("SET_TITLE_BACKGROUND: no color specified");
            return true;
        }
    }

    /**
     * Forms property: SET_SUBTITLE_BACKGROUND
     * @param sParams
     * @return
     */
    private boolean setSubtitleBackgroundColor(String sParams) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                DebugMessage("SET_SUBTITLE_BACKGROUND: setting " + sParams +
                             " as a new background color");
                m_graph.getDataviewSubtitle().setBackground(col);
            } else {
                DebugMessage("SET_SUBTITLE_BACKGROUND: " + sParams +
                             " passed is not a valid color name or RGB value");
            }
            return true;
        } else {
            DebugMessage("SET_SUBTITLE_BACKGROUND: no color specified");
            return true;
        }
    }

    /**
     * Forms property: SET_SERIES_COLOR
     * @param sParams
     * @return
     */
    private boolean setSeriesColor(String sParams) {
        if (sParams.length() > 0) {
            // separate series name from string
            StringTokenizer st = new StringTokenizer(sParams, this.sDelimiter);

            // at least two arguments must be found
            if (st.countTokens() >= 2) {

                String strColor =
                    sParams.substring(sParams.indexOf(this.sDelimiter) + 1);
                String seriesName =
                    sParams.substring(0, sParams.indexOf(this.sDelimiter));

                Color col = ColorCodeRegistry.getColorCode(strColor);
                if (col != null) {
                    DebugMessage("SET_SERIES_COLOR: setting " + strColor +
                                 " as a new series color");

                    int series_count = 0;
                    try {
                        series_count = m_graph.getColumnCount();
                    } catch (EdgeOutOfRangeException ex) {
                        DebugMessage("SET_SERIES_COLOR: " + ex);
                    }

                    for (int i = 0; i < series_count; i++) {
                        String shortLabel = "";
                        try {
                            shortLabel =
                                    (String)m_graph.getDataAccessSliceLabel(DataDirector.COLUMN_EDGE,
                                                                            i,
                                                                            MetadataMap.METADATA_LONGLABEL);
                        } catch (EdgeOutOfRangeException ex) {
                            DebugMessage("SET_SERIES_COLOR: " + ex);
                        } catch (SliceOutOfRangeException ex) {
                            DebugMessage("SET_SERIES_COLOR: " + ex);
                        }

                        if (seriesName.trim().equalsIgnoreCase(shortLabel.trim())) {
                            try {
                                m_graph.getSeries().setColor(col, i);

                                DebugMessage("SET_SERIES_COLOR: Series " +
                                             seriesName + " found");
                                break;
                            } catch (SeriesOutOfRangeException ex) {
                                DebugMessage("SET_SERIES_COLOR: " + ex);
                                return true;
                            }
                        }
                    }
                    return true;
                } else {
                    DebugMessage("SET_SERIES_COLOR: " + sParams +
                                 " does not contain a valid color name or RGB value");
                }
            } else {
                DebugMessage("SET_SERIES_COLOR: no color specified");
            }
        } else {
            DebugMessage("SET_SERIES_COLOR: not enough arguments");
        }
        return true;
    }

    /**
     * Forms property: SET_SERIES_WIDTH
     * @param sParams
     * @return
     */
    private boolean setSeriesWidth(String sParams) {
        if (sParams.length() > 0) {
            // separate series name from string
            StringTokenizer st = new StringTokenizer(sParams, this.sDelimiter);
            int line_width;

            // at least two arguments must be found
            if (st.countTokens() >= 2) {

                String lineWidth =
                    sParams.substring(sParams.indexOf(this.sDelimiter) + 1);
                String seriesName =
                    sParams.substring(0, sParams.indexOf(this.sDelimiter)).trim();

                try {
                    // get line width
                    line_width = new Integer(lineWidth.trim()).intValue();

                    if (line_width <= 0) {
                        // only positiv integers accepted
                        line_width = line_width * -1;
                    }
                } catch (NumberFormatException nfe) {
                    DebugMessage("SET_SERIES_WIDTH: " + sParams +
                                 " does not contain a valid line width");
                    return true;
                }

                int series_count = 0;
                try {
                    series_count = m_graph.getColumnCount();
                } catch (EdgeOutOfRangeException ex) {
                    DebugMessage("SET_SERIES_WIDTH: " + ex);
                }

                for (int i = 0; i < series_count; i++) {
                    String shortLabel = "";
                    try {
                        shortLabel =
                                (String)m_graph.getDataAccessSliceLabel(DataDirector.COLUMN_EDGE,
                                                                        i,
                                                                        MetadataMap.METADATA_LONGLABEL);
                    } catch (EdgeOutOfRangeException ex) {
                        DebugMessage("SET_SERIES_WIDTH: " + ex);
                    } catch (SliceOutOfRangeException ex) {
                        DebugMessage("SET_SERIES_WIDTH: " + ex);
                    }

                    if (seriesName.equalsIgnoreCase(shortLabel.trim())) {
                        try {
                            m_graph.getSeries().setLineWidth(line_width, i);
                            DebugMessage("SET_SERIES_WIDTH: Series '" +
                                         seriesName + "' found");
                            break;
                        } catch (SeriesOutOfRangeException ex) {
                            DebugMessage("SET_SERIES_WIDTH: " + ex);
                            return true;
                        }
                    }
                }
                return true;
            } else {
                DebugMessage("SET_SERIES_WIDTH: not enough arguments specified in " +
                             sParams);
                return true;
            }

        } else {
            DebugMessage("SET_SERIES_WIDTH: no argument specified");
            return true;
        }
    }

    /**
     * Forms property: SET_FOOTER_BACKGROUND
     * @param sParams
     * @return
     */
    private boolean setFooterBackgroundColor(String sParams) {
        if (!sParams.equals("")) {
            Color col = ColorCodeRegistry.getColorCode(sParams);
            if (col != null) {
                DebugMessage("SET_FOOTER_BACKGROUND: setting " + sParams +
                             " as a new background color");
                m_graph.getDataviewFootnote().setBackground(col);
            } else {
                DebugMessage("SET_FOOTER_BACKGROUND: " + sParams +
                             " passed is not a valid color name or RGB value");
            }
            return true;
        } else {
            DebugMessage("SET_FOOTER_BACKGROUND: no color specified");
            return true;
        }
    }

    /**
     * Forms property: SET_DEPTH
     * @param sParams
     * @return
     */
    private boolean setDepth(String sParams) {
        if (!sParams.equals("")) {
            String _sObject = sParams;
            _sObject = handleTokenNullvaluesInStartAndEnd(_sObject);
            StringTokenizer st = new StringTokenizer(_sObject, sDelimiter);
            int tokenLength = st.countTokens();
            String firstToken = "", secondToken = "";

            if (tokenLength > 0) {
                firstToken = (String)st.nextElement();
            }
            if (tokenLength > 1) {
                secondToken = (String)st.nextElement();
            }

            DebugMessage("SET_DEPTH: tokens = " + firstToken + " , " +
                         secondToken);

            try {
                // set first token as depth value and second token as radius
                if (m_graph.getGraphType() == Graph.PIE) {
                    // in a pie there is a depth value and a rotation angle
                    m_graph.setPieDepth(new Double(firstToken).intValue());
                    m_graph.setPieRotation(new Double(secondToken).intValue());
                } else {
                    // all other graphs have a depth and a depth angle
                    m_graph.setDepthRadius(new Double(firstToken).intValue());
                    m_graph.setDepthAngle(new Double(secondToken).intValue());
                }

            } catch (NumberFormatException nfe) {
                DebugMessage("SET_DEPTH: Could not turn String value into integer - " +
                             firstToken + " , " + secondToken);
            }

        } else {
            DebugMessage("SET_DEPTH: No values passed");
        }
        return true;
    }

    /**
     * Forms property: SET_DELIMITER
     * @param sParams
     * @return
     */
    private boolean setDelimiter(String sParams) {
        DebugMessage("SET_DELIMITER: Trying to set delimiter value to '" +
                     sParams + "'");
        sDelimiter = sParams.equals("") ? sDelimiter : sParams;
        DebugMessage("SET_DELIMITER: Delimiter value is now '" + sDelimiter +
                     "'");
        return true;
    }

    /**
     * Forms property: SET_DEBUG_PREFIX
     * @param sParams
     * @return
     */
    private boolean setDebugPrefix(String sParams) {
        debugPrefix = sParams + " ==>";
        return true;
    }

    /**
     * Forms property: SET_EXTERNAL_FRAME_WIDTH_AND_HEIGHT
     * Deprecated, use GRAPH_IN_FRAME.
     * @param sParams
     * @return
     */
    @Deprecated
    private boolean setFrameWidthAndHeight(String sParams) {
        int frWidth = 0;
        int frHeight = 0;

        if (!sParams.equals("")) {
            try {
                String _sObject = sParams;
                _sObject = handleTokenNullvaluesInStartAndEnd(_sObject);
                StringTokenizer st = new StringTokenizer(_sObject, sDelimiter);
                int tokenLength = st.countTokens();
                frHeight = new Integer((String)st.nextElement()).intValue();
                if (tokenLength > 1) {
                    frWidth = new Integer((String)st.nextElement()).intValue();
                    mChart_width = frWidth;
                }
                mChart_height = frHeight;
            } catch (NumberFormatException nfe) {
                DebugMessage("SET_EXTERNAL_FRAME_WIDTH_AND_HEIGHT: " +
                             sParams +
                             " does not contain valid integer values");
            }
        } else {
            DebugMessage("SET_EXTERNAL_FRAME_WIDTH_AND_HEIGHT: No sizing value provided for external frame - ignoring statement");
        }
        return true;
    }

    /**
     * Forms property: SET_TITLE
     * @param sParams
     * @return
     */
    private boolean setTitle(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_TITLE retrieved string " + sParams);
            Object[] mo = getTitleFromString(sParams);
            if (mo.length > 0) {
                m_graph.getDataviewTitle().setVisible(true);
                DebugMessage("SET_TITLE Text: " + (String)mo[0]);
                m_graph.getDataviewTitle().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                DebugMessage("SET_TITLE Font: " + ((Font)mo[1]).getFontName());
                DebugMessage("SET_TITLE Font Style: " +
                             ((Font)mo[1]).getStyle());
                m_graph.getDataviewTitle().setFont((Font)mo[1]);
                m_graph.repaint();
            }
            if (mo.length > 2) {
                m_graph.getDataviewTitle().setForeground((Color)mo[2]);
            }
            m_graph.getDataviewTitle().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            DebugMessage("Property SET_TITLE: No attributes passed");
        }
        return true;
    }
    
    /**
     * Forms property: SET_NO_DATA_FOUND_MESSAGE
     * @param sParams
     * @return
     */
    private boolean setNoDataFoundMessage(String sParams) {
        noDataFoundTxt = sParams;
        return true;
    }

    /**
     * Forms property: SET_SUBTITLE
     * @param sParams
     * @return
     */
    private boolean setSubtitle(String sParams) {
        if (!sParams.equals("")) {
            Object[] mo = getTitleFromString(sParams);
            m_graph.getDataviewSubtitle().setVisible(true);
            m_graph.getDataviewSubtitle().setText((String)mo[0]);
            m_graph.getDataviewSubtitle().setFont((Font)mo[1]);
            m_graph.getDataviewSubtitle().setForeground((Color)mo[2]);
            m_graph.getDataviewSubtitle().setVisible(true);
            m_graph.getDataviewSubtitle().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            DebugMessage("SET_SUBTITLE: no attributes passed");
        }
        return true;
    }

    /**
     * Forms property: SET_FOOTER
     * @param sParams
     * @return
     */
    private boolean setFooter(String sParams) {
        if (!sParams.equals("")) {
            Object[] mo = getTitleFromString(sParams);
            m_graph.getDataviewFootnote().setVisible(true);
            m_graph.getDataviewFootnote().setText((String)mo[0]);
            m_graph.getDataviewFootnote().setFont((Font)mo[1]);
            m_graph.getDataviewFootnote().setForeground((Color)mo[2]);
            m_graph.getDataviewFootnote().setVisible(true);
            m_graph.getDataviewFootnote().setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            DebugMessage("SET_FOOTER: no attributes passed");
        }
        return true;
    }

    /**
     * Forms property: SET_X_LABEL
     * @param sParams
     * @return
     */
    private boolean setXLabel(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_X_LABEL retrieved string " + sParams);
            Object[] mo = getTitleFromString(sParams);
            if (mo.length > 0) {
                m_graph.getO1Title().setVisible(true);
                DebugMessage("SET_X_LABEL Text: " + (String)mo[0]);
                m_graph.getO1Title().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                DebugMessage("SET_X_LABEL Font: " +
                             ((Font)mo[1]).getFontName());
                DebugMessage("SET_X_LABEL Font Style: " +
                             ((Font)mo[1]).getStyle());
                m_graph.getO1Title().setFont((Font)mo[1]);
                m_graph.repaint();
            }
            /*
			 * if (mo.length >2){
			 * m_graph.getO1Title().setForeground((Color)mo[2]); }
			 * m_graph.getO1Title().setHorizontalAlignment(AlignCenter);
			 */
        } else {
            DebugMessage("SET_X_LABEL: No attributes passed");
        }
        return true;
    }

    /**
     * Forms property: SET_X_VALUE_FONT
     * @param sParams
     * @return
     */
    private boolean setXValueFont(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_X_VALUE_FONT: " + sParams);
            sParams = "blabla" + sDelimiter + sParams;
            Object[] mo = getTitleFromString(sParams);
            if (mo.length > 1) {
                DebugMessage("SET_X_VALUE_FONT Font: " +
                             ((Font)mo[1]).getFontName());
                DebugMessage("SET_X_VALUE_FONT Font Style: " +
                             ((Font)mo[1]).getStyle());
                m_graph.getO1TickLabel().setFont((Font)mo[1]);
            }
        } else {
            DebugMessage("SET_X_VALUE_FONT: No attributes passed");
        }
        return true;
    }

    /**
     * Forms property: SET_Y_LABEL
     * @param sParams
     * @return
     */
    private boolean setYLabel(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_Y_LABEL retrieved string " + sParams);
            Object[] mo = getTitleFromString(sParams);
            if (mo.length > 0) {
                m_graph.getY1Title().setVisible(true);
                DebugMessage("SET_Y_LABEL Text: " + (String)mo[0]);
                m_graph.getY1Title().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                DebugMessage("SET_Y_LABEL Font: " +
                             ((Font)mo[1]).getFontName());
                DebugMessage("SET_Y_LABEL Font Style: " +
                             ((Font)mo[1]).getStyle());
                m_graph.getY1Title().setFont((Font)mo[1]);
                m_graph.repaint();
            }
            /*
			 * if (mo.length >2){
			 * m_graph.getY1Title().setForeground((Color)mo[2]); }
			 * m_graph.getY1Title().setHorizontalAlignment(AlignCenter);
			 */
        } else {
            DebugMessage("SET_Y_LABEL: No attributes passed");
        }
        return true;
    }

    /**
     * Forms property: SET_Y2_TITLE
     * @param sParams
     * @return
     */
    private boolean setY2Label(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_Y2_TITLE retrieved string " + sParams);
            Object[] mo = getTitleFromString(sParams);
            if (mo.length > 0) {
                m_graph.getY2Title().setVisible(true);
                DebugMessage("SET_Y2_TITLE Text: " + (String)mo[0]);
                m_graph.getY2Title().setText((String)mo[0]);
            }
            if (mo.length > 1) {
                DebugMessage("SET_Y2_TITLE Font: " +
                             ((Font)mo[1]).getFontName());
                DebugMessage("SET_Y2_TITLE Font Style: " +
                             ((Font)mo[1]).getStyle());
                m_graph.getY2Title().setFont((Font)mo[1]);
                m_graph.repaint();
            }
            /*
			 * if (mo.length >2){
			 * m_graph.getY1Title().setForeground((Color)mo[2]); }
			 * m_graph.getY1Title().setHorizontalAlignment(AlignCenter);
			 */
        } else {
            DebugMessage("SET_Y2_TITLE: No attributes passed");
        }
        return true;
    }

    /**
     * Forms property: SET_Y1_NUMBER_TYPE
     * @param sParams
     * @return
     */
    private boolean setY1NumType(String sParams) {
        if (!sParams.equals("")) {
            // check for percent argument
            if ("%".equalsIgnoreCase(sParams)) {
                m_graph.getY1Axis().getViewFormat().setNumberType(ViewFormat.NUMTYPE_PERCENT);
            } else if ("GENERAL".equalsIgnoreCase(sParams)) {
                m_graph.getY1Axis().getViewFormat().setNumberType(ViewFormat.NUMTYPE_GENERAL);
            } else {
                // assume that the provided string is a currency
                m_graph.getY1Axis().getViewFormat().setNumberType(ViewFormat.NUMTYPE_CURRENCY);
                m_graph.getY1Axis().getViewFormat().setCurrencySymbol(sParams);
            }
        } else {
            DebugMessage("Not a valid argument in call to SET_Y1_NUMBER_TYPE");
        }
        return true;
    }

    /**
     * Forms property: SHOW_FRAME
     * Deprecated, use GRAPH_IN_FRAME.
     * @param sParams
     * @return
     */
    @Deprecated
    private boolean showFrame(String sParams) {
        DebugMessage("Deprecated, use GRAPH_IN_FRAME.");
        return true;
    }

    /**
     * Forms property: SHOW_COLUMNS_AS_ROWS
     * @param sParams
     * @return
     */
    private boolean showColumnsAsRows(String sParams) {
        if (!sParams.equals("")) {
            if (sParams.equalsIgnoreCase("TRUE")) {
                m_graph.setDataRowShownAsASeries(true);
                showGraphAsSeries = true;
                m_graph.getDataFilter().refresh();
            } else if (sParams.equalsIgnoreCase("FALSE")) {
                m_graph.setDataRowShownAsASeries(false);
                showGraphAsSeries = false;
                m_graph.getDataFilter().refresh();
            } else {
                // ignore
                DebugMessage("SHOW_COLUMNS_AS_ROWS attribute is not true nor false: Ignored");
            }
        }
        return true;
    }

    /**
     * Forms property: SHOW_GRID
     * @param sParams
     * @return
     */
    private boolean showGrid(String sParams) {
        if (!sParams.equals("")) {
            if (sParams.equalsIgnoreCase("TRUE")) {
                m_graph.getY1MajorTick().setTickStyle(BaseGraphComponent.GS_AUTOMATIC);
                m_graph.getO1MajorTick().setTickStyle(BaseGraphComponent.GS_AUTOMATIC);
                //m_graph.getY1MajorTick().setVisible(true); // Deprecated
                //m_graph.getO1MajorTick().setVisible(true); // Deprecated
            } else if (sParams.equalsIgnoreCase("FALSE")) {
                m_graph.getY1MajorTick().setTickStyle(BaseGraphComponent.GS_NONE);
                m_graph.getO1MajorTick().setTickStyle(BaseGraphComponent.GS_NONE);
                //m_graph.getY1MajorTick().setVisible(false); // Deprecated
                //m_graph.getO1MajorTick().setVisible(false); // Deprecated
            } else {
                // ignore
                DebugMessage("SHOW_GRID attribute is not true nor false: Ignored");
            }
        }
        return true;
    }

    /**
     * Forms property: REMOVE_DATA
     * @param sParams
     * @return
     */
    private boolean removeData(String sParams) {
        if (!sParams.equals("")) {
            if (lrd.RemoveData(sParams, sDelimiter)) {
                // refresh data shown in the graph
                // m_graph.setLocalRelationalData(lrd.getRelationalData());
                m_graph.setTabularData(lrd.getRelationalData());
            } else {
                DebugMessage("REMOVE_DATA: No row matches search criteria!");
            }
        } else {
            DebugMessage("REMOVE_DATA: No data passed, command ignored!");
        }
        return true;
    }

    /**
     * Forms property: SHOW_LEGEND
     * @param sParams
     * @return
     */
    private boolean showLegend(String sParams) {
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        m_graph.getLegendArea().setVisible(bEnable);
        DebugMessage("SHOW_LEGEND: " + bEnable);
        return true;
    }

    /**
     * Forms property: SHOW_PIE_LABELS
     * @param sParams
     * @return
     */
    private boolean showPieLabels(String sParams) {
        if (!sParams.equals("")) {
            if (sParams.equalsIgnoreCase("TEXT")) {
                m_graph.getSliceLabel().setTextType(BaseGraphComponent.LD_TEXT);
            } else if (sParams.equalsIgnoreCase("TEXT_PERCENT")) {
                m_graph.getSliceLabel().setTextType(BaseGraphComponent.LD_TEXT_PERCENT);
            } else if (sParams.equalsIgnoreCase("PERCENT")) {
                m_graph.getSliceLabel().setTextType(BaseGraphComponent.LD_PERCENT);
            } else if (sParams.equalsIgnoreCase("VALUE")) {
                m_graph.getSliceLabel().setTextType(BaseGraphComponent.LD_VALUE);
            } else {
                DebugMessage("SHOW_PIE_LABELS: " + sParams +
                             " is not a valid argument");
            }
        } else {
            DebugMessage("SHOW_PIE_LABELS: Argument must not be null");
        }
        return true;
    }

    /**
     * Forms property: SET_Y1_SCALE_DOWN
     * @param sParams
     * @return
     */
    private boolean setY1ScaleDown(String sParams) {
        if (!sParams.equals("")) {
            String scale = sParams;
            if ("NONE".equalsIgnoreCase(scale)) {
                m_graph.getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            }

            else if ("THOUSANDS".equalsIgnoreCase(scale)) {
                m_graph.getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_THOUSANDS);
            } else if ("MILLIONS".equalsIgnoreCase(scale)) {
                m_graph.getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_MILLIONS);
            } else if ("BILLIONS".equalsIgnoreCase(scale)) {
                m_graph.getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_BILLIONS);
            } else if ("TRILLIONS".equalsIgnoreCase(scale)) {
                m_graph.getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_TRILLIONS);
            } else {
                DebugMessage("SET_Y1_SCALE_DOWN: Wrong argument in call. Use NONE, THOUSANDS, MILLIONS, BILLIONS or TRILLIONS");
            }
        } else {
            DebugMessage("SET_Y1_SCALE_DOWN: No argument in call. Use NONE, THOUSANDS, MILLIONS, BILLIONS or TRILLIONS");
        }
        return true;
    }

    /**
     * Forms property: SET_LINEGRAPH_MARKER
     * @param sParams
     * @return
     */
    private boolean setLineGraphMarkers(String sParams) {
        DebugMessage("SET_LINEGRAPH_MARKER: received " + sParams +
                     " as an argument");
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        m_graph.setMarkerDisplayed(bEnable);
        return true;
    }

    /**
     * Forms property: SET_GRAPH_BASELINE
     * @param sParams
     * @return
     */
    private boolean setBaseline(String sParams) {
        if (!sParams.equals("")) {
            double baseLineVal = 0;
            DebugMessage("SET_GRAPH_BASELINE: Trying to set base line val to " +
                         sParams);
            try {
                baseLineVal = new Double(sParams).doubleValue();
                m_graph.getY1BaseLine().setValue(baseLineVal);
                DebugMessage("SET_GRAPH_BASELINE: Baseline set to (double) " +
                             baseLineVal);
            } catch (NumberFormatException nfe) {
                DebugMessage("SET_GRAPH_BASELINE: argument passed as a base line value is not a valid number format!");
                return true;
            }
        } else {
            DebugMessage("SET_GRAPH_BASELINE: No base line value passed - statement ignored");
        }
        return true;
    }

    /**
     * Forms property: SET_GRAPHIC_ANTIALIASING
     * @param sParams
     * @return
     */
    private boolean setGraphicAntialiasing(String sParams) {
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        m_graph.setGraphicAntialiasing(bEnable);
        m_graph.setTextAntialiasing(bEnable);
        DebugMessage("SET_GRAPHIC_ANTIALIASING: Setting graph and text antialiasing to: " +
                     bEnable);
        return true;
    }

    /**
     * Forms property: ENALBE_MARKERTEXT
     * @param sParams
     * @return
     */
    private boolean enableMarkerText(String sParams) {
        boolean bEnable = sParams.equalsIgnoreCase("FALSE") ? false : true;
        m_graph.getMarkerText().setVisible(bEnable);
        DebugMessage("ENABLE_MARKERTEXT: " + bEnable);
        return true;
    }

    /**
     * Forms property: ENABLE_GRADIENT
     * @param sParams
     * @return
     */
    private boolean enableGradient(String sParams) {
        if (sParams.equalsIgnoreCase("TRUE")) {
            DebugMessage("ENABLE_GRADIENT: true");
            m_graph.setSeriesEffect(oracle.dss.graph.Graph.SE_AUTO_GRADIENT);
        } else {
            DebugMessage("ENABLE_GRADIENT: false");
            m_graph.setSeriesEffect(oracle.dss.graph.Graph.SE_NONE);
        }
        return true;
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
     * Forms property: RESET_GRAPH
     * @param sParams
     * @return
     */
    private boolean resetGraph(String sParams) {
        DebugMessage("RESET_GRAPH");
        //m_graph.resetToDefault(GraphConstants.RESET_EVERYTHING);
        m_graph.resetToDefault(GraphConstants.RESET_XML_PROPERTIES);
        setDefaults();
        //m_graph.addViewMouseListener(instanceVMListener);
        //m_graph.addViewMouseMotionListener(mouseMotionListener);
        return true;
    }

    /**
     * Forms property: SET_Y_DECIMAL_DIGITS
     * @param sParams
     * @return
     */
    private boolean setYDecimalDigits(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_Y_DECIMAL_DIGITS: Setting '" + sParams +
                         "' decimal digits...");
            ViewFormat vf = new ViewFormat();
            vf.setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            m_graph.getY1Axis().setViewFormat(vf);
            vf.setDecimalDigit(Integer.parseInt(sParams));
            m_graph.getMarkerText().setViewFormat(vf,
                                                  BaseGraphComponent.VF_Y1);
        } else {
            DebugMessage("SET_Y_DECIMAL_DIGITS: no argument specified");
        }
        return true;
    }

    /**
     * Forms property: SET_Y2_DECIMAL_DIGITS
     * @param sParams
     * @return
     */
    private boolean setY2DecimalDigits(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SET_Y2_DECIMAL_DIGITS: Setting '" + sParams +
                         "' decimal digits...");
            ViewFormat vf = new ViewFormat();
            vf.setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
            m_graph.getY2Axis().setViewFormat(vf);
            vf.setDecimalDigit(Integer.parseInt(sParams));
            m_graph.getMarkerText().setViewFormat(vf,
                                                  BaseGraphComponent.VF_Y2);
        } else {
            DebugMessage("SET_Y2_DECIMAL_DIGITS: no argument specified");
        }
        return true;
    }

    /**
     * Forms property: EXPORT_CLIPBOARD
     * @param sParams
     * @return
     */
    private boolean exportToClipboard(String sParams) {
        DebugMessage("EXPORT_CLIPBOARD: Exporting...");
        DebugMessage("EXPORT_CLIPBOARD: m_graph.getParent() h=" +
                     m_graph.getParent().getSize().getHeight() + " w=" +
                     m_graph.getParent().getSize().getWidth());
        m_graph.setImageSize(m_graph.getParent().getSize());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new FormsGraphTransferable(this),
                                                                     null);
        DebugMessage("EXPORT_CLIPBOARD: m_graph h=" +
                     m_graph.getImageSize().getHeight() + " w=" +
                     m_graph.getImageSize().getWidth());
        DebugMessage("EXPORT_CLIPBOARD: Done!");
        return true;
    }

    /**
     * Forms property: SET_STYLE
     * @param sParams
     * @return
     */
    private boolean setStyle(String sParams) {
        DebugMessage("SET_STYLE: Setting style from name...");
        String sPath = "/oracle/dss/graph/styles/" + sParams.toLowerCase() + ".xml";
        InputStream is = m_graph.getClass().getResourceAsStream(sPath);
        try {
            CustomStyle style = new CustomStyle(convertStreamToString(is));
            is.close();
            m_graph.setStyle(style);
        } catch (Exception ex) {
            DebugMessage("setDefaultStyle(): " + ex);
        }
        DebugMessage("SET_STYLE: Done!");
        return true;
    }

    /**
     * Forms property: SET_XML_STYLE
     * @param sParams
     * @return
     */
    private boolean setXmlStyle(String sParams) {
        DebugMessage("SET_XML_STYLE: Setting style from string...");
        try {
            CustomStyle style = new CustomStyle(sParams);
            m_graph.setStyle(style);
        } catch (Exception ex) {
            DebugMessage("SET_XML_STYLE: " + ex);
        }
        DebugMessage("SET_XML_STYLE: Done!");
        return true;
    }
    
    /**
     * Used for testing.
     * Forms property: TEST
     * @param sParams
     * @return
     */
    private boolean test(String sParams) {
        DebugMessage("TEST");
        // Do some tests here
        DebugMessage("TEST: Done!");
        return true;
    }

    /**
     * =========================================================================
     * Other methods
     * =========================================================================
     */


    /**
     * Retrieves the int value contained in the attribute
     * passed with set_custom_property(), defining the Graph type. If no integer
     * value is found, then the existing value stored in mGraphType is returned.
     * @param type
     * @return
     */
    private int getInternalGraphType(Object type) {
        int iGraphType = 9999999;
        try {
            // call graphTypeAnalyzer.analyze() to determine the int
            // representation of the graph.
            // 0 is returned if no matching BI Graph is registerd for Forms
            iGraphType = GraphTypeRegistry.getTypeForString((String)type);

            DebugMessage("getInternalGraphType(): type returned for " + type +
                         " = " + iGraphType);

            if (iGraphType == 9999999) {
                iGraphType = mGraphType;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // set default
            iGraphType = mGraphType;

        } finally {
        }
        return iGraphType;
    }

    public void DebugMessage(String dm) {
        // if debug enabled then print message to system out
        if (bDebugMode) {
            System.out.println(debugPrefix + " " + dm);
        }
    }

    protected void dispatchMouseAction(String msg) {
        try {
            DebugMessage("dispatchMouseAction(): " + msg);
            CustomEvent ce = new CustomEvent(mHandler, eGraphAction);
            mHandler.setProperty(pGraphInfo, msg);
            dispatchCustomEvent(ce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getDelimiter returns the actual string used to separate values passed in
     * a string. There is a need, e.g. for color values passed for a title
     * string, to change the delimiter.
     *
     * @return String
     */
    public String getDelimiter() {
        return sDelimiter;
    }

    private void dispatchDelimiter() {
        try {
            CustomEvent dde = new CustomEvent(mHandler, eGetDelimiter);
            mHandler.setProperty(pDelimiterInfo, sDelimiter);
            dispatchCustomEvent(dde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method dispatching the number of columns shown in a graph and by raising
     * a custom event.
     */
    private void returnSeriesCount(int ret) {
        try {
            CustomEvent sce = new CustomEvent(mHandler, eGetSeriesCount);
            DebugMessage("returnSeriesCount(): returning " + ret);
            mHandler.setProperty(pGraphInfo, Integer.toString(ret));
            dispatchCustomEvent(sce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[] getParamsFromString(String sIn) {
        // remove empty tokens in beginning and end
        sIn = handleTokenNullvaluesInStartAndEnd(sIn);
        StringTokenizer tokens = new StringTokenizer(sIn, sDelimiter);
        DebugMessage("getParamsFromString() received " + tokens.countTokens() +
                     " tokens");
        Object[] tokensArray = new Object[tokens.countTokens()];
        for (int i = 0; i < tokens.countTokens(); i++) {
            tokensArray[i] = tokens.nextElement();
        }
        return tokensArray;
    }

    /**
     * public Object[] getTitleFromString(String in) takes a delimited string
     * and creates a title string, a Font value and a Color value of it. The
     * delimiter used is the defined delimiter stored in sDelimiter, or ',' as
     * the default. The String syntax is '<Title>,<color>,<[b][i]>,<size>,<Font
     * Name>' Values can be omitted from right to left, e.g
     * '<Title>,<color>,<[b][i]>,<size>' or '<Title>,<color>,<[b][i]>'
     *
     */
    public Object[] getTitleFromString(String in) {
        Object[] fontProperties = new Object[4];
        Color fontCol = Color.black;
        String title = "";
        String fontName = "Arial";
        int size = 11;
        int style = 0;

        // remove empty tokens in beginning and end
        in = handleTokenNullvaluesInStartAndEnd(in);

        StringTokenizer tokens = new StringTokenizer(in, sDelimiter);
        int tokenCount = tokens.countTokens();
        DebugMessage("getTitleFromString() received " + tokenCount +
                     " tokens");
        for (int i = 0; i < tokenCount; i++) {
            switch (i) {
                // Title string
            case 0:
                title = (String)tokens.nextElement();
                DebugMessage("getFontFromString(): Title text =" + title);
                break;
                // Font color
            case 1:
                fontCol = ColorCodeRegistry.getColorCode((String)tokens.nextElement());
                fontCol = (fontCol != null ? fontCol : Color.black);
                DebugMessage("getFontFromString(): Color =" +
                             fontCol.toString());
                break;
                // Style
            case 2:
                String s = (String)tokens.nextElement();
                DebugMessage("getFontFromString(): Style =" + s);
                if ((s.indexOf("n")) >= 0) {
                    style = Font.PLAIN;
                }
                if ((s.indexOf("b")) >= 0) {
                    style = Font.BOLD;
                }
                if ((s.indexOf("i")) >= 0) {
                    // bit wise concatenation of italic
                    style |= Font.ITALIC;
                }
                break;
                // size
            case 3:
                try {
                    size =(new Double((String)tokens.nextElement())).intValue();
                    DebugMessage("getFontFromString(): Font size =" + size);
                } catch (NumberFormatException nfe) {
                    DebugMessage("getTitleFromString(): " + size + " is an unknow font size and cannot be casted to Integer");
                    break;
                }

                // color
            case 4:
                fontName = (String)tokens.nextElement();
                DebugMessage("getFontFromString(): Font name =" + fontName);
                break;
            default: // ignore
            }
        }
        fontProperties[0] = title;
        fontProperties[1] = new Font(fontName, style, size);
        fontProperties[2] = fontCol;
        return fontProperties;
    }

    public String handleTokenNullvaluesInStartAndEnd(String in) {
        DebugMessage("Method: handleTokenNullValuesInStartAndEnd received: " +
                     in);

        /*
		 * tokenizers don't work well if nothing is provided between delimiters.
		 * Therefore all String must be checked for this
		 */
        // check if token is first character
        in = (in.startsWith(sDelimiter) ? " " + in : in);

        // check if delimiter is last character in string and if, remove
        while (in.lastIndexOf(sDelimiter) ==
               (in.length() - sDelimiter.length())) {
            in = in.substring(0, in.lastIndexOf(sDelimiter) - 1);
        }

        DebugMessage("Method: handleTokenNullValuesInStartAndEnd returns: " +
                     in);

        return in;
    }

    /**
     * private void handleZerodDataFound() adds teh user defined response to the
     * Graph Bean that shows when no data was passed to the graph
     */
    public void noGraphDataFoundHandler() {
        // remove Graph from container
        super.remove(0);
        // Create message panel
        NoDataFoundPanel.setVisible(true);
        NoDataFoundPanel.setText(noDataFoundTxt);
        NoDataFoundPanel.setSize(new Dimension(super.getWidth(),
                                               super.getHeight()));
        // Set message panel color to color of graph
        NoDataFoundPanel.setSelectionColor(m_graph.getBackground());
        NoDataFoundPanel.setRequestFocusEnabled(false);
        NoDataFoundPanel.setVerifyInputWhenFocusTarget(false);
        super.add(NoDataFoundPanel);
        recoverGraphToVBean = true;
    }

    public Object getProperty(ID _ID) {
        /**********************
		 * GET DELIMITER
		 * *******************/
        if (_ID == pGetDelimiter) {
            return sDelimiter;
        }
        /************************
		 * COLUMNS COUNT
		 * *********************/
        else if (_ID == pSeriesCount) {
            try {
                // retrieve graph column count for further usage
                DebugMessage("SERIES COUNT requested");
                // if rows shown as series then row number = column number
                if (showGraphAsSeries) {
                    return new Integer(m_graph.getRowCount());
                } else {
                    return new Integer(m_graph.getColumnCount());
                }
            } catch (Exception e) {
                DebugMessage("====SERIES COUNT Error====");
                DebugMessage(e.getMessage());
                DebugMessage("==========================");
                DebugMessage("returning 0");
                // show 0 if no column can be retrieved
                return new Integer(0);
            }
        } else {
            return super.getProperty(_ID);
        }
    }

    /**
     * Methods for finding and setting XML styles
     */
    private boolean setDefaultStyle() {
        DebugMessage("setDefaultStyle()");
        InputStream is =
            m_graph.getClass().getResourceAsStream("/oracle/forms/demos/bigraph/style_default.xml");
        try {
            //System.out.println(convertStreamToString(is));
            CustomStyle style = new CustomStyle(convertStreamToString(is));
            is.close();
            m_graph.setStyle(style);
        } catch (Exception ex) {
            DebugMessage("setDefaultStyle(): " + ex);
        }
        return true;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * Sets default values on graph including style
     */
    private void setDefaults() {

        setDefaultStyle();

        // show rows as in the relational world
        m_graph.setDataRowShownAsASeries(false);
        // show Y1 value in full length
        m_graph.getY1Axis().getViewFormat().setScaleFactor(BaseViewFormat.SCALEFACTOR_NONE);
        // have the Bean managing the layout of the Graph
        m_graph.setAutoLayout(Graph.AL_ALWAYS);
        // the graph is first shown when data is provided
        m_graph.setVisible(false);
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
}

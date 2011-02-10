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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.NumberFormatException;

import java.util.ArrayList;
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
import oracle.dss.graph.LegendArea;
import oracle.dss.graph.ReferenceObject;
import oracle.dss.graph.Series;
import oracle.dss.util.CustomStyle;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DefaultErrorHandler;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.SeriesOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;
import oracle.dss.util.format.BaseViewFormat;

import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;
import oracle.forms.ui.CustomEvent;
import oracle.forms.ui.VBean;


public class FormsGraph extends VBean implements Transferable {
    private String sDelimiter = ","; // default delimiter for the data passed to the graph
    private int mChart_height = 400; // default value for chart height.
    private int mChart_width = 400; // default value for chart width.
    private int mGraphType = Graph.BAR_VERT_CLUST; // default graph type is a simple vertical bar graph
    private int mViewListenerCount = 0; // only one view listener shall get implemented
    private int mSeparateFrameXPos = 10; // xPos of the separate Graph frame
    private int mSeparateFrameYPos = 10; // yPos of the separate Graph frame
    private boolean recoverGraphToVBean = false; // this flag is set when the Graph component in the Forms Bean component is replaced by a panel due to no data available in the graph
    private int AlignRight = SwingConstants.RIGHT;
    private int AlignLeft = SwingConstants.LEFT;
    private int AlignCenter = SwingConstants.CENTER;
    protected boolean showGraphAsSeries = false;
    private boolean bDebugMode = false; // debug true/false
    private String mWindowTitle = "Forms - BI Graph"; // title of separate frame
    private String noDataFoundTxt = "Graph Contains No Data!"; // message shown when graph does not contain data
    private String debugPrefix = "";
    private IHandler mHandler;
    private Graph m_graph = null;
    private Series graphSeries = null; // handler to the graph series
    private mViewMouseListener instanceVMListener = null; // reacts to mouse events performed on a graph
    private ViewMouseMotionListener mouseMotionListener = null;
    private localRelationalData lrd = null;
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

    private boolean bMouseActive = true;

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
    protected static final ID pMouseActive = ID.registerProperty("MOUSE_ACTIVE");
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
    private String sVersion = "1.0.2";

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
        lrd = new localRelationalData(this);

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
        instanceVMListener = new mViewMouseListener(m_graph, this, lrd);
        mViewListenerCount = 1;
        // ***************************************************

        m_graph.addViewMouseListener(instanceVMListener);
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
        if (_ID == pAddRowData) {
            return addRowData(sParams);
        } else if (_ID == pAddIndexLine) {
            return addIndexLine(sParams);
        } else if (_ID == pAddReferenceObject) {
            return addReferenceObject(sParams);
        } else if (_ID == pAddDataToGraph) {
            return addDataToGraph(sParams);
        } else if (_ID == pAlignTitleText) {
            return alignTitleText(sParams);
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
        } else if (_ID == pEnableTooltips) {
            return enableTooltips(sParams);
        } else if (_ID == pExplodePieSlice) {
            return explodePieSlice(sParams);
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
        } else if (_ID == pHideAxis) {
            return hideAxis(sParams);
        } else if (_ID == pHideGraph) {
            return hideGraph(sParams);
        } else if (_ID == pHideFrame) {
            return hideFrame(sParams);
        } else if (_ID == pMinScaleYAxis) {
            return minScaleYAxis(sParams);
        } else if (_ID == pMaxScaleYAxis) {
            return maxScaleYAxis(sParams);
        } else if (_ID == pMinScaleY2Axis) {
            return minScaleY2Axis(sParams);
        } else if (_ID == pMaxScaleY2Axis) {
            return maxScaleY2Axis(sParams);
        } else if (_ID == pMouseAction) {
            return mouseAction(sParams);
        } else if (_ID == pMouseActive) {
            return mouseActive(sParams);
        } else if (_ID == pModifyData) {
            return modifyData(sParams);
        } else if (_ID == pPositionLegendArea) {
            return positionLegendArea(sParams);
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
        } else if (_ID == pRotateXLabel) {
            return rotateXLabel(sParams);
        } else if (_ID == pScrollBar) {
            return scrollBar(sParams);
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
        } else if (_ID == pSetSeriesMarkerType) {
            return setSeriesMarkerType(sParams);
        } else if (_ID == pSetScaledLogarithmic) {
            return setScaledLogarithmic(sParams);
        } else if (_ID == pSetBaseline) {
            return setBaseline(sParams);
        } else if (_ID == pSetGraphicAntialiasing) {
            return setGraphicAntialiasing(sParams);
        } else if (_ID == pSetSeriesYAxis) {
            return setSeriesYAxis(sParams);
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
        } else if (_ID == pShowLabels) {
            return showLabels(sParams);
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
     * Forms property: ADD_ROW_DATA
     * @param sParams
     * @return
     */
    private boolean addRowData(String sParams) {
        String sGraphDataRow = null;
        // get String from Forms Object
        if (!sParams.equals("")) {
            sGraphDataRow = sParams;
            // check if delimiter is used correctly
            if (sGraphDataRow.indexOf(sDelimiter) > 0) {
                /*
                 * Pass string to localRelationalData class. in here the
                 * string is parsed and ignored if it doesn't contain 4
                 * parameters and if a number exception occurs. The data
                 * parameters are of tyle label, label, double and
                 * finally integer
                 */
                if (!lrd.addRelationalDataRow(sGraphDataRow, sDelimiter)) {
                    DebugMessage("ADD_ROW_DATA: " + sGraphDataRow +
                                 " not accepted");
                }
                return true;
            } else {
                DebugMessage("ADD_ROWDATA: No valid string delimiter found. Expected \"" +
                             sDelimiter + "\"");
                return true;
            }
        } else {
            DebugMessage("ADD_ROWDATA: No data passed");
            return true;
        }
    }

    /**
     * Forms property: ADD_INDEX_LINE
     * @param sParams
     * @return
     */
    private boolean addIndexLine(String sParams) {
        // defaults
        String theIndexText = "";
        String theAxis = "";
        int theIndexNumber = -1;
        Color theIndexLineColor = Color.red;
        double theValue = 0.0;
        boolean showIndexInLegend = false;
        int theLineWidth = 1;

        if (sParams.length() > 0) {
            StringTokenizer stok =
                new StringTokenizer(sParams, this.sDelimiter);
            int tokenLength = stok.countTokens();

            DebugMessage("ADD_INDEX_LINE - Arguments received: " + sParams);
            DebugMessage("ADD_INDEX_LINE - Tokens: " + tokenLength);

            if (tokenLength > 0) {
                // set Text
                theIndexText = (String)stok.nextElement();
            }

            if (tokenLength > 1) {
                // set visibility
                if ("true".equalsIgnoreCase(((String)stok.nextElement()).trim())) {
                    showIndexInLegend = true;
                    DebugMessage("ADD_INDEX_LINE: Show index line");
                } else {
                    showIndexInLegend = false;
                    DebugMessage("ADD_INDEX_LINE: Don't show index line");
                }
            }

            if (tokenLength > 2) {
                // set axis
                theAxis = ((String)stok.nextElement()).trim();
            }

            if (tokenLength > 3) {
                // set index line - 0 .. 2
                try {
                    theIndexNumber =
                            new Double((String)stok.nextElement()).intValue();
                } catch (Exception e) {
                    DebugMessage("ADD_INDEX_LINE: Not a valid index line number. Should be 0 .. 2");
                    return true;
                }
            }

            if (tokenLength > 4) {
                // set value as double
                try {
                    theValue =
                            new Double((String)stok.nextElement()).doubleValue();
                } catch (Exception e) {
                    DebugMessage("ADD_INDEX_LINE: Not a valid index line value. Should be like 1000.00");
                    return true;
                }

            }

            if (tokenLength > 5) {
                // set color
                Color newColor =
                    colorCodeRegistry.getColorCode((String)stok.nextElement());
                theIndexLineColor =
                        newColor != null ? newColor : theIndexLineColor;
            }

            if (tokenLength > 6) {
                try {
                    theLineWidth =
                            new Double((String)stok.nextElement()).intValue();
                } catch (Exception e) {
                    DebugMessage("ADD_INDEX_LINE: Not a valid value for the index line width");
                }
            }

            // putting it all together						
            ReferenceObject refObj = m_graph.createReferenceObject();
            refObj.setLineValue(theValue);
            refObj.setColor(theIndexLineColor);
            refObj.setDisplayedInLegend(showIndexInLegend);
            refObj.setText(theIndexText);
            theLineWidth = theLineWidth > 0 ? theLineWidth : 1;
            refObj.setLineWidth(theLineWidth);
            refObj.setLocation(BaseGraphComponent.RO_FRONT);

            if (theAxis.equalsIgnoreCase("x-axis"))
                refObj.setAssociation(GraphConstants.X1AXIS);
            if (theAxis.equalsIgnoreCase("X1AXIS"))
                refObj.setAssociation(GraphConstants.X1AXIS);
            if (theAxis.equalsIgnoreCase("Y1AXIS"))
                refObj.setAssociation(GraphConstants.Y1AXIS);
            if (theAxis.equalsIgnoreCase("Y2AXIS"))
                refObj.setAssociation(GraphConstants.Y2AXIS);
            if (theAxis.equalsIgnoreCase("SERIES"))
                refObj.setAssociation(GraphConstants.SERIES);


        } else {
            DebugMessage("ADD_INDEX_LINE: No valid arguments passed with request");
        }
        return true;
    }

    /**
     * Forms property: ADD_REFERENCE_OBJECT
     * @param sParams
     * @return
     */
    private boolean addReferenceObject(String sParams) {
        if (sParams.length() > 0) {
            String[] aParams = sParams.split(sDelimiter);
            DebugMessage("ADD_REFERENCE_OBJECT: Received " + aParams.length +
                         " parameters.");

            if (aParams.length != 9 && aParams.length != 10) {
                DebugMessage("ADD_REFERENCE_OBJECT: Incorrect no. of parameters, need 9 for a line and 10 for an area.");
                return true;
            }

            ReferenceObject refObj = m_graph.createReferenceObject();

            // Object type
            refObj.setType(aParams[0].equalsIgnoreCase("AREA") ?
                           BaseGraphComponent.RO_AREA :
                           BaseGraphComponent.RO_LINE);

            // Object text
            refObj.setText(aParams[1]);

            // Association axis
            if (aParams[2].equalsIgnoreCase("Y1AXIS"))
                refObj.setAssociation(GraphConstants.Y1AXIS);
            if (aParams[2].equalsIgnoreCase("Y2AXIS"))
                refObj.setAssociation(GraphConstants.Y2AXIS);
            if (aParams[2].equalsIgnoreCase("X1AXIS"))
                refObj.setAssociation(GraphConstants.X1AXIS);
            if (aParams[2].equalsIgnoreCase("SERIES"))
                refObj.setAssociation(GraphConstants.SERIES);

            // Color
            Color newColor = colorCodeRegistry.getColorCode(aParams[3]);
            refObj.setColor(newColor != null ? newColor : Color.red);

            // Show in legend
            refObj.setDisplayedInLegend(aParams[4].equalsIgnoreCase("true"));

            // Line width
            refObj.setLineWidth(Integer.valueOf(aParams[5]).intValue());

            // Line style
            if (aParams[6].equalsIgnoreCase("SOLID"))
                refObj.setLineStyle(BaseGraphComponent.LS_SOLID);
            if (aParams[6].equalsIgnoreCase("DASH"))
                refObj.setLineStyle(BaseGraphComponent.LS_DASH);
            if (aParams[6].equalsIgnoreCase("DOTTED"))
                refObj.setLineStyle(BaseGraphComponent.LS_DOTTED);
            if (aParams[6].equalsIgnoreCase("DASH_DOT"))
                refObj.setLineStyle(BaseGraphComponent.LS_DASH_DOT);

            // Set the reference object to front or back
            refObj.setLocation(aParams[7].equalsIgnoreCase("FRONT") ?
                               BaseGraphComponent.RO_FRONT :
                               BaseGraphComponent.RO_BACK);

            //Object value
            if (aParams[0].equalsIgnoreCase("AREA")) {
                refObj.setLowValue(Double.valueOf(aParams[8]).doubleValue());
                refObj.setHighValue(Double.valueOf(aParams[9]).doubleValue());
            } else
                refObj.setLineValue(Double.valueOf(aParams[8]).doubleValue());
        } else {
            DebugMessage("ADD_REFERENCE_OBJECT: No valid parameters passed with request.");
        }
        return true;
    }

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
     * Forms property: ADD_DATA_TO_GRAPH
     * @param sParams
     * @return
     */
    private boolean addDataToGraph(String sParams) {
        DebugMessage("ADD_DATA_TO_GRAPH: setting data for display");
        ArrayList al = lrd.getRelationalData();
        DebugMessage("ADD_DATA_TO_GRAPH: Size of arraylist: " + al.size());
        // handle no data passed
        if (al.size() == 0) {
            DebugMessage("ADD_DATA_TO_GRAPH: decide on the response in the UI");
            noGraphDataFoundHandler();
        } else {
            // if the graph component previously have been removed from
            // the Graph then it needs to be added back there
            if (recoverGraphToVBean) {
                DebugMessage("ADD_DATA_TO_GRAPH: remove component that was added insetad of the graph");
                super.remove(0);
                DebugMessage("ADD_DATA_TO_GRAPH: add graph for display");
                super.add(m_graph);
                DebugMessage("ADD_DATA_TO_GRAPH: reset flag");
                recoverGraphToVBean = false;
            }
            m_graph.setVisible(true);
            m_graph.setTabularData(al);
            // m_graph.setLocalRelationalData(al);
        }
        DebugMessage("ADD_DATA_TO_GRAPH: finished");
        return true;
    }

    /**
     * Forms proerty: ALIGN_TITLE_TEXT
     * @param sParams
     * @return
     */
    private boolean alignTitleText(String sParams) {
        String _sObject = sParams;
        _sObject = handleTokenNullvaluesInStartAndEnd(_sObject);

        StringTokenizer st = new StringTokenizer(_sObject, sDelimiter);
        int tokenLength = st.countTokens();
        String firstToken = "", secondToken = "", thirdToken = "";

        DebugMessage("Property ALIGN_TITLE_TEXT has received " + tokenLength +
                     " tokens and a value of " + sParams);

        if (tokenLength > 0) {
            firstToken = (String)st.nextElement();
            String tValue =
                firstToken.substring(firstToken.toLowerCase().indexOf("title=") +
                                     6);
            DebugMessage("ALIGN_TITLE_TEXT: FIRST TOKEN: " + tValue);
            if ("right".equalsIgnoreCase(tValue)) {
                m_graph.getDataviewTitle().setHorizontalAlignment(AlignRight);
            } else if ("left".equalsIgnoreCase(tValue)) {
                m_graph.getDataviewTitle().setHorizontalAlignment(AlignLeft);
            } else // center
            {
                m_graph.getDataviewTitle().setHorizontalAlignment(AlignCenter);
            }
        }

        if (tokenLength > 1) {
            secondToken = (String)st.nextElement();
            String tValue =
                secondToken.substring(secondToken.toLowerCase().indexOf("subtitle=") +
                                      9);
            DebugMessage("ALIGN_TITLE_TEXT: SECOND TOKEN: " + tValue);

            if ("right".equalsIgnoreCase(tValue)) {
                m_graph.getDataviewSubtitle().setHorizontalAlignment(AlignRight);
            } else if ("left".equalsIgnoreCase(tValue)) {
                m_graph.getDataviewSubtitle().setHorizontalAlignment(AlignLeft);
            } else // center
            {
                m_graph.getDataviewSubtitle().setHorizontalAlignment(AlignCenter);
            }
        }

        if (tokenLength > 2) {
            thirdToken = (String)st.nextElement();
            String tValue =
                thirdToken.substring(thirdToken.toLowerCase().indexOf("footnote=") +
                                     9);
            DebugMessage("ALIGN_TITLE_TEXT: THIRD TOKEN: " + tValue);

            if ("right".equalsIgnoreCase(tValue)) {
                m_graph.getDataviewFootnote().setHorizontalAlignment(AlignRight);
            } else if ("left".equalsIgnoreCase(tValue)) {
                m_graph.getDataviewFootnote().setHorizontalAlignment(AlignLeft);
            } else // center
            {
                m_graph.getDataviewFootnote().setHorizontalAlignment(AlignCenter);
            }
        }
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
     * Forms property: MIN_SCALE_Y_AXIS
     * @param sParams
     * @return
     */
    private boolean minScaleYAxis(String sParams) {
        if (sParams.length() == 0) {
            // go read the manual !
            DebugMessage("MIN_SCALE_Y_AXIS, scaling needs a minimum value to be provided. Please refer to the" +
                         " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    m_graph.getY1Axis().setAxisMinAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int minValue = 0;
                    minValue = new Double(sParams).intValue();
                    m_graph.getY1Axis().setAxisMinAutoScaled(false);
                    m_graph.getY1Axis().setAxisMinValue(minValue);
                }
            } catch (NumberFormatException nfe) {
                DebugMessage("MIN_SCALE_Y_AXIS: Not a valid integer format passed");
                DebugMessage(nfe.getMessage());
            }
        }
        return true;
    }

    /**
     * Forms property: MAX_SCALE_Y_AXIS
     * @param sParams
     * @return
     */
    private boolean maxScaleYAxis(String sParams) {
        if (sParams.length() == 0) {
            // go read the manual !
            DebugMessage("MAX_SCALE_Y_AXIS, scaling needs a maximum value to be provided. Please refer to the" +
                         " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    m_graph.getY1Axis().setAxisMaxAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int maxValue = 0;
                    maxValue = new Double(sParams).intValue();
                    m_graph.getY1Axis().setAxisMaxAutoScaled(false);
                    m_graph.getY1Axis().setAxisMaxValue(maxValue);
                }
            } catch (NumberFormatException nfe) {
                DebugMessage("MAX_SCALE_Y_AXIS: Not a valid integer format passed");
                DebugMessage(nfe.getMessage());
            }
        }
        return true;
    }

    /**
     * Forms property: MIN_SCALE_Y2_AXIS
     * @param sParams
     * @return
     */
    private boolean minScaleY2Axis(String sParams) {
        if (sParams.length() == 0) {
            // go read the manual !
            DebugMessage("MIN_SCALE_Y2_AXIS, scaling needs a minimum value to be provided. Please refer to the" +
                         " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    m_graph.getY2Axis().setAxisMinAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int minValue = 0;
                    minValue = new Double(sParams).intValue();
                    m_graph.getY2Axis().setAxisMinAutoScaled(false);
                    m_graph.getY2Axis().setAxisMinValue(minValue);
                }
            } catch (NumberFormatException nfe) {
                DebugMessage("MIN_SCALE_Y2_AXIS: Not a valid integer format passed");
                DebugMessage(nfe.getMessage());
            }
        }
        return true;
    }

    /**
     * Forms property: MAX_SCALE_Y2_AXIS
     * @param sParams
     * @return
     */
    private boolean maxScaleY2Axis(String sParams) {
        if (sParams.length() == 0) {
            // go read the manual !
            DebugMessage("MAX_SCALE_Y2_AXIS, scaling needs a maximum value to be provided. Please refer to the" +
                         " documentation on how to use this property.");
        } else {
            // exceptions may be raised on the way, so we prep for it
            try {
                // check if auto scaling should be enabled
                if ("AUTO".equalsIgnoreCase(sParams)) {
                    m_graph.getY2Axis().setAxisMaxAutoScaled(true);
                }
                // try to set fixed scale
                else {
                    int maxValue = 0;
                    maxValue = new Double(sParams).intValue();
                    m_graph.getY2Axis().setAxisMaxAutoScaled(false);
                    m_graph.getY2Axis().setAxisMaxValue(maxValue);
                }
            } catch (NumberFormatException nfe) {
                DebugMessage("MAX_SCALE_Y2_AXIS: Not a valid integer format passed");
                DebugMessage(nfe.getMessage());
            }
        }
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
     * Forms property: ENABLE_TOOLTIPS
     * @param sParams
     * @return
     */
    private boolean enableTooltips(String sParams) {
        if (!sParams.equals("")) {
            if ("ALL".equalsIgnoreCase(sParams)) {
                // this is the default setting and you don't have to do
                // anything for this

                DebugMessage("ENABLE_TOOLTIPS: setting all tooltips");
                m_graph.setMarkerTooltipType(Graph.MTT_VALUES_TEXT);
            }
            if ("LABELS".equalsIgnoreCase(sParams)) {
                DebugMessage("ENABLE_TOOLTIPS: setting label tooltips");
                m_graph.setMarkerTooltipType(Graph.MTT_TEXT);

            } else if ("VALUES".equalsIgnoreCase(sParams)) {
                DebugMessage("ENABLE_TOOLTIPS: setting value tooltips");
                m_graph.setMarkerTooltipType(Graph.TLT_MEMBER);
            } else if ("NONE".equalsIgnoreCase(sParams)) {
                DebugMessage("ENABLE_TOOLTIPS: disabling tooltips");
                m_graph.setMarkerTooltipType(Graph.TLT_NONE);
            } else {
                DebugMessage("ENABLE_TOOLTIPS: No valid attribute passed. Use ALL, LABELS,VALUES or NONE");
            }

        } else {
            DebugMessage("ENABLE_TOOLTIPS: No attribute value passed, thus ignoring");
        }
        return true;
    }

    /**
     * Moves the selected slice number away from the rest
     * Forms property: EXPLODE_PIESLICE
     * @param sParams
     * @return
     */
    private boolean explodePieSlice(String sParams) {
        // _object contains a delimited String containing values, where
        // the second value must be between 0 and 100. The first should not exceed
        // the number of graph columns available or be negative

        if (sParams.length() == 0) {
            // go read the manual !
            DebugMessage("EXPLODE_PIESLICE requires attribute passed. please refer to the" +
                         " documentation on how to use this property.");
        } else {
            // some exceptions may be raised on the way, so we prep for it
            try {
                StringTokenizer st = new StringTokenizer(sParams, sDelimiter);
                String[] val = new String[2];
                int tokenLength = st.countTokens();
                if (tokenLength != 2) {
                    // go read the manual !
                    DebugMessage("EXPLODE_PIESLICE requires attribute passed. please refer to the" +
                                 " documentation on how to use this property.");
                } else {
                    val[0] = (String)st.nextElement();
                    val[1] = (String)st.nextElement();

                    DebugMessage("EXPLODE_PIESLICE: Attributes passed: " +
                                 sParams);
                    DebugMessage("EXPLODE_PIESLICE: After untokenizing String: series=" +
                                 val[0] + " and explode rate=" + val[1]);

                    int Series = 0;
                    int ExpRate = 0;

                    Series = (new Double(val[0])).intValue();
                    ExpRate = (new Double(val[1])).intValue();

                    // if you get here the there wasn't any numeric exceptions check if series is below or over Graph range and adjust if necessary
                    Series =
                            (Series < 0 ? 0 : Series > (m_graph.getSeriesObjectCount() -
                                                        1) ?
                                              (m_graph.getSeriesObjectCount() -
                                               1) : Series);
                    // check explode rate between 0 and 100, if not set
                    // to edge values
                    ExpRate =
                            (ExpRate < 0 ? 0 : (ExpRate > 100 ? 100 : ExpRate));

                    // graphSeries = new Series((CommonGraph)m_graph);
                    // graphSeries.setPieSliceExplode(ExpRate,Series);
                    m_graph.getSeries().setPieSliceExplode(ExpRate, Series);
                }
            } catch (NumberFormatException nfe) {
                DebugMessage("EXPLODE_PIESLICE: Not a valid integer format passed");
                DebugMessage(nfe.getMessage());
            }

            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
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
     * Forms property: MOUSE_ACTION
     * @param sParams
     * @return
     */
    private boolean mouseAction(String sParams) {
        if ("TRUE".equalsIgnoreCase(sParams)) {
            /*
			 * only one instance of the mouse view listsner is allowed
			 * for this object. If there exists an instance of this
			 * listener type, then all other registration attempts are
			 * ignored
			 */

            if (mViewListenerCount == 0) {
                instanceVMListener =
                        new mViewMouseListener(m_graph, this, lrd);
                DebugMessage("MOUSE_ACTION: Adding mouse listener");
                m_graph.addViewMouseListener(instanceVMListener);
                ++mViewListenerCount;
            }
        } else if ("FALSE".equalsIgnoreCase(sParams)) {
            if (mViewListenerCount != 0) {
                DebugMessage("MOUSE_ACTION: Removing mouse listener");
                m_graph.removeViewMouseListener(instanceVMListener);
                mViewListenerCount = 0;
            }
        } else {
            // ignore
            DebugMessage("Property MOUSE_ACTION: " + sParams +
                         " passed but TRUE or FALSE required. Ignoring command!");
        }
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
     * Forms property: POSITION_LEGEND
     * @param sParams
     * @return
     */
    private boolean positionLegendArea(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("POSITION_LEGEND: position legend to " + sParams);
            if ("AUTO".equalsIgnoreCase(sParams)) {
                LegendArea legend = m_graph.getLegendArea();
                legend.setAutomaticPlacement(BaseGraphComponent.AP_ALWAYS);
            } else {
                if ("LEFT".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = m_graph.getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the left edge
                    legend.setPosition(BaseGraphComponent.LAP_LEFT);
                } else if ("RIGHT".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = m_graph.getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the right edge
                    legend.setPosition(BaseGraphComponent.LAP_RIGHT);
                } else if ("TOP".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = m_graph.getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the top edge
                    legend.setPosition(BaseGraphComponent.LAP_TOP);
                } else if ("BOTTOM".equalsIgnoreCase(sParams)) {
                    // first, turn off automatic placement
                    LegendArea legend = m_graph.getLegendArea();
                    legend.setAutomaticPlacement(BaseGraphComponent.AP_NEVER);
                    // set the position to the bottom edge
                    legend.setPosition(BaseGraphComponent.LAP_BOTTOM);

                } else {
                    DebugMessage("POSITION_LEGEND: value " + sParams +
                                 " passed does not represent a valid argument");
                }
            }
        } else {
            DebugMessage("POSITION_LEGEND: No value passed. Pass LEFT,TOP,BOTTOM,RIGHT,AUTO as an argument when calling set_custom_property()");
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
     * Forms property: ROTATE_X_LABEL
     * @param sParams
     * @return
     */
    private boolean rotateXLabel(String sParams) {
        if (!sParams.equals("")) {
            String RotationAngle = sParams;
            DebugMessage("ROTATE_X_LABEL: Trying to rotate the X Axis Tick Label " +
                         sParams);
            if ("90".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 90
                m_graph.getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                m_graph.getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ_ROTATE_90);
                DebugMessage("ROTATE_X_LABEL: Successful - " + sParams);
            } else if ("270".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 270
                m_graph.getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                m_graph.getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ_ROTATE_270);
            } else if ("0".equalsIgnoreCase(RotationAngle)) {
                // Rotate the X Axis Tick Label to 0
                m_graph.getO1TickLabel().setAutomaticRotation(BaseGraphComponent.AR_NO_ROTATE);
                m_graph.getO1TickLabel().setTextRotation(BaseGraphComponent.TR_HORIZ);
            } else {
                DebugMessage("ROTATE_X_LABEL: No valid values for Tick Label Rotation. Expected 0,90,270");
            }
        } else {
            DebugMessage("ROTATE_X_LABEL: No valid values for Tick Label Rotation. Expected 0,90,270");
        }
        return true;
    }

    /**
     * Forms property: SCROLLBAR
     * @param sParams
     * @return
     */
    private boolean scrollBar(String sParams) {
        if (!sParams.equals("")) {
            DebugMessage("SCROLLBAR: setting value to " + sParams);
            if ("TRUE".equalsIgnoreCase(sParams)) {
                m_graph.setScrollbarPresenceGroups(Graph.SP_AS_NEEDED);
            } else if ("FALSE".equalsIgnoreCase(sParams)) {
                // disabling scrollbars
                m_graph.setScrollbarPresenceGroups(Graph.SP_NEVER);
            } else {
                DebugMessage("SCROLLBAR: value " + sParams +
                             " does not contain TRUE or FALSE");
            }

        } else {
            DebugMessage("SCROLLBAR: no value associated with command");
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
                        brdC = colorCodeRegistry.getColorCode(brdColor);
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
                        bgC = colorCodeRegistry.getColorCode(bgColor);
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
            Color col = colorCodeRegistry.getColorCode(sParams);
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
            Color col = colorCodeRegistry.getColorCode(sParams);
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
            Color col = colorCodeRegistry.getColorCode(sParams);
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
            Color col = colorCodeRegistry.getColorCode(sParams);
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

                Color col = colorCodeRegistry.getColorCode(strColor);
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
            Color col = colorCodeRegistry.getColorCode(sParams);
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
            m_graph.getDataviewTitle().setHorizontalAlignment(AlignCenter);
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
            m_graph.getDataviewSubtitle().setHorizontalAlignment(AlignCenter);
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
            m_graph.getDataviewFootnote().setHorizontalAlignment(AlignCenter);
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
     * Forms property: SHOW_LABELS
     * @param sParams
     * @return
     */
    private boolean showLabels(String sParams) {
        String _sObject = sParams;
        _sObject = handleTokenNullvaluesInStartAndEnd(_sObject);
        StringTokenizer st = new StringTokenizer(_sObject, sDelimiter);
        int tokenLength = st.countTokens();
        String firstToken = "", secondToken = "";

        DebugMessage("SHOW_LABELS has received " + tokenLength +
                     " tokens and a value of " + sParams);

        if (tokenLength > 0) {
            firstToken = (String)st.nextElement();
            String txValue =
                firstToken.substring(firstToken.indexOf("x=") + 2);
            DebugMessage("SHOW_LABELS: FIRST TOKEN: " + txValue);
            m_graph.getX1Axis().setVisible(txValue.equalsIgnoreCase("FALSE") ?
                                           false : true);
        }
        if (tokenLength > 1) {
            secondToken = (String)st.nextElement();
            String ty1Value =
                secondToken.substring(secondToken.indexOf("y1=") + 3);
            DebugMessage("SHOW_LABELS: SECOND TOKEN: " + ty1Value);
            m_graph.getY1Axis().setVisible(ty1Value.equalsIgnoreCase("FALSE") ?
                                           false : true);
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
     * Forms property: HIDE_AXIS
     * @param sParams
     * @return
     */
    private boolean hideAxis(String sParams) {
        if (sParams.length() > 0) {
            boolean X1 = true;
            boolean Y1 = true;
            boolean Y2 = true;
            //boolean bIsVisible;
            int iTickStyle = -1;

            // check if argument passed contains X, Y1 or Y2
            String args = sParams == null ? "" : sParams.toUpperCase();
            if (args.indexOf("X") > -1) {
                X1 = false;
            } else {
                X1 = true;
            }
            if (args.indexOf("Y1") > -1) {
                Y1 = false;
            } else {
                Y1 = true;
            }
            if (args.indexOf("Y2") > -1) {
                Y2 = false;
            } else {
                Y2 = true;
            }

            iTickStyle = m_graph.getY1MajorTick().getTickStyle();
            //bIsVisible = m_graph.getY1MajorTick().isVisible(); // Deprecated
            m_graph.getY1Axis().setVisible(Y1);
            //m_graph.getY1MajorTick().setVisible(bIsVisible); // Deprecated
            m_graph.getY1MajorTick().setTickStyle(iTickStyle);

            iTickStyle = m_graph.getY2MajorTick().getTickStyle();
            //bIsVisible = m_graph.getY2MajorTick().isVisible(); // Deprecated
            m_graph.getY2Axis().setVisible(Y2);
            //m_graph.getY2MajorTick().setVisible(bIsVisible); // Deprecated
            m_graph.getY2MajorTick().setTickStyle(iTickStyle);

            iTickStyle = m_graph.getO1MajorTick().getTickStyle();
            //bIsVisible = m_graph.getO1MajorTick().isVisible(); // Deprecated
            m_graph.getO1Axis().setVisible(X1);
            //m_graph.getO1MajorTick().setVisible(bIsVisible); // Deprecated
            m_graph.getO1MajorTick().setTickStyle(iTickStyle);
        } else {
            DebugMessage("HIDE_AXIS: No arguments passed, thus showing all axis labels.");

            // Show all axis labels
            m_graph.getY1Axis().setVisible(true);
            m_graph.getY2Axis().setVisible(true);
            m_graph.getO1Axis().setVisible(true);
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
     * Forms property: SET_SERIES_MARKER_TYPE
     * @param sParams
     * @return
     */
    private boolean setSeriesMarkerType(String sParams) {
        if (sParams.length() > 0) {
            // separate series name from string
            StringTokenizer st = new StringTokenizer(sParams, this.sDelimiter);
            // at least two arguments must be found
            if (st.countTokens() >= 2) {
                String seriesType =
                    sParams.substring(sParams.indexOf(this.sDelimiter) + 1);
                String seriesName =
                    sParams.substring(0, sParams.indexOf(this.sDelimiter)).trim();

                int series_type;
                if ("MT_AREA".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_AREA;
                else if ("MT_BAR".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_BAR;
                else if ("MT_MARKER".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_MARKER;
                else if ("MT_DEFAULT".equalsIgnoreCase(seriesType))
                    series_type = BaseGraphComponent.MT_DEFAULT;
                else {
                    DebugMessage("Property SET_SERIES_MARKER_TYPE: " +
                                 sParams +
                                 " does not contain a valid marker type");
                    return true;
                }

                int series_count = 0;

                try {
                    series_count = m_graph.getColumnCount();
                } catch (EdgeOutOfRangeException ex) {
                    DebugMessage("SET_SERIES_MARKER_TYPE: " + ex);
                    return true;
                }

                for (int i = 0; i < series_count; i++) {
                    String shortLabel = "";
                    try {
                        shortLabel =
                                (String)m_graph.getDataAccessSliceLabel(DataDirector.COLUMN_EDGE,
                                                                        i,
                                                                        MetadataMap.METADATA_LONGLABEL);
                    } catch (SliceOutOfRangeException ex) {
                        DebugMessage("SET_SERIES_MARKER_TYPE: " + ex);
                        return true;
                    } catch (EdgeOutOfRangeException ex) {
                        DebugMessage("SET_SERIES_MARKER_TYPE: " + ex);
                        return true;
                    }
                    if (seriesName.equalsIgnoreCase(shortLabel.trim())) {
                        try {
                            m_graph.getSeries().setMarkerType(series_type, i);
                            DebugMessage("Property SET_SERIES_MARKER_TYPE: Series '" +
                                         seriesName + "' found");
                            break;
                        } catch (SeriesOutOfRangeException sour) {
                            DebugMessage("Property SET_SERIES_MARKER_TYPE: Series out of range exception");
                            return true;
                        }
                    }
                }
                return true;
            } else {
                DebugMessage("SET_SERIES_MARKER_TYPE: not enough arguments specified in " +
                             sParams);
                return true;
            }
        } else {
            DebugMessage("SET_SERIES_MARKER_TYPE: no argument specified");
            return true;
        }
    }

    /**
     * Forms property: SET_SCALED_LOGARITHMIC
     * @param sParams
     * @return
     */
    private boolean setScaledLogarithmic(String sParams) {
        if (!sParams.equals("")) {
            sParams = handleTokenNullvaluesInStartAndEnd(sParams);
            StringTokenizer st = new StringTokenizer(sParams, sDelimiter);
            int tokenLength = st.countTokens();
            String axis = "";
            String enable = "false";
            double base = 0; // unrealistic initial value

            // parse string of arguments
            for (int i = 0; i < tokenLength && i < 3; i++) {
                switch (i) {
                    // axis is Y or X
                case 0:
                    axis = (String)st.nextElement();
                    break;
                    // enable id true or false
                case 1:
                    enable = (String)st.nextElement();
                    break;
                    // value is double or nothing
                case 2:
                    try {
                        base =
(new Double((String)st.nextElement())).doubleValue();
                    } catch (NumberFormatException nfe) {
                        DebugMessage("SET_SCALED_LOGARITHMIC: argument specified as base cannot be parsed to double! Argument ignored");
                    }
                    break;
                default:
                    break;
                }
            }

            // set axis values
            if ("Y".equalsIgnoreCase(axis)) {
                if ("FALSE".equalsIgnoreCase(enable)) {
                    DebugMessage("SET_SCALED_LOGARITHMIC: disabling logarithmic scale for Y axis");
                    m_graph.getY1Axis().setScaledLogarithmic(false);
                } else {
                    DebugMessage("SET_SCALED_LOGARITHMIC: enabling logarithmic scale for Y axis");
                    m_graph.getY1Axis().setScaledLogarithmic(true);
                    if (base != 0) {
                        DebugMessage("SET_SCALED_LOGARITHMIC: setting base to " +
                                     base);
                        m_graph.getY1Axis().setLogarithmicBase(base);
                    }
                }
            } else if ("X".equalsIgnoreCase(axis)) {
                if ("FALSE".equalsIgnoreCase(enable)) {
                    DebugMessage("SET_SCALED_LOGARITHMIC: disabling logarithmic scale for X axis");
                    m_graph.getX1Axis().setScaledLogarithmic(false);
                } else {
                    DebugMessage("SET_SCALED_LOGARITHMIC: enabling logarithmic scale for X axis");
                    m_graph.getX1Axis().setScaledLogarithmic(true);
                    if (base != 0) {
                        DebugMessage("SET_SCALED_LOGARITHMIC: setting base to " +
                                     base);
                        m_graph.getX1Axis().setLogarithmicBase(base);
                    }
                }
            } else {
                DebugMessage("SET_SCALED_LOGARITHMIC: not a valid Axis identifier!");
            }

        } else {
            DebugMessage("SET_SCALED_LOGARITHMIC: no arguments passed with call to set property. Statement ignored!");
        }
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
     * Forms property: MOUSE_ACTIVE
     * @param sParams
     * @return
     */
    private boolean mouseActive(String sParams) {
        if (!sParams.equals("")) {
            bMouseActive = sParams.equalsIgnoreCase("FALSE") ? false : true;
            glass.setVisible(!bMouseActive);
            DebugMessage("MOUSE_ACTIVE: " + bMouseActive);
        }
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
     * Forms property: SET_SERIES_Y_AXIS
     * @param sParams
     * @return
     */
    private boolean setSeriesYAxis(String sParams) {
        if (sParams.length() > 0) {
            try {
                // separate series name from string
                StringTokenizer st =
                    new StringTokenizer(sParams, this.sDelimiter);
                // at least two arguments must be found
                if (st.countTokens() >= 2) {
                    String seriesYAxis =
                        sParams.substring(sParams.indexOf(this.sDelimiter) +
                                          1);
                    String seriesName =
                        sParams.substring(0, sParams.indexOf(this.sDelimiter)).trim();
                    int series_count = m_graph.getColumnCount();
                    DebugMessage("SET_SERIES_Y_AXIS: Looking for a series named '" +
                                 seriesName + "'...");
                    for (int i = 0; i < series_count; i++) {
                        String shortLabel =
                            (String)m_graph.getDataAccessSliceLabel(DataDirector.COLUMN_EDGE,
                                                                    i,
                                                                    MetadataMap.METADATA_LONGLABEL);
                        if (seriesName.equalsIgnoreCase(shortLabel.trim())) {
                            // m_graph.getSeries().setAssignedToY2(!m_graph.getSeries().isAssignedToY2(i),i);
                            m_graph.getSeries().setAssignedToY2("Y2".equalsIgnoreCase(seriesYAxis),
                                                                i);
                            DebugMessage("SET_SERIES_Y_AXIS: Series '" +
                                         seriesName + "' found");
                            break;
                        }
                    }
                    return true;
                } else {
                    DebugMessage("SET_SERIES_Y_AXIS: not enough arguments specified in " +
                                 sParams);
                    return true;
                }
            } catch (EdgeOutOfRangeException ex) {
                DebugMessage("SET_SERIES_Y_AXIS: " + ex);
                return true;
            } catch (SeriesOutOfRangeException ex) {
                DebugMessage("SET_SERIES_Y_AXIS: " + ex);
                return true;
            } catch (SliceOutOfRangeException ex) {
                DebugMessage("SET_SERIES_Y_AXIS: " + ex);
                return true;
            }
        } else {
            DebugMessage("SET_SERIES_Y_AXIS: no argument specified");
            return true;
        }
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
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(this,
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
            iGraphType = graphTypeRegistry.getTypeForString((String)type);

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

    protected void DebugMessage(String dm) {
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
                fontCol =
                        colorCodeRegistry.getColorCode((String)tokens.nextElement());
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

    private String handleTokenNullvaluesInStartAndEnd(String in) {
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
    private void noGraphDataFoundHandler() {
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
     * Methods for transferring image to clipboard
     */
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { DataFlavor.imageFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor))
            throw new UnsupportedFlavorException(flavor);

        if (flavor == DataFlavor.imageFlavor) {
            // Capture exported image in a byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            m_graph.exportToPNG(out);

            // Wait until the rendering completed
            Image image =
                Toolkit.getDefaultToolkit().createImage(out.toByteArray());
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(image, 1);
            try {
                tracker.waitForAll();
            } catch (InterruptedException ex) {
            }

            // Begin: Add title to image
            String sTitle = m_graph.getDataviewTitle().getText();

            if (sTitle == null || sTitle.equals("")) {
                DebugMessage("Graph title is empty, skipping...");
                return image;
            }

            DebugMessage("Graph title: '" + sTitle + "'");

            Font font = m_graph.getDataviewTitle().getFont();
            FontMetrics fm = m_graph.getFontMetrics(font);
            int iStringWidth = fm.stringWidth(sTitle);
            int iStringHeight = fm.getHeight();
            int iImageWidth = m_graph.getParent().getWidth();
            int iImageHeight = m_graph.getParent().getHeight();
            DebugMessage("iImageWidth=" + iImageWidth + " iImageHeight=" +
                         iImageHeight + " iStringWidth=" + iStringWidth +
                         " iStringHeight=" + iStringHeight);
            iStringHeight += 2; // Add some space between title and image

            String[] aTitle = sTitle.split("\n");

            // Create a new image with room for title and image
            BufferedImage newImage =
                new BufferedImage(iImageWidth, iImageHeight +
                                  (iStringHeight * aTitle.length),
                                  BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();

            // Draw the screenshot in the new image
            g.drawImage(image, 0, iStringHeight * aTitle.length, null);

            // Fill the title area with white background
            g.setColor(Color.white);
            g.fillRect(0, 0, iImageWidth, iStringHeight * aTitle.length);

            // Draw the title in the title area
            g.setColor(Color.black);
            g.setFont(font);

            for (int i = 0; i < aTitle.length; i++) {
                iStringWidth = fm.stringWidth(aTitle[i]);
                g.drawString(aTitle[i], iImageWidth / 2 - iStringWidth / 2,
                             (iStringHeight * (i + 1)) - 2);
            }

            g.dispose();
            // End: Add title to image

            return newImage;
        }
        return null;
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
}

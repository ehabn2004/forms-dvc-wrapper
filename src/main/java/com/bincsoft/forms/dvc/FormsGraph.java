package com.bincsoft.forms.dvc;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.properties.formsgraph.FormsGraphProperty;
import com.bincsoft.forms.dvc.properties.formsgraph.ResetGraph;
import com.bincsoft.forms.dvc.properties.formsgraph.ReturnValues;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import oracle.dss.dataView.ViewMouseEvent;
import oracle.dss.dataView.ViewMouseMotionListener;
import oracle.dss.graph.Graph;
import oracle.dss.util.DefaultErrorHandler;

import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;


/**
 * Wrapper class for oracle.dss.graph.Graph which enables graphs in Oracle Forms.
 *
 * <p>Many Oracle Forms applications require a graphical representation of data stored in a Forms
 * table or the database. So far Oracle Graphics has been used and still can be used to render
 * interactive graphs in Oracle Forms applications. For each Forms user process, Oracle Graphics
 * runs in its own separate process on the middle tier server. Many customers regard a separate
 * process for embedded charts too much an overhead and look for an alternative solution to Oracle
 * Graphics.</p>
 *
 * <p>Oracle Forms Services provide a runtime environment for Java beans that allows Forms developers
 * to add missing or extra functionality built in Java to their Web applications. With the
 * BI Bean Graph Oracle also provides a powerful set of Java classes that can be used to render many
 * types of charts when run in a Java environment. Because the BI Bean became a strategic product at
 * Oracle, Oracle products like OracleAS Reports, OracleAS Discoverer Oracle JDeveloper and
 * OracleAS Portal use the BI Bean Graph for their users to create graphical displays.</p>
 *
 * <p>To make the BI Bean Graph work in OracleAS Forms Services applications, all that is required is
 * a Bean wrapper to work within the Forms Bean Container. The wrapper can be built dynamically using
 * the Forms Bean Manager or manually coded using Java. The FormsGraph component is a generic wrapper
 * built in Java that exposes a set of functionality and properties provided within the BI Bean Graph
 * to Forms. Using this wrapper allows customers to use the BI Bean Graph within their own applications,
 * not requiring any additional line of Java code. The demo application to this help file contains an
 * example implementation of the generic BI Bean Graph wrapper.</p>
 *
 * <p>This sample works with Oracle Forms and was not tested with Forms6i.</p>
 * <b>How does the bean work</b>
 *
 * <p>The FormsGraph class instantiates the BI Bean Graph when added to the implementation class
 * property of a Bean Container in Forms. Data is added to the graph by Forms calls to
 * {@code SET_CUSTOM_PROPERTY('BeanContainer',1,ADD_ROWDATA.'<column_label,row_label,value>')}. The data
 * is passed in a character delimited string, where the default value defined as a delimiter is the
 * comma. The delimiter can, and sometimes must, be changed by a call to
 * {@code SET_CUSTOM_PROPERTY('BeanContainer',1, 'SET_DELIMITER','<new_char>')}.</p>
 * <ul>
 * <li>column label - The name of the data group, like salary, sales or home runs hit. The column
 * label by default shows in the graph's legend</li>
 * <li>row label - The row label, in combination with the column label, defines the the cell for the
 * data value. For the salary column this can be employee names and for the sales column the name
 * of departments</li>
 * <li>value - The value is defined as double 00.00. Though the value is passed as a string it gets
 * converted to double within the FormsGraph wrapper.</li>
 * </ul>
 *
 * <p>ADD_ROWDATA is a registered property in the FormsGraph wrapper class that creates one relational
 * row with each calls. This property can be called many times and the data is stored within the
 * FormsGraph instance until it is explicitly deleted by a call to
 * {@code SET_CUSTOM_PROPERTY('BeanContainer',1, 'CLEAR_GRAPH',)}. Note that even if no argument is
 * required when setting a property it still needs to be passed in Forms.</p>
 *
 * <p>The following code uses a cursor to create rows in the graph for two columns,
 * 'Sales Total' and 'Sales Tax'</p>
 * <pre>{@code
 * ...
 * OPEN PopulateGraphWithData;
 *   FETCH PopulateGraphWithData INTO vName, nOrder, vDate, nTotalValue, nTaxValue;
 *   while (PopulateGraphWithData%FOUND) LOOP
 *       vData :='Sales Total '||vDelimiter||vDate||vDelimiter||nTotalValue;
 *       SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'ADD_ROWDATA',vData);
 *       vData :='Sales Tax '||vDelimiter||vDate||vDelimiter||nTaxValue;
 *       SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'ADD_ROWDATA',vData);
 *       FETCH PopulateGraphWithData INTO vName, nOrder, vDate, nTotalValue, nTaxValue;
 *   END LOOP;
 *   SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'ADD_DATA_TO_GRAPH',);
 * CLOSE PopulateGraphWithData;
 * ...
 * }</pre>
 * <p>The graph is shown in Forms after a call to
 * {@code SET_CUSTOM_PROPERTY('BeanContainer',1,'ADD_DATA_TO_GRAPH',)}.</p>
 *
 * <p>The FormsGraph bean can be used for static charts and for drill down charts alike. The call
 * to ADD_DATAROW can optionally have a forth argument, representing the primary key used for the
 * drill down or drill across operation. Drill actions are handled by the same bean but in another
 * Forms Bean Container. This ensures that the control over the operation is left to the Forms
 * application developer.</p>
 * <b>Building a simple graph</b>
 *
 * <p>A simple graph is a graph that does not have any drill down or drill across actions. To create
 * a graph in Forms, you first need to place a Bean Container to the Forms canvas hosting the graph.
 * Open the bean container's property palette and edit the value of the 'Implementation class' property
 * to oracle.forms.demos.bigraph. FormsGraph. This instantiates the FormsGraph bean.</p>
 *
 * <p>The graph is not shown until the registered property ADD_DATA_TO_GRAPH is called using the
 * Forms SET_CUSTOM_PROPERTY() built-in. Calling this property before adding data to the graph
 * internal data store leaves the graph empty. Use the ADD_ROWDATA property to pass data to the
 * graph bean, again using the SET_CUSTOM_ PROPERTY() built-in. The data rendered in the BI Bean graph
 * is queried by and passed from Forms, which means that the Forms developer always in control.
 * The ADD_ROWDATA property can be called as many times as needed, even after calling the
 * ADD_DATA_TO_GRAPH property. To create a new graph, exposing new data, the CLEAR_GRAPH needs to be
 * called before adding new data with additional calls to ADD_ROWDATA.</p>
 *
 * <p>Without expecting too much, this is all that needs to be done to create a simply bar graph
 * based on custom data in Forms. All other bean properties exposed through the FormsGraph wrapper
 * class can be used for additional graph modification.</p>
 * <b>HowTo master-detail</b>
 *
 * <p>A master-detail behavior in the graph is made of two bean containers, each running an instance
 * of oracle.forms.demos.bigraph.FormsGraph . The master-detail behavior is controlled by Forms,
 * where Forms acts like a controller accessed from the master-bean by the WHEN-CUSTOM-ITEM-EVENT
 * trigger attached to the bean container of the master graph in Forms.</p>
 *
 * <p>Assume two Bean Conatiners created on one Forms canvas, "block1.masterBeanCon" and
 * "block1.detailBeanCon". The block1.masterBeanCon component has an attached WHEN-CUSTOM-ITEM-EVENT
 * trigger listening to events raised by the graph bean.</p>
 *
 * <p>Further assuming the block1.masterBeanCon to be that simple graph explained above, the following
 * calls to SET_CUSTOM_PROPERTY() must be performed to have the bean returning a value to the Bean
 * container.</p>
 *
 * <p>{@code SET_CUSTOM_PROPERTY(' block1.masterBeanCon',1,'MOUSEACTION',true);}</p>
 *
 * <p>{@code SET_CUSTOM_PROPERTY('block1.masterBeanCon',1,'RETURN_VALUES_ON_CLICK','PRIMARY_KEY');}</p>
 *
 * <p>For the bean to return the primary key for a graph component that the user clicked on, a fourth
 * argument must be passed when calling the ADD_ROWDATA property.</p>
 *
 * <p>{@code SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'ADD_ROWDATA','Europe,Sales,25000');}</p>
 *
 * <p>renders a row that does not have a primary key. Simply add the primary key as a fourth argument
 * to be used in master-detail behaviors.</p>
 *
 * <p>{@code SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'ADD_ROWDATA','Europe,Sales,25000,12');}</p>
 *
 * <p>If a user clicks onto the bar that represents the sales value 25000 for Europe, then the bean
 * returns the primary key 12, raising a WHEN-CUSTOM-ITEM-EVENT trigger attached to the bean container.
 * The code in the WHEN-CUSTOM-ITEM-EVENT trigger looks like:</p>
 * <pre>{@code
 * DECLARE
 *   eventName varchar2(30) := :system.custom_item_event;
 *   tempString varchar2(100);
 *   eventValues ParamList;
 *   eventValueType number;
 * BEGIN
 *   IF (eventName='GRAPH_ACTION') THEN
 *       eventValues := get_parameter_list(:system.custom_item_event_parameters);
 *       get_parameter_attr(eventValues,'GRAPH_INFO',eventValueType, tempString);
 *       -- clear data stored and shown in block1.detailBeanCon
 *       FORMSGRAPHSAMPLE.clearData('block1.detailBeanCon');
 *       -- call a procedure that populates the detail graph, by repeated calls to
 *       -- ADD_ROWDATA and one to ADD_DATA_TO_GRAPH
 *       FORMSGRAPHSAMPLE.populateDetailGraphData('block1.detailBeanCon',tempString,',');
 *   ELSE
 *       null;
 *   END IF;
 * END;
 * }</pre>
 *
 * <p>You can debug the graph bean independently for both bean containers. You can provide a debugging
 * prefix to distinguish the messages written to the Java panel. E.g. to have all messages of the
 * master bean pre-fixed with "block1.master bean:"</p>
 *
 * <p>{@code set_custom_property('block1.masterBeanCon',1,'SET_DEBUG_PREFIX','block1.master bean:');}</p>
 *
 * <p>This creates a simple master-detail behavior controlled by Forms.</p>
 * <b>Forms implementation</b>
 * <ol>
 * <li>Open a Forms canvas and add a Forms Bean container to it</li>
 * <li>Open the Bean container property palette and add oracle.forms.demos.bigraph.FormsGraph
 * to the implementation class property field</li>
 *  <li>Set the bean properties exposed by the FormsGraph wrapper using the SET_CUSTOM_PROPERTY()
 *  built-in</li>
 * </ol>
 * <b>Deployment</b>
 * <ol>
 * <li>Add the FormsGraph.jar file to the application definition in the formsweb.cfg file.
 * The FormsGraph.jar file contains all BI Bean Graph classes and the FormsGraph wrapper class used.
 * The file size is 1.6 MB and is permanently stored on the client after a first time download.</li>
 * <li>Copy the FormsGraph.jar file into the forms90/java directory of the Forms Services installation</li>
 * <li>Set imagebase=codebase if you want to use graph icons, provided in the FormsGraph.jar file,
 * within your application</li>
 * </ol>
 * <b>Example formsweb.cfg entry:</b>
 * <pre>{@code
 * [BIGraph]
 * ...
 * imagebase=codebase
 * ...
 * archive_jini=f90all_jinit.jar,/formsdemo/jars/FormsGraph.jar,/formsdemo/jars/demo90.jar
 * ...
 * }</pre>
 *
 * <b>Properties registered in the FormsGraph wrapper</b>
 *
 * <p>Properties are named identifiers that map to a specific functionality in the BI Bean Graph
 * component. Properties are called by the SET_CUSTOM_PROPERTY() built-in and may or may not require
 * additional arguments to be passed.</p>
 *
 * <b>FormsGraph Properties</b>
 * <b>Named colors</b>
 *
 * <p>The following colors can be used by their name and do not require RGB encoding</p>
 *
 * <p>black, blue, cyan, darkGray, gray, green, lightGray, magenta, orange, pink, red, white, yellow</p>
 * <b>Known issues</b>
 *
 * <p>This implementation is treated as a reusable component and Java code is shipped with the
 * Forms demo. The source code can be edited within all projects in JDeveloper that have the
 * BI Bean Graph added. The following is a list of known issues</p>
 * <ul>
 * <li>The Forms Graph overlaps Alerts and lists in Forms. This behavior is caused by heavy weight
 * components (awt classes) in a lightweight interface (Oracle ewt). This may get addressed in the
 * future, but no promises are made to this time. This has been fixed,
 * see {@link com.bincsoft.forms.dvc.properties.formsgraph.AddDataToGraph#screenshotSolution}</li>
 * <li>For interactive graphs to work, the combination of column label, row label and value must be
 * unique. This is required for retrieving the primary key stored within a different Java object.</li>
 * </ul>
 */
public class FormsGraph extends BincsoftBean {
    private Logger log = Logger.getLogger(getClass().getName());
    private Graph graph = null; // Instance of the DVC graph
    private JLabel graphScreenshotWrapper = new JLabel();
    private int mViewListenerCount = 0; // only one view listener shall get implemented
    private int mSeparateFrameXPos = 10; // xPos of the separate Graph frame
    private int mSeparateFrameYPos = 10; // yPos of the separate Graph frame
    private boolean recoverGraphToVBean =
        false; // this flag is set when the Graph component in the Forms Bean component is replaced by a panel due to no data available in the graph
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
    private JInternalFrame internalFrame = null;

    private static final int DEFAULT_GRAPH_TYPE =
        Graph.BAR_VERT_CLUST; // default graph type is a simple vertical bar graph
    private static final String DEFAULT_XML_STYLE = "/com/bincsoft/forms/dvc/style_default.xml";

    /**
     * Custom item events
     */
    protected static final ID GRAPH_INFO = ID.registerProperty("GRAPH_INFO"); // Used when dispatching
    
    protected static final ID SERIES_COUNT = ID.registerProperty("COLUMNCOUNT");
    protected static final ID TEST = ID.registerProperty("TEST");
    protected static final ID RETURNED_COLUMN_NUMBER = ID.registerProperty("RETURNED_COLUMN_NUMBER");
    protected static final ID GRAPH_ACTION = ID.registerProperty("GRAPH_ACTION");

    public FormsGraph() throws ClassNotFoundException {
        super();

        // Instantiate the BI Bean Graph
        graph = new Graph();

        // Set default values
        ResetGraph.setDefaults(this);

        // Disable debug information sent from the graph
        DefaultErrorHandler deh = new DefaultErrorHandler();
        deh.setDebugMode(DefaultErrorHandler.SHOW_NONE);
        graph.addErrorHandler(deh);

        // Instantiate the data store for this graph
        lrd = new LocalRelationalData();

        // Set mouse interaction to on
        instanceVMListener = new GraphViewMouseListener(this);
        mViewListenerCount = 1;

        graph.addViewMouseListener(instanceVMListener);

        // Register all property handlers
        registerProperties(FormsGraphProperty.values());
    }

    /**
     * Implementation of IView interface. Init is called when the bean is first instantiated.
     *
     * @see oracle.forms.ui.IView
     */
    @Override
    public void init(IHandler handler) {
        super.init(handler);

        // Create the glasspane which will stop mouse events
        glass = new GlassPane();

        // Create the internal frame which will house the graph and the glasspane
        internalFrame = new JInternalFrame();
        BasicInternalFrameUI internalFrameUI = (BasicInternalFrameUI)internalFrame.getUI();
        internalFrameUI.setNorthPane(null);
        internalFrame.setBorder(BorderFactory.createEmptyBorder());
        internalFrame.setVisible(true);
        internalFrame.add(graph);
        internalFrame.setGlassPane(glass);
        super.add(internalFrame);

        log.info(String.format("%s version '%s' started, %s version '%s'.", getClass().getName(),
                                         getVersion(), graph.getClass().getName(), graph.getVersion()));
    }

    /**
     * Implementation of IView interface which sets a requested property to a given value
     *
     * @param id  property to be set.
     * @param object value of the property id.
     * @return true if the property could be set, false otherwise.
     * @see oracle.forms.ui.IView
     */
    @Override
    public boolean setProperty(ID id, Object object) {
        String sParams = object == null ? "" : object.toString();

        if (id == TEST) {
            return test(sParams);
        }

        return super.setProperty(id, object);
    }

    /**
     * Handles get actions from Forms
     * @param id
     * @return
     */
    @Override
    public Object getProperty(ID id) {
        if (id == SERIES_COUNT) {
            try {
                // retrieve graph column count for further usage
                log.log(Level.FINE, "SERIES COUNT requested");
                // if rows shown as series then row number = column number
                if (showGraphAsSeries) {
                    return Integer.valueOf(graph.getRowCount());
                } else {
                    return Integer.valueOf(graph.getColumnCount());
                }
            } catch (Exception e) {
                log.log(Level.FINE, "====SERIES COUNT Error====", e);
                log.log(Level.FINE, "returning 0");
                // show 0 if no column can be retrieved
                return Integer.valueOf(0);
            }
        } else {
            return super.getProperty(id);
        }
    }

    /**
     * Used for testing.
     * Forms property: TEST
     * @param sParams
     * @return
     */
    private boolean test(String sParams) {
        log.log(Level.FINE, "TEST");
        // Do some tests here
        log.log(Level.FINE, "TEST: Done!");
        return true;
    }

    public void dispatchMouseAction(String msg) {
        dispatchCustomEvent(GRAPH_INFO, msg, GRAPH_ACTION);
    }

    public void returnSeriesCount(int ret) {
        dispatchCustomEvent(GRAPH_INFO, Integer.toString(ret), RETURNED_COLUMN_NUMBER);
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
        getNoDataFoundPanel().setSelectionColor(graph.getBackground());
        getNoDataFoundPanel().setRequestFocusEnabled(false);
        getNoDataFoundPanel().setVerifyInputWhenFocusTarget(false);
        super.add(getNoDataFoundPanel());
        recoverGraphToVBean = true;
    }

    /**
     * Setters and getters
     */

    public Graph getGraph() {
        return graph;
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
        getInternalFrame().remove(graph);
    }

    public void addGraph() {
        getInternalFrame().add(graph);
    }
    
    public void setGraphScreenshot(BufferedImage image) {
        removeGraph();
        graphScreenshotWrapper.setIcon(new ImageIcon(image));
        getInternalFrame().add(graphScreenshotWrapper);
    }
    
    public void removeGraphScreenshot() {
        getInternalFrame().remove(graphScreenshotWrapper);
        addGraph();
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
        return getGraph().getGraphType();
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

    public JInternalFrame getInternalFrame() {
        return internalFrame;
    }

    public String getDefaultXmlStyle() {
        return DEFAULT_XML_STYLE;
    }

    public int getDefaultGraphType() {
        return DEFAULT_GRAPH_TYPE;
    }
    
    @Override
    public Object getWrappedObject() {
        return getGraph();
    }

    private void addTestData() {
        /*
         * Test Data allowing you to run this Java Bean in Forms without
         * providing Data Forms. use this data for developing new features and
         * functionality or for tracing problems
         */

        lrd.addRelationalDataRow("Europe,Sales,2000,1", getDelimiter());
        lrd.addRelationalDataRow("Europe,Provisions,2000,2", getDelimiter());
        lrd.addRelationalDataRow("USA,Sales,4000,3", getDelimiter());
        lrd.addRelationalDataRow("USA,Provisions,5000,4", getDelimiter());
        lrd.addRelationalDataRow("Europe,Netto,2200.12,5", getDelimiter());
        lrd.addRelationalDataRow("USA,Netto,30000,6", getDelimiter());
        lrd.addRelationalDataRow("USA,Brutto,7000,7", getDelimiter());
        lrd.addRelationalDataRow("Europe,Brutto,5000,8", getDelimiter());

        graph.setTabularData(lrd.getRelationalData());
        graph.setVisible(true);
    }

    private void enableThreeDMouseManipulation() {
        // Enable 3D manipulation using the mouse
        mouseMotionListener = new ViewMouseMotionListener() {
                public void mouseMoved(ViewMouseEvent evt) {
                }

                public void mouseDragged(ViewMouseEvent evt) {
                    graph.setDepthRadius(15);
                    graph.setDepthAngle(evt.getX());
                    graph.setPieDepth(evt.getY());
                    graph.setPieRotation(evt.getX());
                }
            };

        graph.addViewMouseMotionListener(mouseMotionListener);
    }
}

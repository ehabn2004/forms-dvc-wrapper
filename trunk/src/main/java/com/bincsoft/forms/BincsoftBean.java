package com.bincsoft.forms;

import com.bincsoft.forms.properties.FormsProperty;
import com.bincsoft.forms.properties.FormsPropertyHandler;
import com.bincsoft.forms.properties.IFormsProperty;

import java.awt.Frame;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import oracle.forms.engine.Main;
import oracle.forms.handler.IHandler;
import oracle.forms.ui.CustomEvent;
import oracle.forms.ui.VBean;
import oracle.forms.properties.ID;

public class BincsoftBean extends VBean {
    private Logger log = Logger.getLogger(getClass().getName());
    private String sDelimiter = ","; // default delimiter for the data passed to the graph
    protected static final ID DEBUG = ID.registerProperty("DEBUG");
    protected static final ID GET_DELIMITER = ID.registerProperty("GET_DELIMITER");
    protected static final ID RETURN_XML = ID.registerProperty("RETURN_XML");
    protected static final ID PROP_PRINTLN =
        ID.registerProperty("PROP_PRINTLN"); // Property to use for printing to Java console
    protected static final ID PROP_JAVA_VERSION =
        ID.registerProperty("PROP_JAVA_VERSION"); // Property to retreive Java version string
    protected static final ID HDAPP_VERSION =
        ID.registerProperty("HDAPP_VERSION"); // Property to get the hdapp version

    protected static final ID DELIMITER_INFO =
        ID.registerProperty("DELIMITER_INFO"); // Used when dispatching delimiter
    protected static final ID CURRENT_DELIMITER = ID.registerProperty("CURRENT_DELIMITER");

    private Main formsMain = null;
    private Frame formsTopFrame = null;
    private boolean bReturnXml = false;

    private Map<String, IFormsProperty> hmPropertyHandlers = new HashMap<String, IFormsProperty>();

    public BincsoftBean() throws ClassNotFoundException {
        super();

        registerProperties(FormsProperty.values());
    }

    @Override
    public void init(IHandler handler) {
        super.init(handler);
        formsMain = handler.getApplet();
        formsTopFrame = formsMain.getFrame();
    }

    protected String getVersion() {
        return BincsoftBean.class.getPackage().getImplementationVersion();
    }

    public Map<String, IFormsProperty> getPropertyHandlers() {
        return hmPropertyHandlers;
    }

    /**
     * Registers all items in FormsProperty as Forms property handlers.
     * @see FormsProperty
     */
    protected void registerProperties(IFormsProperty[] properties) throws ClassNotFoundException {
        for (IFormsProperty property : properties) {
            registerProperty(property);
        }

        log.log(Level.FINE, String.format("Registered %s property handlers", getPropertyHandlers().size()));
    }

    /**
     * Will try to load the specified handler class so exceptions occur at startup rather than at runtime.
     * @param property
     */
    private void registerProperty(IFormsProperty property) throws ClassNotFoundException {
        ClassLoader cl = BincsoftBean.class.getClassLoader();
        cl.loadClass(property.getHandlerFullyQualifiedName());
        getPropertyHandlers().put(property.getName(), property);
        log.log(Level.FINE,
                String.format("Registering handler '%s' for property '%s'", property.getHandlerFullyQualifiedName(),
                              property.getName()));
        ID.registerProperty(property.getName());
    }

    protected void dispatchCustomEvent(ID eventValuesID, String sValue, ID eventID) {
        try {
            getHandler().setProperty(eventValuesID, sValue);
            CustomEvent ce = new CustomEvent(getHandler(), eventID);
            dispatchCustomEvent(ce);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not dispatch custom event.", e);
        }
    }

    @Override
    public boolean setProperty(ID id, Object object) {
        String sParams = object == null ? "" : object.toString();
        log(sParams);

        if (getPropertyHandlers().containsKey(id.toString())) {
            IFormsProperty property = getPropertyHandlers().get(id.toString());
            log.log(Level.FINE,
                    String.format("Found handler %s for property %s", property.getHandlerFullyQualifiedName(),
                                  id.toString()));
            ClassLoader cl = this.getClass().getClassLoader();
            try {
                Class cls = cl.loadClass(property.getHandlerFullyQualifiedName());
                FormsPropertyHandler prop = (FormsPropertyHandler)cls.newInstance();
                return prop.handleProperty(sParams, this);
            } catch (ClassNotFoundException ex) {
                log.log(Level.SEVERE, String.format("Could not find handler for property: %s", property), ex);
            } catch (InstantiationException ex) {
            	log.log(Level.SEVERE, "Could not instantiate property handler.", ex);
            } catch (IllegalAccessException ex) {
            	log.log(Level.SEVERE, "Could not access property handler.", ex);
            }
        } else if (id == DEBUG) {
            return setDebug(sParams);
        } else if (id == RETURN_XML) {
            return setReturnXml(sParams);
        } else if (id == PROP_PRINTLN) {
            if (object != null) {
                System.out.println((String)object);
            }
            return true;
        } else if (id == GET_DELIMITER) {
            dispatchDelimiter();
            return true;
        } else {
            return super.setProperty(id, object);
        }
        return super.setProperty(id, object);
    }

    @Override
    public Object getProperty(ID id) {
        if (id == GET_DELIMITER) {
            return getDelimiter();
        } else if (id == PROP_JAVA_VERSION) {
            return System.getProperty("java.version"); //System.getProperty("java.vm.version"); // returns incorrect version when running VM 1.6
        } else if (id == HDAPP_VERSION) {
            return getVersion();
        } else {
            return super.getProperty(id);
        }
    }

    /**
     * Gets the underlying object which is wrapped in this class.
     * @return
     */
    public Object getWrappedObject() {
        return null;
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

    protected Main getMain() {
        return formsMain;
    }

    protected Frame getFormsTopFrame() {
        return formsTopFrame;
    }

    private boolean setReturnXml(String sParams) {
        bReturnXml = sParams.equalsIgnoreCase("TRUE");
        return true;
    }

    protected void setReturnXml(boolean b) {
        bReturnXml = b;
    }

    protected boolean isReturningXml() {
        return bReturnXml;
    }

    public void setDebug(boolean b) {
        setDebug(Boolean.toString(b));
    }

    protected boolean setDebug(String sParams) {
        log(sParams);
        if (sParams != null && sParams.equalsIgnoreCase("TRUE")) {
            Level level = Level.ALL;
            String packageName = getClass().getPackage().getName();
            String logPackageName = packageName.substring(0, packageName.indexOf(".", packageName.indexOf(".") + 1));
            log.log(Level.INFO, String.format("Settings logging level '%s' for package '%s'", level, logPackageName));

            ConsoleHandler handler = new ConsoleHandler();
            handler.setLevel(level);
            Logger.getLogger(logPackageName).addHandler(handler);
            Logger.getLogger(logPackageName).setLevel(level);
        }

        if (sParams != null && sParams.equalsIgnoreCase("FALSE")) {
            log.log(Level.INFO, "Resetting log manager");
            LogManager.getLogManager().reset();
        }
        return true;
    }

    public void log(String msg) {
        log(Level.FINE, msg);
    }

    protected void log(Level lvl, String msg) {
        log.log(lvl, String.format("%s - %s", getClass().getName(), msg));
    }

    private void dispatchDelimiter() {
        dispatchCustomEvent(DELIMITER_INFO, getDelimiter(), CURRENT_DELIMITER);
    }
}

package com.bincsoft.forms.properties;


import com.bincsoft.forms.BincsoftBean;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for all Forms property handlers.
 */
public abstract class FormsPropertyHandler {
    private Logger log = Logger.getLogger(getClass().getName());
    
    /**
     * Gets the IFormsProperty from the related enum.
     * @return
     */
    public IFormsProperty getFormsProperty() {
        return FormsProperty.fromString(getClass().getName());
    }
    
    /**
     * Base handler for Forms properties.
     * @param params Parameter value to handle
     * @param bean Instance of BincsoftBean to manipulate
     * @return True if the property should be handled, otherwise false
     */
    public boolean handleProperty(String params, BincsoftBean bean) {
        log(String.format("Handler %s for bean %s received '%s' as parameter.", getClass().getName(), bean.getClass().getName(), params));
        return (params != null && !params.isEmpty());
    }
    
    /**
     * Log a message using a fixed log level.
     * @param msg Message to log
     */
    protected void log(String msg) {
        log(Level.FINE, msg);
    }
    
    /**
     * Log a message using a given log level.
     * @param level The logging level
     * @param msg The message to log
     */
    protected void log(Level level, String msg) {
        log.log(level, String.format("%s(%s) - %s", getFormsProperty().getName(), getClass().getName(), msg));
    }
}

package com.bincsoft.forms;

import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.forms.ui.CustomEvent;
import oracle.forms.ui.VBean;
import oracle.forms.properties.ID;

public class BincsoftBean extends VBean {
    private boolean bDebug = false;
    private Logger logger = Logger.getLogger(getClass().getName());
    protected static final ID DEBUG = ID.registerProperty("DEBUG");

    public BincsoftBean() {
        super();
    }

    protected void dispatchCustomEvent(ID eventValuesID, String sValue, ID eventID) {
        try {
            getHandler().setProperty(eventValuesID, sValue);
            CustomEvent ce = new CustomEvent(getHandler(), eventID);
            dispatchCustomEvent(ce);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void debugMessage(String s) {
        if (bDebug) {
            logger.log(Level.INFO, s);
        }
    }

    protected void debugMessage(Level lvl, String s) {
        logger.log(lvl, s);
    }
}

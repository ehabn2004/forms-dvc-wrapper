package com.bincsoft.forms;

import java.awt.Component;
import java.awt.Container;

import oracle.forms.ui.ExtendedFrame;


/**
 * Utility class providing some useful methods.
 */

public class Utils {
    private static Utils _instance = new Utils();

    private Utils() {}

    public static Utils getInstance() {
        return _instance;
    }

    /**
     * Finds and returns the instance of ExtendedFrame which holds the supplied Component.
     * @param c Instance of Component which owner window needs to be found.
     * @return The ExtendedFrame which holds the Component or null if not found.
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
     * @param frmOwnerFrame Instance of ExtendedFrame.
     * @param comp Instance of Component.
     * @return true/false
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
     * Finds out if the parameter cont owns the parameter comp.
     * @param cont Instance of Container.
     * @param comp Instance of Component.
     * @return true/false
     */
    public boolean isOwnerOf(Container cont, Component comp) {
      Component[] components = cont.getComponents();
      for (int i=0; i<components.length; i++) {
        if (components[i] == comp)
          return true;
      }
      return false;
    }
}

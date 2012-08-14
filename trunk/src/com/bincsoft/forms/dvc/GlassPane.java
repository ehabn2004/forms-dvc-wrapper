package com.bincsoft.forms.dvc;

import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.swing.JPanel;

public class GlassPane extends JPanel implements FocusListener, MouseListener, MouseMotionListener {
    private Logger log = Logger.getLogger(getClass().getName());
    
    public GlassPane() {
        super();
        setOpaque(false);
        //setFocusCycleRoot(true);
    }

    public GlassPane(boolean b) {
        super(b);
    }

    public GlassPane(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public GlassPane(LayoutManager layoutManager, boolean b) {
        super(layoutManager, b);
    }

    @Override
    public void focusGained(FocusEvent e) {
        log.log(Level.FINE, "focusGained");
    }

    @Override
    public void focusLost(FocusEvent e) {
        log.log(Level.FINE, "focusLost");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        log.log(Level.FINE, "mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        log.log(Level.FINE, "mouseExited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

package com.bincsoft.forms.properties;

import com.bincsoft.forms.dvc.FormsGraph;

import com.bincsoft.forms.dvc.properties.formsgraph.FormsGraphProperty;

import java.util.logging.Logger;

import oracle.forms.engine.Main;
import oracle.forms.handler.IHandler;
import oracle.forms.properties.ID;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DynamicFormsPropertyTest {
    
    private Logger log = Logger.getLogger(getClass().getName());
    private FormsGraph formsGraph;
    private Main main;
    private IHandler handler;
    
    public DynamicFormsPropertyTest() {
    }
    
    @Before
    public void setup() throws ClassNotFoundException {
        formsGraph = new FormsGraph();
        formsGraph.setDebug(true);
        handler = mock(IHandler.class);
        main = mock(Main.class);
        
        when(handler.getApplet()).thenReturn(main);
        formsGraph.init(handler);
    }
    
    @Test
    public void testInitializeFormsGraph() {        
        log.info(String.format("FormsGraphProperty contains %s properties", FormsGraphProperty.values().length));
        log.info(String.format("FormsGraph registered %s property handlers", formsGraph.getPropertyHandlers().size()));
        int expectedPropertyCount = FormsProperty.values().length + FormsGraphProperty.values().length;
        assertEquals(expectedPropertyCount, formsGraph.getPropertyHandlers().size());
    }

    /**
     * @see DynamicFormsProperty#handleProperty(String,com.bincsoft.forms.BincsoftBean)
     */
    @Test
    public void testHandleProperty() throws ClassNotFoundException {
        ID id = ID.registerProperty(FormsProperty.INVOKE_METHOD.getName());
        boolean result = formsGraph.setProperty(id, "getX1Axis.getMajorTickStep");
        assertTrue(result);
    }
}

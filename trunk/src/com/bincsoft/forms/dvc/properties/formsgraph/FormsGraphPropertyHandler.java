package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.dvc.FormsGraph;
import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.properties.FormsPropertyHandler;
import com.bincsoft.forms.properties.IFormsProperty;

/**
 * Base class for all property handlers used by FormsGraph.
 */
public abstract class FormsGraphPropertyHandler extends FormsPropertyHandler {
    protected FormsGraph graph;
    
    /**
     * Gets the property of the implementation class.
     * @return
     * @see IFormsProperty
     */
    @Override
    public IFormsProperty getFormsProperty() {
        return FormsGraphProperty.fromString(getClass().getName());
    }

    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        graph = (FormsGraph) bean;
        return super.handleProperty(params, bean);
    }
}

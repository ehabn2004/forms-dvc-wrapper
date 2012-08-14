package com.bincsoft.forms.properties;

/**
 * Base definition of Forms properties.
 */
public interface IFormsProperty {
    /**
     * Gets the name of the Forms property.
     * @return Name of Forms property as String.
     */
    public String getName();
    
    /**
     * Gets the name of the class handling the Forms property.
     * @return Name of handler class as String.
     */
    public String getHandler();
    
    /**
     * Gets the fully qualified name of the class handling the Forms property.
     * @return Name of handler class as String.
     */
    public String getHandlerFullyQualifiedName();
}
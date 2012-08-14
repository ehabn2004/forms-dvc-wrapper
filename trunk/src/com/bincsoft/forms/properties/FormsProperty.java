package com.bincsoft.forms.properties;

/**
 * Enum holding properties shared among all beans.
 */
public enum FormsProperty implements IFormsProperty {
    /**
     * Invokes a given method in the wrapped object.
     *
     * <p>Invokes a given method in the graph using the syntax:</p>
     * <p>
     * {@code SET_CUSTOM_PROPERTY('MyBlock.MyBeanArea', 1, 'INVOKE_METHOD', 'methodName,first param,second param,...');}
     * </p>
     */
    INVOKE_METHOD("DynamicFormsProperty"),

    /**
     * Changes the default string delimiter, a comma, to another string.
     * 
     * <p>This is required, e.g. when setting a title string including a RGB encoded font color.
     * The RGB encoded color requires a comma as the delimiter so tha this character cannot be used with
     * the rest of the string. The following examples will make this clearer.</p>
     *
     * <pre>{@code
     * SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'SET_DELIMITER','#');
     *SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'SET_TITLE','Hello World#120,255,120#bi#12');
     *SET_CUSTOM_PROPERTY('block1.BeanArea1',1,'SET_DELIMITER',',');
     * }</pre>
     *
     * <p>In this example the delimiter is set to '#' before the graph title is set to "Hello World".
     * The color for this string is encoded by 120,255,120 (a light green) using a comma as a delimiter.
     * Finally the delimiter is set back to the default value.</p>
     */
    SET_DELIMITER("Delimiter");

    private String handler;

    /**
     * Constructor accepting name of class handling the Forms property.
     * @param handler
     */
    FormsProperty(String handler) {
        this.handler = handler;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getHandler() {
        return this.handler;
    }

    @Override
    public String getHandlerFullyQualifiedName() {
        return String.format("%s.%s", this.getClass().getPackage().getName(), this.handler);
    }

    /**
     * Gets a IFormsProperty from a string.
     * @param name
     * @return
     */
    public static IFormsProperty fromString(String name) {
        if (name != null) {
            for (FormsProperty property : FormsProperty.values()) {
                if (name.equalsIgnoreCase(property.getHandlerFullyQualifiedName())) {
                    return property;
                }
            }
        }
        throw new IllegalArgumentException(String.format("Could not find any property named %s", name));
    }

    @Override
    public String toString() {
        return String.format("%s - %s", getName(), getHandler());
    }
}

package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.properties.IFormsProperty;

/**
 * Enum which holds all definitions of property handlers for {@link com.bincsoft.forms.FormsGauge}.
 */
public enum FormsGaugeProperty implements IFormsProperty {
    /**
     * <Label>, <Initial value>, <Minimum value>, <Maximum value>
     */
    ADD_GAUGE_DATA("AddGaugeData"),
    ADD_THRESHOLDS("AddThresholds"),
    SET_THRESHOLD_COLORS("ThresholdColors"),
    SHOW_GAUGE("ShowGauge"),
    
    /**
     * <Value as double>
     */
    SET_VALUE("SetValue"),
    SET_GAUGE_TYPE("GaugeType"),
    
    /**
     * LINE (Default), NEEDLE, FILL
     */
    SET_INDICATOR_TYPE("IndicatorType"),
    
    /**
     * BELOW_GAUGE, INSIDE_GAUGE, INSIDE_GAUGE_LEFT, INSIDE_GAUGE_RIGHT, NONE, WITH_BOTTOM_LABEL (Default)
     */
    SET_METRIC_LABEL_POS("MetricLabelPosition"),
    
    /**
     * NUMBER (Default), PERCENT
     */
    SET_METRIC_LABEL_NUMBER_TYPE("MetricLabelNumberType"),
    
    /**
     * TRUE, FALSE (Default)
     */
    SET_USE_THRESHOLD_FILL_COLOR("UseThresholdFillColor"),
    
    /**
     * BELOW_GAUGE (Default), INSIDE_GAUGE, NONE
     */
    SET_BOTTOM_LABEL_POS("BottomLabelPosition"),
    SET_BOTTOM_LABEL_TEXT("BottomLabelText"),
    
    /**
     * ABOVE_GAUGE (Default), INSIDE_GAUGE, NONE
     */
    SET_TOP_LABEL_POS("TopLabelPosition"),
    SET_THRESHOLDS_BORDER_COLOR("ThresholdsBorderColor"),
    
    /**
     * TC_NONE, TC_MIN_MAX, TC_INCREMENTS, TC_THRESHOLD, TC_METRIC
     */
    SET_TICK_LABEL_CONTENT("TickLabelContent"),
    
    /**
     * Not implemented
     */
    SET_DATA_COLUMNS("DataColumns"),
    
    /**
     * Not implemented
     */
    SET_COLUMN_LABELS("ColumnLabels"),
    
    /**
     * Not implemented
     */
    ADD_DATA_ROW("AddDataRow"),
    
    /**
     * Not implemented
     */
    ADD_DATA_TO_GAUGE("AddDataToGauge");
    
    private String handler;
    
    /**
     * Constructor accepting name of class handling the Forms property.
     * @param handler
     */
    FormsGaugeProperty(String handler) {
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
    
    public static IFormsProperty fromString(String name) {
        if (name != null) {
            for (FormsGaugeProperty property : FormsGaugeProperty.values()) {
                if (name.equalsIgnoreCase(property.getHandlerFullyQualifiedName())) {
                    return property;
                }
            }
        }
        throw new IllegalArgumentException(String.format("Could not find any property named %s", name));
    }
}

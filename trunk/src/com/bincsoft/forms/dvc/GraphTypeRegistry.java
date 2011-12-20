package com.bincsoft.forms.dvc;

import java.lang.Exception;

import oracle.dss.graph.Graph;

/**
 * graphTypeRegistry maps BI Graph types to the named strings passed as an argument to
 * the getTypeForString method. The following Graph type names can be placed as an argument
 *
 * The main use of this registry is to limit the number of supported Graphs in Forms. To add
 * additional graph types, just add the graph type definition to this class
 */
public abstract class GraphTypeRegistry {
    public GraphTypeRegistry() {
    }

    /**
     * getTypeForString(String gt) retrieves the dss Graph internal number for the Graph type specified in the string.
     * If no matching Graph type is found then the method returns 0
     */
    public static int getTypeForString(String gt) throws Exception {
        int graphTypeReturn = 9999999;

        if ("HORIZONTAL_BAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_HORIZ_CLUST;
        } else if ("HORIZONTAL_BAR_2Y".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_HORIZ_CLUST_2Y;
        } else if ("VERTICAL_BAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_VERT_CLUST;
        } else if ("VERTICAL_BAR_2Y".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_VERT_CLUST2Y;
        } else if ("VERTICAL_STACKED_BAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_VERT_STACK;
        } else if ("HORIZONTAL_STACKED_BAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_HORIZ_STACK;
        } else if ("VERTICAL_PERCENT_BAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_VERT_PERCENT;
        } else if ("HORIZONTAL_PERCENT_BAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.BAR_HORIZ_PERCENT;
        } else if ("VERTICAL_LINE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.LINE_VERT_ABS;
        } else if ("HORIZONTAL_LINE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.LINE_HORIZ_ABS;
        } else if ("VERTICAL_LINE_SPLIT_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.LINE_VERT_ABS_SPLIT2Y;
        } else if ("VERTICAL_STACKED_LINE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.LINE_VERT_STACK;
        } else if ("HORIZONTAL_STACKED_LINE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.LINE_HORIZ_STACK;
        } else if ("VERTICAL_AREA_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.AREA_VERT_ABS;
        } else if ("VERTICAL_PERCENT_AREA_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.AREA_VERT_PERCENT;
        }

        else if ("VERTICAL_STACKED_AREA_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.AREA_VERT_STACK;
        } else if ("PIE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.PIE;
        } else if ("PIE_BAR_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.PIE_BAR;
        } else if ("MULTI_PIE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.PIE_MULTI;
        } else if ("MULTI_RING_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.RING_MULTI;
        } else if ("MULTI_PIE_PROPORTIONAL_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.PIE_MULTI_PROP;
        } else if ("MULTI_RING_PROPORTIONAL_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.RING_MULTI_PROP;
        } else if ("SCATTER_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.SCATTER;
        } else if ("STOCK_HIGHLOW_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.STOCK_HILO;
        } else if ("RADAR".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.RADAR_LINE;
        } else if ("RING_BAR_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.RING_BAR;
        } else if ("3D_BAR_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.THREED_BAR;
        } else if ("3D_AREA_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.THREED_AREA_SERIES;
        } else if ("3D_AREA_GROUP_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.THREED_AREA_GROUP;
        } else if ("3D_CUBE_GRAPH".equalsIgnoreCase(gt)) {
            graphTypeReturn = Graph.THREED_CUBE;
        }
        /*Manually added*/
        else if ("AREA_HORIZ_ABS".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_HORIZ_ABS;
        else if ("AREA_HORIZ_ABS_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_HORIZ_ABS_SPLIT2Y;
        else if ("AREA_HORIZ_PERCENT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_HORIZ_PERCENT;
        else if ("AREA_HORIZ_STACK".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_HORIZ_STACK;
        else if ("AREA_HORIZ_STACK_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_HORIZ_STACK_SPLIT2Y;
        else if ("AREA_VERT_ABS".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_VERT_ABS;
        else if ("AREA_VERT_ABS_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_VERT_ABS_SPLIT2Y;
        else if ("AREA_VERT_PERCENT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_VERT_PERCENT;
        else if ("AREA_VERT_STACK".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_VERT_STACK;
        else if ("AREA_VERT_STACK_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.AREA_VERT_STACK_SPLIT2Y;
        else if ("BAR_HORIZ_CLUST".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_CLUST;
        else if ("BAR_HORIZ_CLUST_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_CLUST_2Y;
        else if ("BAR_HORIZ_CLUST_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_CLUST_SPLIT2Y;
        else if ("BAR_HORIZ_PERCENT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_PERCENT;
        else if ("BAR_HORIZ_STACK".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_STACK;
        else if ("BAR_HORIZ_STACK_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_STACK_2Y;
        else if ("BAR_HORIZ_STACK_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_HORIZ_STACK_SPLIT2Y;
        else if ("BAR_VERT_CLUST".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_CLUST;
        else if ("BAR_VERT_CLUST_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_CLUST_SPLIT2Y;
        else if ("BAR_VERT_CLUST2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_CLUST2Y;
        else if ("BAR_VERT_PERCENT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_PERCENT;
        else if ("BAR_VERT_STACK".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_STACK;
        else if ("BAR_VERT_STACK_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_STACK_SPLIT2Y;
        else if ("BAR_VERT_STACK2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BAR_VERT_STACK2Y;
        else if ("BUBBLE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BUBBLE;
        else if ("BUBBLE_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BUBBLE_2Y;
        else if ("BUBBLE_LABELS".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BUBBLE_LABELS;
        else if ("BUBBLE_LABELS_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.BUBBLE_LABELS_2Y;
        else if ("HIST_HORIZ".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.HIST_HORIZ;
        else if ("HIST_VERT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.HIST_VERT;
        else if ("LINE_HORIZ_ABS".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_ABS;
        else if ("LINE_HORIZ_ABS_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_ABS_2Y;
        else if ("LINE_HORIZ_ABS_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_ABS_SPLIT2Y;
        else if ("LINE_HORIZ_PERCENT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_PERCENT;
        else if ("LINE_HORIZ_STACK".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_STACK;
        else if ("LINE_HORIZ_STACK_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_STACK_2Y;
        else if ("LINE_HORIZ_STACK_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_HORIZ_STACK_SPLIT2Y;
        else if ("LINE_VERT_ABS".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_ABS;
        else if ("LINE_VERT_ABS_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_ABS_2Y;
        else if ("LINE_VERT_ABS_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_ABS_SPLIT2Y;
        else if ("LINE_VERT_PERCENT".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_PERCENT;
        else if ("LINE_VERT_STACK".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_STACK;
        else if ("LINE_VERT_STACK_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_STACK_2Y;
        else if ("LINE_VERT_STACK_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.LINE_VERT_STACK_SPLIT2Y;
        else if ("PARETO".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.PARETO;
        else if ("PIE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.PIE;
        else if ("PIE_BAR".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.PIE_BAR;
        else if ("PIE_MULTI".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.PIE_MULTI;
        else if ("PIE_MULTI_PROP".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.PIE_MULTI_PROP;
        else if ("POLAR".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.POLAR;
        else if ("POLAR_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.POLAR_2Y;
        else if ("RADAR_AREA".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RADAR_AREA;
        else if ("RADAR_LINE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RADAR_LINE;
        else if ("RADAR_LINE_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RADAR_LINE_2Y;
        else if ("RING".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RING;
        else if ("RING_BAR".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RING_BAR;
        else if ("RING_MULTI".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RING_MULTI;
        else if ("RING_MULTI_PROP".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.RING_MULTI_PROP;
        else if ("SCATTER".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.SCATTER;
        else if ("SCATTER_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.SCATTER_2Y;
        else if ("SCATTER_LABELS".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.SCATTER_LABELS;
        else if ("SCATTER_LABELS_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.SCATTER_LABELS_2Y;
        else if ("SPECTRAL".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.SPECTRAL;
        else if ("STOCK_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_2Y;
        else if ("STOCK_CANDLE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_CANDLE;
        else if ("STOCK_CANDLE_VOLUME".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_CANDLE_VOLUME;
        else if ("STOCK_HILO".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO;
        else if ("STOCK_HILO_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_2Y;
        else if ("STOCK_HILO_CLOSE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_CLOSE;
        else if ("STOCK_HILO_CLOSE2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_CLOSE2Y;
        else if ("STOCK_HILO_CLOSE_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_CLOSE_SPLIT2Y;
        else if ("STOCK_HILO_CLOSE_VOLUME".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_CLOSE_VOLUME;
        else if ("STOCK_HILO_SPLIT2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_SPLIT2Y;
        else if ("STOCK_HILO_VOLUME".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_HILO_VOLUME;
        else if ("STOCK_OPEN_HILO_CLOSE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_OPEN_HILO_CLOSE;
        else if ("STOCK_OHLC_CANDLE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_OHLC_CANDLE;
        else if ("STOCK_OHLC_CANDLE_VOLUME".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_OHLC_CANDLE_VOLUME;
        else if ("STOCK_SPLIT_2Y".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_SPLIT_2Y;
        else if ("STOCK_VOLUME".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.STOCK_VOLUME;
        else if ("THREED_AREA_GROUP".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_AREA_GROUP;
        else if ("THREED_AREA_SERIES".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_AREA_SERIES;
        else if ("THREED_BAR".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_BAR;
        else if ("THREED_CUBE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_CUBE;
        else if ("THREED_DIAMOND".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_DIAMOND;
        else if ("THREED_OCTAGON".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_OCTAGON;
        else if ("THREED_PYRAMID".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_PYRAMID;
        else if ("THREED_RIBBON_GROUP".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_RIBBON_GROUP;
        else if ("THREED_RIBBON_SERIES".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_RIBBON_SERIES;
        else if ("THREED_START".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_START;
        else if ("THREED_SURFACE".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_SURFACE;
        else if ("THREED_SURFACE_HONEYCOMB".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_SURFACE_HONEYCOMB;
        else if ("THREED_SURFACE_SIDES".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_SURFACE_SIDES;
        else if ("THREED_END".equalsIgnoreCase(gt))
            graphTypeReturn = Graph.THREED_END;
        return graphTypeReturn;
    }
}

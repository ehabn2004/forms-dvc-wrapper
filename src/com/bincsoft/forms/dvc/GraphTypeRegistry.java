package com.bincsoft.forms.dvc;


import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import oracle.dss.graph.Graph;


/**
 * graphTypeRegistry maps BI Graph types to the named strings passed as an argument to
 * the getTypeForString method. The following Graph type names can be placed as an argument
 *
 * The main use of this registry is to limit the number of supported Graphs in Forms. To add
 * additional graph types, just add the graph type definition to this class
 */
public abstract class GraphTypeRegistry {
    private static Map oldMap = new HashMap<String, Integer>();
    public static final int NO_GRAPH_TYPE_FOUND = 9999999;

    /**
     * Retrieves the dss Graph internal number for the Graph type specified in the string.
     * If no matching Graph type is found then the method returns NO_GRAPH_TYPE_FOUND
     */
    public static int getTypeForString(String gt) {
        Logger logger = Logger.getLogger(GraphTypeRegistry.class.getName());
        
        if (oldMap.isEmpty()) {
            initOldMap();
        }

        if (oldMap.containsKey(gt)) {
            logger.log(Level.FINE,
                       String.format("%s found graph type %s in map.", GraphTypeRegistry.class.getName(), gt));
            return ((Integer)oldMap.get(gt)).intValue();
        }
        
        int graphType = NO_GRAPH_TYPE_FOUND;

        try {
            graphType = getGraphType(gt);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        if (graphType != NO_GRAPH_TYPE_FOUND) {
            logger.log(Level.FINE,
                       String.format("%s found graph type %s in %s.", GraphTypeRegistry.class.getName(), gt,
                                     Graph.class.getName()));
            return graphType;
        }

        logger.log(Level.SEVERE,
                   String.format("%s could not find graph type %s.", GraphTypeRegistry.class.getName(), gt));
        
        return NO_GRAPH_TYPE_FOUND;
    }

    private static void initOldMap() {
        oldMap.clear();
        oldMap.put("HORIZONTAL_BAR", Graph.BAR_HORIZ_CLUST);
        oldMap.put("HORIZONTAL_BAR_2Y", Graph.BAR_HORIZ_CLUST_2Y);
        oldMap.put("VERTICAL_BAR", Graph.BAR_VERT_CLUST);
        oldMap.put("VERTICAL_BAR_2Y", Graph.BAR_VERT_CLUST2Y);
        oldMap.put("VERTICAL_STACKED_BAR", Graph.BAR_VERT_STACK);
        oldMap.put("HORIZONTAL_STACKED_BAR", Graph.BAR_HORIZ_STACK);
        oldMap.put("VERTICAL_PERCENT_BAR", Graph.BAR_VERT_PERCENT);
        oldMap.put("HORIZONTAL_PERCENT_BAR", Graph.BAR_HORIZ_PERCENT);
        oldMap.put("VERTICAL_LINE_GRAPH", Graph.LINE_VERT_ABS);
        oldMap.put("HORIZONTAL_LINE_GRAPH", Graph.LINE_HORIZ_ABS);
        oldMap.put("VERTICAL_LINE_SPLIT_GRAPH", Graph.LINE_VERT_ABS_SPLIT2Y);
        oldMap.put("VERTICAL_STACKED_LINE_GRAPH", Graph.LINE_VERT_STACK);
        oldMap.put("HORIZONTAL_STACKED_LINE_GRAPH", Graph.LINE_HORIZ_STACK);
        oldMap.put("VERTICAL_AREA_GRAPH", Graph.AREA_VERT_ABS);
        oldMap.put("VERTICAL_PERCENT_AREA_GRAPH", Graph.AREA_VERT_PERCENT);
        oldMap.put("VERTICAL_STACKED_AREA_GRAPH", Graph.AREA_VERT_STACK);
        oldMap.put("PIE_GRAPH", Graph.PIE);
        oldMap.put("PIE_BAR_GRAPH", Graph.PIE_BAR);
        oldMap.put("MULTI_PIE_GRAPH", Graph.PIE_MULTI);
        oldMap.put("MULTI_RING_GRAPH", Graph.RING_MULTI);
        oldMap.put("MULTI_PIE_PROPORTIONAL_GRAPH", Graph.PIE_MULTI_PROP);
        oldMap.put("MULTI_RING_PROPORTIONAL_GRAPH", Graph.RING_MULTI_PROP);
        oldMap.put("SCATTER_GRAPH", Graph.SCATTER);
        oldMap.put("STOCK_HIGHLOW_GRAPH", Graph.STOCK_HILO);
        oldMap.put("RADAR", Graph.RADAR_LINE);
        oldMap.put("RING_BAR_GRAPH", Graph.RING_BAR);
        oldMap.put("3D_BAR_GRAPH", Graph.THREED_BAR);
        oldMap.put("3D_AREA_GRAPH", Graph.THREED_AREA_SERIES);
        oldMap.put("3D_AREA_GROUP_GRAPH", Graph.THREED_AREA_GROUP);
        oldMap.put("3D_CUBE_GRAPH", Graph.THREED_CUBE);
    }

    private static int getGraphType(String graphType) throws NoSuchFieldException, IllegalAccessException {
        Class graphClass = Graph.class;
        Graph graph = new Graph();
        
        Field[] fields = graphClass.getFields();
        for (Field f : fields) {
            if (f.getName().equals(graphType) && f.getType().isPrimitive()) {
                return f.getInt(graph);
            }
        }
        
        return NO_GRAPH_TYPE_FOUND;
    }
}

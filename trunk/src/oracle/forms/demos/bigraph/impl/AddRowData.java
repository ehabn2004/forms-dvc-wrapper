package oracle.forms.demos.bigraph.impl;

import oracle.forms.demos.bigraph.FormsGraph;
import oracle.forms.demos.bigraph.IFGPropImpl;
import oracle.forms.properties.ID;

public class AddRowData implements IFGPropImpl {
    public static final ID propertyId = ID.registerProperty("ADD_ROWDATA");
  
    public AddRowData() {
        super();
    }

    public boolean handleProperty(String sParams, FormsGraph graph) {
      String sGraphDataRow = null;
      // get String from Forms Object
      if (!sParams.equals("")) {
          sGraphDataRow = sParams;
          // check if delimiter is used correctly
          if (sGraphDataRow.indexOf(graph.getDelimiter()) > 0) {
              /*
               * Pass string to localRelationalData class. in here the
               * string is parsed and ignored if it doesn't contain 4
               * parameters and if a number exception occurs. The data
               * parameters are of tyle label, label, double and
               * finally integer
               */
              if (!graph.getLocalRelationalData().addRelationalDataRow(sGraphDataRow, graph.getDelimiter())) {
                  graph.DebugMessage("ADD_ROW_DATA: " + sGraphDataRow +
                               " not accepted");
              }
              return true;
          } else {
              graph.DebugMessage("ADD_ROWDATA: No valid string delimiter found. Expected \"" +
                           graph.getDelimiter() + "\"");
              return true;
          }
      } else {
          graph.DebugMessage("ADD_ROWDATA: No data passed");
          return true;
      }
    }
}

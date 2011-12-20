package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.dvc.FormsGraph;


public class AddRowData implements IFormsGraphProperty {
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
                  graph.debugMessage("ADD_ROW_DATA: " + sGraphDataRow +
                               " not accepted");
              }
              return true;
          } else {
              graph.debugMessage("ADD_ROWDATA: No valid string delimiter found. Expected \"" +
                           graph.getDelimiter() + "\"");
              return true;
          }
      } else {
          graph.debugMessage("ADD_ROWDATA: No data passed");
          return true;
      }
    }
}

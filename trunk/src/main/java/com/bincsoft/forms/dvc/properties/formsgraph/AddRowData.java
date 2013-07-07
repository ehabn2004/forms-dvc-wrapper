package com.bincsoft.forms.dvc.properties.formsgraph;

import com.bincsoft.forms.BincsoftBean;

public class AddRowData extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            // check if delimiter is used correctly
            if (sParams.indexOf(graph.getDelimiter()) > 0) {
                /*
               * Pass string to localRelationalData class. in here the
               * string is parsed and ignored if it doesn't contain 4
               * parameters and if a number exception occurs. The data
               * parameters are of tyle label, label, double and
               * finally integer
               */
                if (!graph.getLocalRelationalData().addRelationalDataRow(sParams, graph.getDelimiter())) {
                    log("ADD_ROW_DATA: " + sParams + " not accepted");
                }
                return true;
            } else {
                log("ADD_ROWDATA: No valid string delimiter found. Expected \"" + graph.getDelimiter() + "\"");
                return true;
            }
        } else {
            log("ADD_ROWDATA: No data passed");
            return true;
        }
    }
}

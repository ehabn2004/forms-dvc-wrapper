package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.BincsoftBean;

public class DataColumns extends FormsGaugePropertyHandler {
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        log("SET_DATA_COLUMNS is not implemented.");
        /*String[] aParams = ((String)_object).split(sDelimiter);
                                debugMessage("SET_DATA_COLUMNS: Received '" + aParams.length + "' parameters.");
                                alDataRowLabels.clear();
                                alDataRows.clear(); // We need to clear the data array when setting new columns
                                ArrayList<Object> alSpec = new ArrayList<Object>();
                                ArrayList<Object> alColLbls = new ArrayList<Object>();
                                for (int i=0; i<aParams.length; i++) {
                                  if (aParams[i].equalsIgnoreCase("LABEL"))
                                    ; // Do nothing because this is a required column
                                  else if (aParams[i].equalsIgnoreCase("METRIC")) {
                                    alSpec.add(DataSpecification.METRIC);
                                    alColLbls.add("Value");
                                  }
                                  else if (aParams[i].equalsIgnoreCase("MINIMUM")) {
                                    alSpec.add(DataSpecification.MINIMUM);
                                    alColLbls.add("Min");
                                  }
                                  else if (aParams[i].equalsIgnoreCase("MAXIMUM")) {
                                    alSpec.add(DataSpecification.MAXIMUM);
                                    alColLbls.add("Max");
                                  }
                                  else if (aParams[i].equalsIgnoreCase("THRESHOLD")) {
                                    alSpec.add(DataSpecification.THRESHOLD);
                                    alColLbls.add("Threshold" + i);
                                  }
                                  else
                                    debugMessage("SET_DATA_COLUMNS: Could not understand column type '" + aParams[i] + "'!");
                    }
                    aDataColumns = alSpec.toArray();
                    aDataColumnLabels = alColLbls.toArray();*/
        return true;
    }
}

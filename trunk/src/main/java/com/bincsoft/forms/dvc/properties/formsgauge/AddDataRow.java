package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.BincsoftBean;

public class AddDataRow extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        log("ADD_DATA_ROW is not implemented.");
        /*String[] aParams = ((String)_object).split(sDelimiter);
                                debugMessage("ADD_DATA_ROW: Adding a row with '" + aParams.length + "' columns...");
                                for (int i=0; i<aParams.length; i++)
                                {
                                  if (i!=0) {
                                  ArrayList<Object> al = new ArrayList<Object>();
                                  al.add(aParams[i]);
                                  alDataRows.add(al);
                                  }
                                  else
                                     alDataRowLabels.add(aParams[i]);
                                }*/
        return true;
    }
}

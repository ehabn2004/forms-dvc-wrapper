package com.bincsoft.forms.dvc.properties.formsgauge;

import com.bincsoft.forms.BincsoftBean;

public class AddDataToGauge extends FormsGaugePropertyHandler {
    @Override
    public boolean handleProperty(String params, BincsoftBean bean) {
        super.handleProperty(params, bean);
        log("ADD_DATA_TO_GAUGE is not implemented.");
        /*debugMessage("ADD_DATA_TO_GAUGE: Adding data to gauge...");
                                Object[][] a = new Object[alDataRows.size()][];
                                for (int i=0; i<alDataRows.size(); i++) {
                                  a[i] = ((ArrayList)alDataRows.get(i)).toArray();
                    }
                                m_gauge.setGridData(aDataColumns, aDataColumnLabels, alDataRowLabels.toArray(), a);
                                m_gauge.setVisible(true);*/
        return true;
    }
}

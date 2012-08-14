package com.bincsoft.forms.dvc.properties.formsgraph;


import com.bincsoft.forms.BincsoftBean;
import com.bincsoft.forms.dvc.DvcHelper;

import java.awt.Font;

import java.util.ArrayList;
import java.util.List;

import oracle.dss.dataView.Titles;
import oracle.dss.graph.BaseText;


public class GraphFontSize extends FormsGraphPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            List<Titles> titles = new ArrayList<Titles>();
            List<BaseText> baseTexts = new ArrayList<BaseText>();
            
            titles.add(graph.getGraph().getDataviewFootnote());
            titles.add(graph.getGraph().getDataviewSubtitle());
            titles.add(graph.getGraph().getDataviewTitle());
            titles.add(graph.getGraph().getFootnote());
            
            baseTexts.add(graph.getGraph().getLegendText());
            baseTexts.add(graph.getGraph().getLegendTitle());
            baseTexts.add(graph.getGraph().getO1TickLabel());
            baseTexts.add(graph.getGraph().getO1Title());
            baseTexts.add(graph.getGraph().getPieLabel());
            baseTexts.add(graph.getGraph().getRingTotalLabel());
            baseTexts.add(graph.getGraph().getSliceLabel());
            titles.add(graph.getGraph().getSubtitle());
            titles.add(graph.getGraph().getTitle());
            baseTexts.add(graph.getGraph().getX1TickLabel());
            baseTexts.add(graph.getGraph().getX1Title());
            baseTexts.add(graph.getGraph().getY1TickLabel());
            baseTexts.add(graph.getGraph().getY1Title());
            baseTexts.add(graph.getGraph().getY2TickLabel());
            baseTexts.add(graph.getGraph().getY2Title());
            baseTexts.add(graph.getGraph().getZTickLabel());
            baseTexts.add(graph.getGraph().getZTitle());
            
            for (Titles title : titles) {
                Font oldFont = title.getFont();
                Font newFont = new Font(oldFont.getName(), oldFont.getStyle(), DvcHelper.getFormsFontSizeInPoints(sParams));
                title.setFont(newFont);
            }
            
            for (BaseText baseText : baseTexts) {
                Font oldFont = baseText.getFont();
                Font newFont = new Font(oldFont.getName(), oldFont.getStyle(), DvcHelper.getFormsFontSizeInPoints(sParams));
                baseText.setFont(newFont);
            }
        }
        return true;
    }
}

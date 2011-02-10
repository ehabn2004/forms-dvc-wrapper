/**
 * ======================================================
 * Oracle Forms Wrapper for Data Visualization Components
 * ======================================================
 *
 * Author: Bincsoft
 * based on the work by Frank Nimphius (version: 9.0.4 - January 2005)
 *
 * GaugeDataStore.java Version 1.0.2 - December 12th 2010
 */

package oracle.forms.demos.bigauge;

import java.util.ArrayList;
import java.awt.Color;

public class GaugeDataStore {
  private FormsGauge fg = null;
  
  private String sLabel = "";
  private double dCurrentValue = 0.00;
  private double dMinValue = 0.00;
  private double dMaxValue = 100.00;
  private ArrayList<Double> alThresholds = new ArrayList<Double>();
  private ArrayList<Color> alThresholdColors = new ArrayList<Color>();
  
  public GaugeDataStore(FormsGauge pParent) {
    fg = pParent;
  }
  
  public void setLabel(String s) {
    sLabel = s;
  }
  
  public String getLabel() {
    return sLabel;
  }
  
  public void setCurrentValue(double d) {
    dCurrentValue = d;
  }
  
  public double getCurrentValue() {
    return dCurrentValue;
  }
  
  public void setMinValue(double d) {
    dMinValue = d;
  }
  
  public double getMinValue() {
    return dMinValue;
  }
  
  public void setMaxValue(double d) {
    dMaxValue = d;
  }
  
  public double getMaxValue() {
    return dMaxValue;
  }
  
  public void addThreshold(double d) {
    alThresholds.add(d);
  }
  
  public ArrayList getThresholds() {
    return alThresholds;
  }
  
  public void addThresholdColor(Color c) {
    alThresholdColors.add(c);
  }
  
  public ArrayList getThresholdColors() {
    return alThresholdColors;
  }
  
  private void debugMessage(String s) {
    fg.debugMessage(s);
  }
  
  public void reset() {
    sLabel = "";
    dCurrentValue = 0.00;
    dMinValue = 0.00;
    dMaxValue = 100.00;
    alThresholds = new ArrayList<Double>();
    alThresholdColors = new ArrayList<Color>();
  }
}
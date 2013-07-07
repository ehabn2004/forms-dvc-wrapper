package com.bincsoft.forms.dvc;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles data operations for the Graph Bean. To retrieve the ArrayList containing
 * the graph data use the getRelationalData() method. Data is passed as a String delimited string,
 * containing two lables and one label value
 */
public class LocalRelationalData {
    Logger log = Logger.getLogger(getClass().getName());
    // Store for the data passed to the BI Graph
    ArrayList<Object> aDataStore = new ArrayList<Object>();
    // Store for PrimaryKey references
    ArrayList<Object> aPrimKeys = new ArrayList<Object>();

    public LocalRelationalData() {
    }

    /**
     * boolean addRelationalDataRow(String inData, String sDelimiter) takes a delimited string with
     * three tokens as a first argument. The string must contain two labels and one data value and
     * one String value representing the primary key
     */
    public boolean addRelationalDataRow(String inData, String sDelimiter) {
        log.log(Level.FINE, "============ =========== ============");
        log.log(Level.FINE, "addRelationalDataRow(): inData=" + inData + " stringDelimiter=" + sDelimiter);

        Object[] newRow = parseStringData(inData, sDelimiter);
        log.log(Level.FINE, "addRelationalDataRow(): Object[] length of parsed string= " + newRow.length);

        Object[] passNewData = new Object[newRow.length];


        // new row can be null or an object of max length 4
        if (newRow.length > 0) {
            if (newRow.length > 3) {
                // add primary key to ArrayList
                aPrimKeys.add(newRow[3]);
            } else {
                // add dummy to keep primKEys in synch - You never know who uses this bean
                aPrimKeys.add(null);
            }

            // add graph data to graph data Array
            passNewData[0] = newRow[0];
            passNewData[1] = newRow[1];
            passNewData[2] = newRow[2];
            aDataStore.add(passNewData);
            return true;
        }
        return false;
    }

    private int findIndexOfMatch(Object[] fnd, boolean bShowGraphAsSeries) {
        int indx = -1;
        Object[] ob = aDataStore.toArray();
        for (indx = 0; indx < aDataStore.size(); indx++) {
            if (bShowGraphAsSeries) {
                if (((Object[])ob[indx])[0].equals(fnd[1]) && ((Object[])ob[indx])[1].equals(fnd[0]) &&
                    ((Object[])ob[indx])[2].equals(fnd[2])) {
                    // found match
                    break;
                }
            } else {
                if (((Object[])ob[indx])[0].equals(fnd[0]) && ((Object[])ob[indx])[1].equals(fnd[1]) &&
                    ((Object[])ob[indx])[2].equals(fnd[2])) {
                    // found match
                    break;
                }
            }
        }
        return indx;
    }

    private void log(String msg) {
        log.log(Level.FINE, msg);
    }

    /**
     * String getPrimaryKey searches and retrieves the primary key of a row
     * based on its object signature
     */
    public String getPrimaryKey(String dataRow, String sDelimiter, boolean bShowGraphAsSeries) {
        String primaryKey = "";
        Object[] searchRow = this.parseStringData(dataRow, sDelimiter);
        // find the index in the data store that points to the data match
        int indx = findIndexOfMatch(searchRow, bShowGraphAsSeries);
        log.log(Level.FINE, "getPrimaryKey(): " + dataRow + ", returns indx=" + indx);
        if (indx >= 0 && indx < aPrimKeys.size()) {
            primaryKey = (String)aPrimKeys.get(indx);
            log.log(Level.FINE, "getPrimaryKey(): " + primaryKey);
        } else {
            log.log(Level.FINE, "getPrimaryKey() - no Value found for search string");
        }
        return primaryKey;
    }

    /**
     * ArrayList getRelationalData() returns a List object of the current data saved for this Graph
     */
    public ArrayList getRelationalData() {
        return aDataStore;
    }

    /**
     * ModifyData updates a columns or pie slice in a graph
     * @param inData a delimited string containing the column name , the row name and the new value
     * @return true of false
     */
    public boolean ModifyData(String inData, String sDelimiter, boolean bShowGraphAsSeries) {
        boolean ret = true;
        Object[] dataRow = this.parseStringData(inData, sDelimiter);
        int indx = findIndexOfMatch(dataRow, bShowGraphAsSeries);
        log.log(Level.FINE, "ModifyData(): " + dataRow + ", found indx=" + indx);
        if (indx >= 0) {
            try {
                Object[] newData = new Object[3];
                newData[0] = dataRow[0];
                newData[1] = dataRow[1];
                // add the new value, replacing the old
                newData[2] = new Double((String)dataRow[3]);
                aDataStore.set(indx, newData);
                ret = true;
            } catch (NumberFormatException nfe) {
                log.log(Level.FINE, "ModifyData(): new value is not a valid number format");
                ret = false;
            }
        } else {
            ret = false;
        }

        return ret;
    }

    /**
     * Object[] parseStringData (String psd, String SDelimiter) creates an array of
     * objects from a string. For the Forms Graph this string has three tokens delimited
     * by the string passed as second argument. The string format is <column><delimiter><row><delimiter><value>.
     * For example: "USA,AVGSAL,1345"
     */
    protected Object[] parseStringData(String psd, String sDelimiter) {
        Object[] rowData = null;
        int tokenLength = 0;

        StringTokenizer tokens = new StringTokenizer(psd, sDelimiter);
        // create an array with the number of columns provided. Make sure that the
        // first two columns are of type String and the rest is double

        tokenLength = tokens.countTokens();

        // add data passes the primary key as an optional 4th value for further
        // use in a master/detail relationship or row searches, This forth value
        // can be null

        log.log(Level.FINE, "parseStringData() received " + tokenLength + " tokens");

        try {

            /*
      * You cannot pass more than two labels and one value at the same time, nor can you pass
      * labels only.
      *
      * However I am not rude and instead ignore values after the third position
      * rather than raising an exception
      */
            log.log(Level.FINE, "parseStringData(): try block entered");

            if (tokenLength > 2) {
                rowData = new Object[tokenLength];
                for (int i = 0; i < tokenLength; i++) {

                    /*
         * The first two rows indicate the column labels and row labels. Data passed to
         * Forms thus must have the following format
         */
                    switch (i) {
                    case 0:
                    case 1:
                        rowData[i] = tokens.nextElement();
                        log.log(Level.FINE, " parseStringData(): Token" + i + " has a value of " + (String)rowData[i]);
                        break;
                    case 2:
                        rowData[i] = new Double((String)tokens.nextElement());
                        log.log(Level.FINE, " parseStringData(): Token" + i + " has a value of " + (Double)rowData[i]);
                        break;
                        // case 4 is used e.g. with addRelationalDataRow and modifyRelationalRow
                    case 3:
                        rowData[i] = tokens.nextElement();
                        log.log(Level.FINE, " parseStringData(): Token" + i + " has a value of " + (String)rowData[i]);
                        break;
                    default: // ignore
                    }
                }
                return rowData;
            } else {
                log.log(Level.FINE, " parseStringData(): Wrong number of tokens in line: " + psd);
            }
        }

        /* if a more serious exception occurs then raise customEvent in Forms for the Forms application to
     * react. In addition send message to the debug console if debug is enabled
    */
        catch (NumberFormatException nfc) {
            log.log(Level.FINE, "parseStringData(): Exception in parseStringData method: Value passed is not a valid number");
        }

        catch (Exception ex) {
            log.log(Level.FINE, "parseStringData(): unhandled exception " + ex.getMessage());
        }
        log.log(Level.FINE, "parseStringData(): return empty array of Objects.");
        return new Object[0];
    }

    /**
     * Remove Data
     */
    public boolean RemoveData(String remData, String sDelimiter, boolean bShowGraphAsSeries) {
        boolean ret = true;
        Object[] dataRow = this.parseStringData(remData, sDelimiter);
        int indx = this.findIndexOfMatch(dataRow, bShowGraphAsSeries);
        // if matching row is found then remove it from data store array
        if (indx >= 0) {
            aDataStore.remove(indx);
            // check if primary keys exist and if remove primary key stored
            // for this row
            if (aPrimKeys.size() > 0) {
                aPrimKeys.remove(indx);
            }
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    public void clearGraphData() {
        // reset data kept for this Graph
        aDataStore = new ArrayList<Object>();
        aPrimKeys = new ArrayList<Object>();
    }
}

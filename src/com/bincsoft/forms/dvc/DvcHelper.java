package com.bincsoft.forms.dvc;

import java.awt.Color;
import java.awt.Font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DvcHelper {    
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    
    public static String handleTokenNullvaluesInStartAndEnd(String in, String sDelimiter) {
        /*
         * tokenizers don't work well if nothing is provided between delimiters.
         * Therefore all String must be checked for this
         */
        // check if token is first character
        in = (in.startsWith(sDelimiter) ? " " + in : in);

        // check if delimiter is last character in string and if, remove
        while (in.lastIndexOf(sDelimiter) == (in.length() - sDelimiter.length())) {
            in = in.substring(0, in.lastIndexOf(sDelimiter) - 1);
        }

        return in;
    }
    
    /**
     * public Object[] getTitleFromString(String in) takes a delimited string
     * and creates a title string, a Font value and a Color value of it. The
     * delimiter used is the defined delimiter stored in sDelimiter, or ',' as
     * the default. The String syntax is {@code '<Title>,<color>,<[b][i]>,<size>,<Font
     * Name>'} Values can be omitted from right to left, e.g
     * {@code '<Title>,<color>,<[b][i]>,<size>' or '<Title>,<color>,<[b][i]>'}
     */
    public static GraphText getTitleFromString(String in, String sDelimiter) {
        Color fontCol = Color.black;
        String title = "";
        String fontName = "Times New Roman";
        int size = 12;
        int style = Font.PLAIN;

        // remove empty tokens in beginning and end
        in = handleTokenNullvaluesInStartAndEnd(in, sDelimiter);

        StringTokenizer tokens = new StringTokenizer(in, sDelimiter);
        int tokenCount = tokens.countTokens();
        log(Level.FINE, ("getTitleFromString() received " + tokenCount + " tokens"));
        for (int i = 0; i < tokenCount; i++) {
            switch (i) {
                // Title string
            case 0:
                title = (String)tokens.nextElement();
                log(Level.FINE, ("getFontFromString(): Title text =" + title));
                break;
                // Font color
            case 1:
                fontCol = ColorCodeRegistry.getColorCode((String)tokens.nextElement());
                fontCol = (fontCol != null ? fontCol : Color.black);
                log(Level.FINE, ("getFontFromString(): Color =" + fontCol.toString()));
                break;
                // Style
            case 2:
                String s = (String)tokens.nextElement();
                log(Level.FINE, ("getFontFromString(): Style =" + s));
                if ((s.indexOf("n")) >= 0) {
                    style = Font.PLAIN;
                }
                if ((s.indexOf("b")) >= 0) {
                    style = Font.BOLD;
                }
                if ((s.indexOf("i")) >= 0) {
                    // bit wise concatenation of italic
                    style |= Font.ITALIC;
                }
                break;
                // size
            case 3:
                try {
                    size = getFormsFontSizeInPoints((String)tokens.nextElement());
                    log(Level.FINE, ("getFontFromString(): Font size =" + size));
                } catch (NumberFormatException nfe) {
                    log(Level.FINE, ("getTitleFromString(): " + size +
                                 " is an unknown font size and cannot be casted to Integer"));
                }
                break;
                // color
            case 4:
                fontName = (String)tokens.nextElement();
                log(Level.FINE, ("getFontFromString(): Font name =" + fontName));
                break;
            default: // ignore
            }
        }
        return new GraphText(title, new Font(fontName, style, size), fontCol);
    }
    
    public static int getFormsFontSizeInPoints(String sizeAsString) throws NumberFormatException {
        return (int) (Integer.parseInt(sizeAsString) * 1.33d); // Recalculate whatever format Forms uses to pt
    }
    
    public Object[] getParamsFromString(String sIn, String sDelimiter) {
        // remove empty tokens in beginning and end
        sIn = handleTokenNullvaluesInStartAndEnd(sIn, sDelimiter);
        StringTokenizer tokens = new StringTokenizer(sIn, sDelimiter);
        log(Level.FINE, ("getParamsFromString() received " + tokens.countTokens() + " tokens"));
        Object[] tokensArray = new Object[tokens.countTokens()];
        for (int i = 0; i < tokens.countTokens(); i++) {
            tokensArray[i] = tokens.nextElement();
        }
        return tokensArray;
    }
    
    private static void log(Level lvl, String s) {
        Logger.getLogger(DvcHelper.class.getName()).log(lvl, s);
    }
}

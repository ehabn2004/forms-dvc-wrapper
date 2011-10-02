 /**
  * ======================================================
  * Oracle Forms Wrapper for Data Visualization Components
  * ======================================================
  *
  * Author: Bincsoft
  * based on the work by Frank Nimphius (version: 9.0.4 - January 2005)
  *
  * colorCodeRegistry.java Version 1.0.2 - December 12th 2010
  */

package oracle.forms.demos.bigraph;

import java.awt.Color;

import java.lang.Exception;

import java.util.StringTokenizer;

public abstract class ColorCodeRegistry
{


  public static Color getColorCode(String col)
  {

   Color ret = null;

   // check if RGB values are passed
   int indx = col.indexOf(",");

    // if indx > 0 then possibly an RGB value was passed
   if (indx > 0)
   {
    // parse string for RGB values
    return parseRGB(col);
   }
   else
   {
    // check the different color values
    if("black".equalsIgnoreCase(col))
    {
      return Color.black;
    }
    else if ("blue".equalsIgnoreCase(col)){
      return Color.blue;
    }
    else if ("cyan".equalsIgnoreCase(col)){
      return Color.cyan;
    }
    else if ("darkGray".equalsIgnoreCase(col)){
     return Color.darkGray;
    }
     else if ("gray".equalsIgnoreCase(col)){
      return Color.gray;
    }
     else if ("green".equalsIgnoreCase(col)){
      return Color.green;
    }
    else if ("lightGray".equalsIgnoreCase(col)){
      return Color.lightGray;
    }
    else if ("magenta".equalsIgnoreCase(col)){
      return Color.magenta;
    }
    else if ("orange".equalsIgnoreCase(col)){
      return Color.orange;
    }
    else if ("pink".equalsIgnoreCase(col)){
      return Color.pink;
    }
    else if ("red".equalsIgnoreCase(col)){
      return Color.red;
    }
    else if ("yellow".equalsIgnoreCase(col)){
      return Color.yellow;
    }
    else if ("white".equalsIgnoreCase(col)){
      return Color.white;
    }
    else
    {
      // no match found
      return null;
    }
   }
  }

  private static Color parseRGB(String col)
  {
    try
      {

        int tokenLength = 0;

        StringTokenizer tokens = new StringTokenizer(col,",");

        // only three tokens should be provided, if more found then the
        // rest is ignored

        tokenLength        = tokens.countTokens();
        String[] sRGB   =   new String[tokenLength];

        for (int i = 0; i < 3; i++)
          {
           sRGB[i] = ((String) tokens.nextElement());
          }

        // create and return Color
        return new Color((new Integer(sRGB[0])).intValue(),(new Integer(sRGB[1])).intValue(),(new Integer(sRGB[2])).intValue());
        }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
     return null;
    }
}
package oracle.forms.demos.bigraph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FormsGraphTransferable implements Transferable {
    FormsGraph m_graph = null;
    
    public FormsGraphTransferable(FormsGraph graph) {
        super();
        m_graph = graph;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { DataFlavor.imageFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException,
                                                            IOException {
        if (!isDataFlavorSupported(flavor))
            throw new UnsupportedFlavorException(flavor);

        if (flavor == DataFlavor.imageFlavor) {
            // Capture exported image in a byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            m_graph.getGraph().exportToPNG(out);

            // Wait until the rendering completed
            Image image =
                Toolkit.getDefaultToolkit().createImage(out.toByteArray());
            MediaTracker tracker = new MediaTracker(m_graph);
            tracker.addImage(image, 1);
            try {
                tracker.waitForAll();
            } catch (InterruptedException ex) {
            }

            // Begin: Add title to image
            String sTitle = m_graph.getGraph().getDataviewTitle().getText();

            if (sTitle == null || sTitle.equals("")) {
                m_graph.DebugMessage("Graph title is empty, skipping...");
                return image;
            }

            m_graph.DebugMessage("Graph title: '" + sTitle + "'");

            Font font = m_graph.getGraph().getDataviewTitle().getFont();
            FontMetrics fm = m_graph.getFontMetrics(font);
            int iStringWidth = fm.stringWidth(sTitle);
            int iStringHeight = fm.getHeight();
            int iImageWidth = m_graph.getParent().getWidth();
            int iImageHeight = m_graph.getParent().getHeight();
            m_graph.DebugMessage("iImageWidth=" + iImageWidth + " iImageHeight=" +
                         iImageHeight + " iStringWidth=" + iStringWidth +
                         " iStringHeight=" + iStringHeight);
            iStringHeight += 2; // Add some space between title and image

            String[] aTitle = sTitle.split("\n");

            // Create a new image with room for title and image
            BufferedImage newImage =
                new BufferedImage(iImageWidth, iImageHeight +
                                  (iStringHeight * aTitle.length),
                                  BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();

            // Draw the screenshot in the new image
            g.drawImage(image, 0, iStringHeight * aTitle.length, null);

            // Fill the title area with white background
            g.setColor(Color.white);
            g.fillRect(0, 0, iImageWidth, iStringHeight * aTitle.length);

            // Draw the title in the title area
            g.setColor(Color.black);
            g.setFont(font);

            for (int i = 0; i < aTitle.length; i++) {
                iStringWidth = fm.stringWidth(aTitle[i]);
                g.drawString(aTitle[i], iImageWidth / 2 - iStringWidth / 2,
                             (iStringHeight * (i + 1)) - 2);
            }

            g.dispose();
            // End: Add title to image

            return newImage;
        }
        return null;
    }
}

package com.bincsoft.forms.dvc;

import java.awt.Color;
import java.awt.Font;

/**
 * Defines a text, font and color used when defining labels in a graph.
 */
public class GraphText {
    public static Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 14);
    
    private String text;
    private Font font;    
    private Color color = Color.BLACK;
    
    private GraphText() {}
    
    public GraphText(String text, Font font, Color color) {
        setText(text);
        setFont(font);
        setColor(color);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return String.format("Text: %s, Font: %s, Color: %s", getText(), getFont(), getColor());
    }
}

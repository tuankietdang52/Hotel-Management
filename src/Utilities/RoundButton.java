package Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class RoundButton extends JButton {
    private final int radius;
    private String text;
    private Color borderColor = null;
    private ImageIcon image;

    public RoundButton(int radius, String text){
        this.radius = radius;
        this.text = text;
        setOpaque(false);
        setContentAreaFilled(false);
    }

    public RoundButton(int radius, ImageIcon image){
        this.radius = radius;
        this.image = image;
        setOpaque(false);
        setContentAreaFilled(false);
    }

    public RoundButton(int radius, String text, Color borderColor){
        this.radius = radius;
        this.text = text;
        this.borderColor = borderColor;
        setOpaque(false);
        setContentAreaFilled(false);
    }

    public RoundButton(int radius, ImageIcon image, Color borderColor){
        this.radius = radius;
        this.image = image;
        this.borderColor = borderColor;
        setOpaque(false);
        setContentAreaFilled(false);
    }

    private void drawBorder(){
        setBorder(new RoundBorder(borderColor, radius));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension arcs = new Dimension(radius, radius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //draws the rounded opaque panel with borders.
        g2d.setColor(getBackground());
        //paint background
        g2d.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        if (borderColor != null){
            g2d.setColor(borderColor);
            g2d.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }

        if (image != null) drawImage(g);
        else drawText(g);

        drawBorder();
        super.paintComponent(g);
        g.dispose();
    }

    private void drawText(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(text)) / 2;
        int y = ((getHeight() - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g.setColor(getForeground());
        g.drawString(text, x, y);
    }

    private void drawImage(Graphics g){
        int x = getWidth() / 2 - image.getIconWidth() / 2;
        int y = getHeight() / 2 - image.getIconHeight() / 2;
        image.paintIcon(this, g, x, y);
    }
}

package Utilities;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SelectListCellRenderer extends JLabel implements ListCellRenderer<Object> {
    private final Color borderColor;
    private String text;
    public SelectListCellRenderer(Color borderColor){
        this.borderColor = borderColor;
        setOpaque(false);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        text = value.toString();

        if (isSelected){
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
            setBorder(new RoundBorder(borderColor, 10));
        }
        else{
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
        }

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(10,10);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //draws the rounded opaque panel with borders.
        g2d.setColor(getBackground());
        //paint background
        g2d.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = (getWidth() - fontMetrics.stringWidth(text)) / 2;
        int y = ((getHeight() - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g.drawString(text, x, y);
    }
}

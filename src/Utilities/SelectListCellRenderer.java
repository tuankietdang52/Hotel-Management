package Utilities;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class SelectListCellRenderer extends JLabel implements ListCellRenderer<Object> {
    private final Color borderColor;
    private String text;
    private boolean isSelected;

    public SelectListCellRenderer(Color borderColor){
        this.borderColor = borderColor;
        setOpaque(false);
        setFont(getFont().deriveFont(Font.BOLD, 20));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        text = value.toString();
        this.isSelected = isSelected;

        if (isSelected){
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
            setBorder(new RoundBorder(borderColor, 10));
        }
        else{
            setForeground(Color.WHITE);
            setBackground(list.getSelectionBackground());
            setBorder(new MatteBorder(1, 0, 1, 0, Color.blue));
        }

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isSelected){
            paintUnselected(g);
            return;
        }

        Dimension arcs = new Dimension(50,50);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color secondColor = new Color(33, 33, 33);

        GradientPaint gp = new GradientPaint(0, 0, Color.red, width, height, secondColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        drawText(g);
        g.dispose();
    }

    private void paintUnselected(Graphics g){
        drawText(g);
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
}

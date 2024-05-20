package Utilities;

import javax.swing.*;
import java.awt.*;

public class SearchField extends JTextField {
    private final String placeHolder;

    public SearchField(String placeHolder){
        this.placeHolder = placeHolder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground() );
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

        if (getText().isEmpty()) drawPlaceHolder(g);
        super.paintComponent(g);
    }

    private void drawPlaceHolder(Graphics g){
        setFont(getFont().deriveFont(Font.ITALIC, 20));

        Graphics2D g2d = (Graphics2D) g;

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = 10;
        int y = ((getHeight() - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        g.setColor(new Color(143, 143, 143));
        g.drawString(placeHolder, x, y);
    }
}

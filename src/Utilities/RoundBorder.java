package Utilities;

import javax.swing.border.Border;
import java.awt.*;

public class RoundBorder implements Border {
    private final Color borderColor;
    private final int radius;
    public RoundBorder(Color borderColor, int radius){
        this.borderColor = borderColor;
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius + 1, radius + 1, radius + 2, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(borderColor);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

package Utilities;

import javax.swing.*;
import java.awt.*;

public class ComboBoxRenderer extends DefaultListCellRenderer {
    private final Color color;

    public ComboBoxRenderer(Color color){
        this.color = color;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setFont(getFont().deriveFont(Font.PLAIN, 20));
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (isSelected){
            setBackground(color);
        }

        return this;
    }
}

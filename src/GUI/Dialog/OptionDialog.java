package GUI.Dialog;

import BUS.SupplierBUS;
import Utilities.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public abstract class OptionDialog<T> extends JDialog {
    protected JPanel contentPanel;
    protected JPanel dialogPanel;
    protected RoundButton saveButton;
    protected T current;
    protected boolean isAdjust = false;

    public OptionDialog(String title, Dimension size){
        setTitle(title);
        setup(size);

        add(dialogPanel);
    }

    public OptionDialog(String title, Dimension size, T current){
        this.current = current;
        isAdjust = true;

        setTitle(title);
        setup(size);
        writeData();

        add(dialogPanel);
    }

    protected void setup(Dimension size){
        setPreferredSize(size);
        setSize(size);
        setResizable(false);

        dialogPanel = new JPanel();
        dialogPanel.setPreferredSize(size);
        dialogPanel.setSize(getPreferredSize());

        dialogPanel.setBackground(new Color(255, 238, 225));
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        setModal(true);
        setLocationRelativeTo(null);
        setupContentPanel(size);

        setupButtonPanel();
        add(dialogPanel);
    }

    private void setupContentPanel(Dimension size){
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(size);
        contentPanel.setBackground(new Color(255, 238, 225));

        contentPanel.setLayout(new GridBagLayout());

        setupComponent();
        dialogPanel.add(contentPanel);
    }

    protected abstract void setupComponent();

    protected void setupButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(700, 200));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        buttonPanel.setBackground(new Color(255, 238, 225));

        buttonPanel.setLayout(new GridBagLayout());

        saveButton = new RoundButton(10, "Lưu");

        saveButton.setBackground(Color.green);
        saveButton.setForeground(Color.white);
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setMaximumSize(saveButton.getPreferredSize());

        buttonPanel.add(saveButton);
        dialogPanel.add(buttonPanel);
    }

    protected void addToContentPanel(Component component,
                                   int gridx,
                                   int gridy,
                                   int gridwidth,
                                   int gridheight,
                                   Insets insets){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.insets = insets;

        contentPanel.add(component, constraints);
    }

    protected JPanel createLabel(String labelText){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 45));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());
        panel.setBackground(new Color(255, 238, 225));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(200, 40));
        label.setMaximumSize(label.getPreferredSize());
        label.setForeground(Color.black);
        label.setHorizontalAlignment(SwingConstants.LEFT);

        panel.add(label);
        return panel;
    }

    protected JTextField createTextField(){
        JTextField textField = new JTextField();

        textField.setPreferredSize(new Dimension(300, 30));
        textField.setMaximumSize(textField.getPreferredSize());
        textField.setMinimumSize(textField.getPreferredSize());
        textField.setBackground(new Color(237, 221, 209));
        textField.setForeground(Color.black);
        textField.setFont(textField.getFont().deriveFont(Font.PLAIN, 15));
        textField.setBorder(null);

        return textField;
    }

    protected JTextArea createTextArea(){
        JTextArea textArea = new JTextArea();

        textArea.setPreferredSize(new Dimension(300, 90));
        textArea.setMaximumSize(textArea.getPreferredSize());
        textArea.setMinimumSize(textArea.getPreferredSize());
        textArea.setBackground(new Color(237, 221, 209));
        textArea.setForeground(Color.black);
        textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN, 15));
        textArea.setBorder(null);
        textArea.setLineWrap(true);

        return textArea;
    }

    protected JTextField createDateTextField(){
        JTextField formattedTextField = new JTextField(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(getBackground());

                if (!getText().isEmpty()) return;

                setFont(getFont().deriveFont(Font.ITALIC, 15));
                Graphics2D g2d = (Graphics2D) g;

                FontMetrics fontMetrics = g2d.getFontMetrics();
                int x = 10;
                int y = ((getHeight() - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

                g.setColor(new Color(143, 143, 143));
                g.drawString("Nhập theo định dạng yyyy-mm-dd", x, y);
            }
        };

        formattedTextField.setPreferredSize(new Dimension(300, 30));
        formattedTextField.setMaximumSize(formattedTextField.getPreferredSize());
        formattedTextField.setMinimumSize(formattedTextField.getPreferredSize());
        formattedTextField.setBackground(new Color(237, 221, 209));
        formattedTextField.setForeground(Color.black);
        formattedTextField.setFont(formattedTextField.getFont().deriveFont(Font.PLAIN, 15));
        formattedTextField.setBorder(null);

        return formattedTextField;
    }

    protected abstract void writeData();

    protected abstract T getModel();
}

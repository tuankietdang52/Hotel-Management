package GUI;

import Interface.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home implements IView {
    private JPanel homePanel;
    private JButton kakakaButton;

    public Home(){
        homePanel.setBackground(new Color(255, 238, 225));
    }

    @Override
    public JPanel GetPanel() {
        return homePanel;
    }
}

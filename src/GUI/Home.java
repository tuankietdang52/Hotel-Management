package GUI;

import Interface.IView;

import javax.swing.*;

public class Home implements IView {
    private JPanel homePanel;
    private JButton kakakaButton;

    @Override
    public JPanel GetPanel() {
        return homePanel;
    }
}

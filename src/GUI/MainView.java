package GUI;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private JPanel MainPanel;

    public void Setup(){
        CardLayout layout = new CardLayout();
        MainPanel.setLayout(layout);
        SetupContent();
    }

    private void SetupContent(){
        MainPanel.add(new Home().GetPanel(), "Home");
    }

    public void Display(){
        JFrame frame = new JFrame("Shiba");
        frame.setContentPane(MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(700, 700);

        ChangeContent("Home");
    }

    public void ChangeContent(String formName){
        CardLayout layout = (CardLayout) MainPanel.getLayout();
        layout.show(MainPanel, formName);
    }

    public static void main(String[] args) {
        MainView view = new MainView();
        view.Setup();
        view.Display();
    }
}

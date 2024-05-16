package GUI;

import Interface.IList;
import Utilities.ImageUtils;
import Utilities.RoundButton;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class MainView {
    //region GUI Field

    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel navigatePanel;
    private JButton homeButton;
    private JTextField searchBar;
    private RoundButton searchButton;
    private JPanel searchPanel;

    //endregion

    public void setup(){
        initNavigatePanel();
        initContentPanel();
        setupMainPanel();
    }

    private void initNavigatePanel(){
        navigatePanel = new JPanel();
        navigatePanel.setVisible(false);
        navigatePanel.setPreferredSize(new Dimension(1200, 100));

        setupNavigatePanel();
    }

    private void addComponentToGrid(@NotNull JPanel container,
                                    Component item,
                                    int gridx,
                                    int gridy,
                                    int gridwidth,
                                    int gridheight) {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = gridx;
        cons.gridy = gridy;
        cons.gridwidth = gridwidth;
        cons.gridheight = gridheight;
        cons.anchor = GridBagConstraints.CENTER;
        container.add(item, cons);
    }

    private void addComponentToGrid(@NotNull JPanel container,
                                        Component item,
                                        int gridx,
                                        int gridy,
                                        int gridwidth,
                                        int gridheight,
                                        int anchor,
                                        Insets insets) {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = gridx;
        cons.gridy = gridy;
        cons.gridwidth = gridwidth;
        cons.gridheight = gridheight;
        cons.anchor = anchor;
        cons.insets = insets;
        container.add(item, cons);
    }

    private void createHomeButton(){
        homeButton = new JButton();
        homeButton.setBorder(null);

        ImageIcon image = ImageUtils.loadImageResource(getClass(), "/Assets/logo.png");
        image = ImageUtils.resizeImage(image, 124, 50);

        homeButton.setPreferredSize(new Dimension(124, 50));
        homeButton.setIcon(image);
    }

    private void createSearchBar(){
        searchPanel = new JPanel();
        searchPanel.setVisible(false);
        GridBagLayout layout = new GridBagLayout();

        searchPanel.setLayout(layout);
        searchPanel.setPreferredSize(new Dimension(500, 100));

        searchBar = new JTextField();
        searchBar.setFont(searchBar.getFont().deriveFont(30f));
        searchBar.setPreferredSize(new Dimension(430, 50));

        ImageIcon image = ImageUtils.loadImageResource(getClass(), "/Assets/search.png");
        image = ImageUtils.resizeImage(image, 30, 30);

        searchButton = new RoundButton(10, image);
        searchButton.setPreferredSize(new Dimension(70, 50));
        searchButton.setBackground(new Color(255, 0, 0));

        addComponentToGrid(searchPanel, searchBar, 0, 0, 1, 1);
        addComponentToGrid(searchPanel, searchButton, 1, 0, 2, 1);

        searchPanel.setVisible(true);
    }

    private void setupNavigatePanel(){
        JPanel container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        container.setLayout(layout);
        container.setPreferredSize(new Dimension(1200, 100));

        createHomeButton();
        createSearchBar();

        addComponentToGrid(container, homeButton, 0, 0, 1, 0,
                GridBagConstraints.LINE_START, new Insets(0, 0, 0, 40));

        addComponentToGrid(container, searchPanel, 1, 0, 1, 0,
                GridBagConstraints.CENTER, new Insets(0, 0, 0, 40));

        navigatePanel.add(container);
    }

    private void initContentPanel(){
        contentPanel = new JPanel();
        contentPanel.setVisible(false);
        CardLayout layout = new CardLayout();
        contentPanel.setLayout(layout);
        setupContent();
    }

    private void setupContent(){
        contentPanel.add(new Home().getPanel(), "Home");
    }

    private void setupMainPanel(){
        BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);

        mainPanel.setLayout(layout);
        mainPanel.setSize(new Dimension(1200, 800));

        mainPanel.add(navigatePanel);
        navigatePanel.setVisible(true);
        mainPanel.add(contentPanel);
        contentPanel.setVisible(true);
    }

    private void setLookAndFeel(){
        try{

            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }
        catch (Exception ex){
            System.out.println("Cannot set look and feel");
        }
    }

    public void display(){
        setLookAndFeel();

        JFrame frame = new JFrame("Shiba");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1200, 900);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        changeContent("Home");
    }

    public void changeContent(String formName){
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, formName);
    }
}

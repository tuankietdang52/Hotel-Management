package GUI;

import Utilities.ImageUtils;
import Utilities.SelectListCellRenderer;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;


public class MainView {
    //region GUI Field

    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel navigatePanel;
    private JList<String> navigateList;

    //endregion

    // Size for content panel: 1000 900

    public MainView(){
        setupNavigatePanel();
        setupContentPanel();
        setupMainPanel();
    }

    private void setupContentPanel(){
        contentPanel = new JPanel();
        contentPanel.setVisible(false);
        contentPanel.setPreferredSize(new Dimension(1000, 900));
        contentPanel.setLayout(new CardLayout());

        setupContent();
    }

    private void setupContent(){
        contentPanel.add(new Home().getPanel(), "Home");
        contentPanel.add(new ProductGUI().getPanel(), "Sản phẩm");
        contentPanel.add(new EmployeeGUI().getPanel(), "Nhân viên");
        contentPanel.add(new CustomerGUI().getPanel(), "Khách hàng");
    }

    private void setupNavigatePanel(){
        navigatePanel = new JPanel();
        navigatePanel.setVisible(false);
        navigatePanel.setPreferredSize(new Dimension(200, 900));
        navigatePanel.setMaximumSize(navigatePanel.getPreferredSize());
        navigatePanel.setLayout(new BoxLayout(navigatePanel, BoxLayout.Y_AXIS));
        navigatePanel.setBackground(new Color(33, 33, 33));

        setupNavigateComponent();
    }

    private JPanel getHomeButtonPanel(){
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setSize(new Dimension(200, 100));
        homeButtonPanel.setMaximumSize(homeButtonPanel.getSize());
        homeButtonPanel.setMinimumSize(homeButtonPanel.getSize());
        homeButtonPanel.setBackground(new Color(33, 33, 33));
        homeButtonPanel.setLayout(new GridBagLayout());

        JButton homeButton = new JButton();
        homeButton.setBackground(new Color(33, 33, 33));
        homeButton.setBorder(null);

        ImageIcon image = ImageUtils.loadImageResource(getClass(), "/Assets/logo.png");
        image = ImageUtils.resizeImage(image, 124, 50);

        homeButton.setSize(new Dimension(124, 50));
        homeButton.setMaximumSize(homeButton.getPreferredSize());
        homeButton.setIcon(image);

        homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeButton.addActionListener(e -> {
            if (navigateList != null) navigateList.clearSelection();
            changeContent("Home");
        });

        homeButtonPanel.add(homeButton);

        return homeButtonPanel;
    }

    private JList<String> getNavigateList(String[] list){
        navigateList = new JList<>(list);

        SelectListCellRenderer renderer = new SelectListCellRenderer(Color.RED);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);
        navigateList.setCellRenderer(renderer);

        navigateList.setPreferredSize(new Dimension(200, 800));
        navigateList.setLayoutOrientation(JList.VERTICAL);
        navigateList.setVisibleRowCount(1);
        navigateList.setFixedCellHeight(70);
        navigateList.setFixedCellWidth(200);
        navigateList.setBackground(new Color(33, 33, 33));
        navigateList.setForeground(Color.white);
        navigateList.setSelectionForeground(Color.white);
        navigateList.setSelectionBackground(Color.red);
        navigateList.setSelectedIndex(-1);

        navigateList.addListSelectionListener(e -> changeContent(navigateList.getSelectedValue()));

        return navigateList;
    }

    private void setupNavigateComponent(){
        JPanel homeButtonPanel = getHomeButtonPanel();
        navigatePanel.add(homeButtonPanel);

        String[] list = {"Sản phẩm", "Nhân viên", "Khách hàng", "Nhà cung cấp", "Hóa đơn", "Phiếu nhập", "Khuyến mãi"};
        JList<String> navigateList = getNavigateList(list);
        navigatePanel.add(navigateList);
    }

    private void setupMainPanel(){
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setSize(new Dimension(1200, 800));
        mainPanel.setBackground(new Color(33, 33, 33));

        mainPanel.add(navigatePanel);
        mainPanel.add(contentPanel);
        navigatePanel.setVisible(true);
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

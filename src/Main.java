import DAO.SaleDAO;
import GUI.MainView;

public class Main {
    public static void main(String[] args) {
        SaleDAO a = new SaleDAO();

        MainView view = new MainView();
        view.setup();
        view.display();
    }
}
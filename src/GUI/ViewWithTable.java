package GUI;

import BUS.BaseBUS;
import Interface.IView;
import Utilities.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class ViewWithTable implements IView {
    //region GUI Field

    protected JPanel searchPanel;
    protected JScrollPane scrollTable;
    protected JTable dataTable;
    protected JTextField searchArea;
    private TableModelWithFilter tableModel;
    protected BaseBUS viewBUS;

    //endregion

    public ViewWithTable(){

    }

    public ViewWithTable(TableModelWithFilter tableModel, BaseBUS viewBUS){
        this.tableModel = tableModel;
        this.viewBUS = viewBUS;

        this.tableModel.setData(viewBUS.getDataTable());
    }

    protected void setupTableModel(TableModelWithFilter tableModel, BaseBUS viewBUS){
        this.tableModel = tableModel;
        this.viewBUS = viewBUS;
    }

    protected void setupSearchPanel(){
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setPreferredSize(new Dimension(1000, 50));
        searchPanel.setBackground(new Color(255, 238, 225));

        setupSearch();
    }

    protected void setupSearch(){
        searchArea = new SearchField("Search...");

        searchArea.setPreferredSize(new Dimension(600, 50));
        searchArea.setFont(searchArea.getFont().deriveFont(Font.PLAIN, 20));
        searchArea.setBorder(null);
        searchArea.setOpaque(false);
        searchArea.setBackground(new Color(237, 221, 209));

        setRowSowrter();
        searchPanel.add(searchArea);
    }

    protected void setRowSowrter(){
        TableModelWithFilter model = (TableModelWithFilter) dataTable.getModel();
        dataTable.setRowSorter(model.createTableFilter(searchArea));
    }

    protected void setupTable(){
        dataTable = new JTable(tableModel);
        dataTable.setSize(1000, 600);

        setupTableComponent();

        scrollTable = new JScrollPane(dataTable);
        scrollTable.setBackground(new Color(255, 238, 225));
        scrollTable.getViewport().setBackground(new Color(255, 238, 225));
        scrollTable.setPreferredSize(new Dimension(1000, 600));
    }

    public void setTableData(ArrayList<ArrayList<String>> data){
        TableModelWithFilter model = (TableModelWithFilter) dataTable.getModel();
        model.setData(data);
    }

    public void resetTableData(){
        ArrayList<ArrayList<String>> data = viewBUS.getDataTable();
        TableModelWithFilter model = (TableModelWithFilter) dataTable.getModel();
        model.setData(data);
    }

    protected abstract void setupTableComponent();
}

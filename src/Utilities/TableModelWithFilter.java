package Utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;

public abstract class TableModelWithFilter extends AbstractTableModel {
    private String[] cols;
    private ArrayList<ArrayList<String>> data;

    public void setCols(String[] cols){
        this.cols = cols;
    }

    public void addRow(ArrayList<String> row){
        data.add(row);
        fireTableRowsInserted(data.size(), data.size());
    }

    public void setData(ArrayList<ArrayList<String>> dataProduct){
        if (dataProduct == null){
            data = new ArrayList<>();
            return;
        }

        if (data != null) data.clear();
        data = dataProduct;

        System.out.println(data.size());
        fireTableDataChanged();
    }

    public TableRowSorter<TableModel> createTableFilter(JTextField searchArea){
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(this);

        searchArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchArea.getText();

                if (text.trim().isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchArea.getText();

                if (text.trim().isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = searchArea.getText();

                if (text.trim().isEmpty()) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        return rowSorter;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
}

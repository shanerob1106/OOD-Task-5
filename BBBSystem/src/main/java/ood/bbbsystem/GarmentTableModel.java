/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ood.bbbsystem;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author shane
 */
public class GarmentTableModel extends AbstractTableModel {

    private String[] columnNames = {"ID", "Make", "Name", "Colour", "Price", "Stock"};
    private List<Garment> garments;

    public GarmentTableModel(List<Garment> garments) {
        this.garments = garments;
    }

    @Override
    public int getRowCount() {
        return garments.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Garment garment = garments.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return garment.getId();
            case 1:
                return garment.getName();
            case 2:
                return garment.getMake();
            case 3:
                return garment.getColour();
            case 4:
                return garment.getFormattedPrice();
            case 5:
                return garment.getStock();
            default:
                return null;
        }
    }
}

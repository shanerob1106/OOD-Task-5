/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package ood.bbbsystem;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;

public class BoroBabyBoutique extends JFrame {

    // Array List of Garments
    private ArrayList<Garment> garments = new ArrayList<>();

    // GUI Components
    private JTable stockTable;
    private JTextArea descriptionArea;
    private JLabel imageLabel;
    private JButton addStockButton, sellStockButton, quitButton;

    private int max_stock = 5;
    private int min_stock = 0;

    public BoroBabyBoutique() {
        // Frame Setup
        setTitle("Boro Baby Boutique - Inventory Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());
        setResizable(false); // Prevent resizing

        // Load data from file
        loadData();

        // Create the Table & set model
        stockTable = new JTable(); // Configure table model later
        stockTable.setModel(new GarmentTableModel(garments));
        JScrollPane tableScrollPane = new JScrollPane(stockTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 400));

        // Row selection
        // Sets image and description to what is selected 
        stockTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Garment selectedGarment = garments.get(selectedRow);

                    // Update the image 
                    String imagePath = "images/" + selectedGarment.getId() + ".jpg";
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)));

                    // Update Description
                    descriptionArea.setText(selectedGarment.getDescription());

                    checkStock(selectedGarment.getStock());
                }

            }
        });

        // Set rows with a stock of less than 5 yellow
        stockTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int stock = (int) table.getModel().getValueAt(row, 7);
                if (stock < 5) {
                    c.setBackground(Color.YELLOW); // Make row yellow
                } else {
                    c.setBackground(Color.WHITE); // Normal row
                }
                return c;
            }
        });

        // Image and Description Panel
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Image Display
        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.add(imageLabel, BorderLayout.NORTH);

        // Description
        descriptionArea = new JTextArea("Description will appear here...");
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        rightPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        // Split the Center
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, rightPanel);
        splitPane.setDividerLocation(600); // Set split point (60% table, 40% right panel)
        splitPane.setResizeWeight(0.6); // Maintain ratio when resizing
        add(splitPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addStockButton = new JButton("Add Stock");
        sellStockButton = new JButton("Sell Item");
        quitButton = new JButton("Quit");
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);

        sellStockButton.setToolTipText("Please select a garment, before processing a sale. ");
        addStockButton.setToolTipText("Please select a garment, before adding to stock. ");
        quitButton.setToolTipText("Close and save the application. ");

        // Add stock button action
        addStockButton.addActionListener(e -> {
            int selectedRow = stockTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a garment to add stock.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Garment selectedGarment = garments.get(selectedRow);
            String input = JOptionPane.showInputDialog(this, "Enter quantity to add:");
            try {
                int quantity = Integer.parseInt(input);
                if (quantity <= 0 || quantity + selectedGarment.getStock() > 100) {
                    throw new NumberFormatException();
                }
                selectedGarment.incrementStock(quantity);
                stockTable.repaint(); // Refresh the table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a positive integer within stock limits.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sell stock button action
        sellStockButton.addActionListener(e -> {
            int selectedRow = stockTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a garment to sell.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Garment selectedGarment = garments.get(selectedRow);
            String input = JOptionPane.showInputDialog(this, "Enter quantity to sell:");
            try {
                int quantity = Integer.parseInt(input);
                if (quantity <= 0 || quantity > selectedGarment.getStock()) {
                    throw new NumberFormatException();
                }
                selectedGarment.decreaseStock(quantity);
                stockTable.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a positive integer within avalible stock", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Quit button action
        quitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit? ");
            if (confirm == JOptionPane.YES_OPTION) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("bbb.dat"))) {
                    for (Garment garment : garments) {
                        writer.write(String.join(",",
                                garment.getId(),
                                String.valueOf(garment.getPrice()),
                                garment.getMake(),
                                garment.getName(),
                                garment.getColour(),
                                garment.getDescription(),
                                garment.getMaterial(),
                                String.valueOf(garment.getStock())
                        ));
                        writer.newLine();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        buttonPanel.add(addStockButton);
        buttonPanel.add(sellStockButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Finalize Frame
        setVisible(true);
    }

    // loadData method
    private void loadData() {
        String filePath = "bbb.dat";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split each like CSV
                String[] tokens = line.split(",", 8);

                // Ensure correct number of tokens exist
                if (tokens.length != 8) {
                    System.out.println("Invalid data format: " + line);
                    continue;
                }

                // Order of bbb.dat file
                String id = tokens[0].trim();
                int price = Integer.parseInt(tokens[1].trim());
                String make = tokens[2].trim();
                String name = tokens[3].trim();
                String colour = tokens[4].trim();
                String description = tokens[5].trim();
                String material = tokens[6].trim();
                int stock = Integer.parseInt(tokens[7].trim());

                garments.add(new Garment(id, price, make, name, colour, description, material, stock));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void checkStock(int stock) {
        if (stock >= 5) {
            addStockButton.setVisible(false);
        } else {
            addStockButton.setVisible(true);
        }

        if (stock <= 0) {
            sellStockButton.setVisible(false);
        } else {
            sellStockButton.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoroBabyBoutique::new);
    }
}

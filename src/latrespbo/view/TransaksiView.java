/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package latrespbo.view;

import latrespbo.controller.TransaksiController;
import latrespbo.model.TransaksiModel;
import latrespbo.model.TransaksiModel.Transaction; 

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class TransaksiView extends JFrame{
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField fieldNamaPelanggan;
    private JTextField fieldNamaObat;
    private JTextField fieldHargaSatuan;
    private JTextField fieldJumlahBeli;

    private JButton addButton, deleteButton, editButton, updateButton, clearButton;

    private TransaksiController controller;
    private int currentEditingId = -1;

    public TransaksiView() {
        setTitle("Aplikasi Transaksi Penjualan Obat");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Transaksi"));

        inputPanel.add(new JLabel("Nama Pelanggan:"));
        fieldNamaPelanggan = new JTextField();
        inputPanel.add(fieldNamaPelanggan);

        inputPanel.add(new JLabel("Nama Obat:"));
        fieldNamaObat = new JTextField();
        inputPanel.add(fieldNamaObat);

        inputPanel.add(new JLabel("Harga Satuan:"));
        fieldHargaSatuan = new JTextField();
        inputPanel.add(fieldHargaSatuan);

        inputPanel.add(new JLabel("Jumlah Beli:"));
        fieldJumlahBeli = new JTextField();
        inputPanel.add(fieldJumlahBeli);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        addButton = new JButton("Tambah");
        deleteButton = new JButton("Hapus");
        editButton = new JButton("Edit");
        updateButton = new JButton("Update");
        clearButton = new JButton("Batal/Clear");
        
        updateButton.setEnabled(false);
        
        buttonsPanel.add(addButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{
            "ID", "Nama Pelanggan", "Nama Obat", "Harga Satuan", "Jumlah Beli", "Total Bayar"
        }, 0);

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String namaPelanggan = fieldNamaPelanggan.getText().trim();
                String namaObat = fieldNamaObat.getText().trim();
                int hargaSatuan = Integer.parseInt(fieldHargaSatuan.getText().trim());
                int jumlahBeli = Integer.parseInt(fieldJumlahBeli.getText().trim());

                if (namaPelanggan.isEmpty() || namaObat.isEmpty()) {
                    showMessage("Isi semua field dengan benar.");
                    return;
                }

                controller.addTransaction(namaPelanggan, namaObat, hargaSatuan, jumlahBeli);

                // Kosongkan field
                fieldNamaPelanggan.setText("");
                fieldNamaObat.setText("");
                fieldHargaSatuan.setText("");
                fieldJumlahBeli.setText("");

            } catch (NumberFormatException ex) {
                showMessage("Harga dan jumlah beli harus berupa angka.");
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                controller.deleteTransaction(id);
            } else {
                showMessage("Pilih transaksi yang ingin dihapus.");
            }
        });

        editButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                currentEditingId = (int) tableModel.getValueAt(selectedRow, 0);
                fieldNamaPelanggan.setText(tableModel.getValueAt(selectedRow, 1).toString());
                fieldNamaObat.setText(tableModel.getValueAt(selectedRow, 2).toString());
                fieldHargaSatuan.setText(tableModel.getValueAt(selectedRow, 3).toString());
                fieldJumlahBeli.setText(tableModel.getValueAt(selectedRow, 4).toString());

                addButton.setEnabled(false);
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                updateButton.setEnabled(true);
                // clearButton.setEnabled(true); // Tombol clear bisa diaktifkan saat mode edit
            } else {
                showMessage("Pilih transaksi yang ingin diedit dari tabel.");
            }
        });

        updateButton.addActionListener((ActionEvent e) -> {
            if (currentEditingId == -1) {
                showMessage("Tidak ada transaksi yang dipilih untuk diupdate. Klik Edit dulu.");
                return;
            }
            try {
                String namaPelanggan = fieldNamaPelanggan.getText().trim();
                String namaObat = fieldNamaObat.getText().trim();
                int hargaSatuan = Integer.parseInt(fieldHargaSatuan.getText().trim());
                int jumlahBeli = Integer.parseInt(fieldJumlahBeli.getText().trim());

                if (namaPelanggan.isEmpty() || namaObat.isEmpty()) {
                    showMessage("Nama pelanggan dan nama obat tidak boleh kosong.");
                    return;
                }
                 if (hargaSatuan <= 0 || jumlahBeli <= 0) {
                    showMessage("Harga satuan dan jumlah beli harus lebih dari 0.");
                    return;
                }

                controller.updateTransaction(currentEditingId, namaPelanggan, namaObat, hargaSatuan, jumlahBeli);
                resetFormState();

            } catch (NumberFormatException ex) {
                showMessage("Harga dan jumlah beli harus berupa angka.");
            }
        });

         clearButton.addActionListener((ActionEvent e) -> {
            resetFormState();
        });

    }

    private void resetFormState() {
        fieldNamaPelanggan.setText("");
        fieldNamaObat.setText("");
        fieldHargaSatuan.setText("");
        fieldJumlahBeli.setText("");

        currentEditingId = -1; // Reset ID yang diedit

        addButton.setEnabled(true);
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        updateButton.setEnabled(false); // Nonaktifkan tombol update
        // clearButton.setEnabled(false); // Jika clear hanya untuk mode edit

        table.clearSelection(); // Hapus seleksi pada tabel
    }

    public void setController(TransaksiController controller) {
        this.controller = controller;
    }

    public void showTransactions(ArrayList<TransaksiModel.Transaction> transactions) {
        tableModel.setRowCount(0); // clear table
        for (TransaksiModel.Transaction t : transactions) {
            tableModel.addRow(new Object[]{
                t.id,
                t.namaPelanggan,
                t.namaObat,
                t.hargaSatuan,
                t.jumlahBeli,
                t.getTotalBayar()
            });
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    

}

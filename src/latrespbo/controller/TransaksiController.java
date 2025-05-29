/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package latrespbo.controller;
import latrespbo.model.TransaksiModel;
import latrespbo.model.TransaksiModel.Transaction;
import latrespbo.view.TransaksiView;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class TransaksiController {
    private TransaksiModel model;
    private TransaksiView view;

    public TransaksiController(TransaksiModel model, TransaksiView view) {
        this.model = model;
        this.view = view;
        view.setController(this);
        loadTransactionList();
    }

    public void loadTransactionList() {
        ArrayList<TransaksiModel.Transaction> transactions = model.getAllTransactions();
        view.showTransactions(transactions); // sekarang harus menerima list of Transaction
    }

    public void addTransaction(String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        if (model.addTransaction(namaPelanggan, namaObat, hargaSatuan, jumlahBeli)) {
            view.showMessage("Transaksi berhasil ditambahkan!");
            loadTransactionList();
        } else {
            view.showMessage("Gagal menambahkan transaksi.");
        }
    }

    public void deleteTransaction(int id) {
        if (model.deleteTransaction(id)) {
            view.showMessage("Transaksi berhasil dihapus!");
            loadTransactionList();
        } else {
            view.showMessage("Gagal menghapus transaksi.");
        }
    }

    public void updateTransaction(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        if (model.updateTransaction(id, namaPelanggan, namaObat, hargaSatuan, jumlahBeli)) {
            view.showMessage("Transaksi berhasil diperbarui!");
            loadTransactionList();
        } else {
            view.showMessage("Gagal memperbarui transaksi.");
        }
    }
}

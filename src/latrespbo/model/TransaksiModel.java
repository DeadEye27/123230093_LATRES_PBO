/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package latrespbo.model;
import java.sql.*;
import java.util.ArrayList;
import latrespbo.database.Koneksi;
/**
 *
 * @author Lenovo
 */
public class TransaksiModel {
    // Representasi transaksi
    public static class Transaction {
        public int id;
        public String namaPelanggan;
        public String namaObat;
        public int hargaSatuan;
        public int jumlahBeli;

        public Transaction(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
            this.id = id;
            this.namaPelanggan = namaPelanggan;
            this.namaObat = namaObat;
            this.hargaSatuan = hargaSatuan;
            this.jumlahBeli = jumlahBeli;
        }

        public int getTotalBayar() {
            int total = hargaSatuan * jumlahBeli;
            if (jumlahBeli > 5) {
                total -= total * 0.1;
            }
            return total;
        }
    }

    // Ambil semua transaksi
    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM transaksi";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                    rs.getInt("id"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_obat"),
                    rs.getInt("harga_satuan"),
                    rs.getInt("jumlah_beli")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Tambah transaksi
    public boolean addTransaction(String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "INSERT INTO transaksi (nama_pelanggan, nama_obat, harga_satuan, jumlah_beli) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaPelanggan);
            stmt.setString(2, namaObat);
            stmt.setInt(3, hargaSatuan);
            stmt.setInt(4, jumlahBeli);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hapus transaksi
    public boolean deleteTransaction(int id) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "DELETE FROM transaksi WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Edit transaksi
    public boolean updateTransaction(int id, String namaPelanggan, String namaObat, int hargaSatuan, int jumlahBeli) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "UPDATE transaksi SET nama_pelanggan = ?, nama_obat = ?, harga_satuan = ?, jumlah_beli = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaPelanggan);
            stmt.setString(2, namaObat);
            stmt.setInt(3, hargaSatuan);
            stmt.setInt(4, jumlahBeli);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

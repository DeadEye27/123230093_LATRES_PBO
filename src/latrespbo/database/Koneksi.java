/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package latrespbo.database;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Lenovo
 */
public class Koneksi {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null){
            try {
                String url = "jdbc:mysql://localhost:3306/latrespbo";
                String user = "root";
                String password = "";
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi Berhasil");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Koneksi Gagal: " + e.getMessage());
                return null;
            }
        }
        return conn;
    }


}

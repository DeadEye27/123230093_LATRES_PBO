/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package latrespbo;
import latrespbo.controller.TransaksiController;
import latrespbo.model.TransaksiModel;
import latrespbo.view.TransaksiView;

/**
 *
 * @author Lenovo
 */
public class LatresPBO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TransaksiModel model = new TransaksiModel();
            TransaksiView view = new TransaksiView();
            new TransaksiController(model, view);
            view.setVisible(true);
        });
    }
    
}

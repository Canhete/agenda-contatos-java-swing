/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author ueg
 */
public class AgendaApp {
    public static void main(String[] args) {
       javax.swing.SwingUtilities.invokeLater(() -> {
           AgendaView view = new AgendaView();
           AgendaModel model = new AgendaModel();
           new AgendaController(view, model);
           
           view.setVisible(true);
       });
    }
}

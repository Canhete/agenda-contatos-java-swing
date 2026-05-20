/**
* @author canhete
*/

// Agenda App:
// Inicializa, simplesmente instancia os módulos e os chama pra execução

public class AgendaApp {
    public static void main(String[] args) {
       javax.swing.SwingUtilities.invokeLater(() -> {
           AgendaView view = new AgendaView();
           AgendaModel model = new AgendaModel();
           new AgendaController(view, model);
           
           // Cria um JFrame para a agenda
           javax.swing.JFrame frame = new javax.swing.JFrame("Agenda");
           frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
           frame.add(view);
           frame.pack();
           frame.setLocationRelativeTo(null);
           frame.setVisible(true);
       });
    }
}

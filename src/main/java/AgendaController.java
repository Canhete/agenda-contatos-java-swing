
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author ueg
 */
public class AgendaController {
    private final AgendaView view;
    private final AgendaModel model;
    
    public AgendaController(AgendaView view, AgendaModel model) {
        this.view = view;
        this.model = model;
        
        atualizarView();
        configurarEventos();
    }
    
    private void adicionarContato() {
        ContatoDialog dialog = new ContatoDialog(view, null);
        dialog.setVisible(true);
        if (dialog.isConfirmado()) {
            model.novoContato(dialog.getNome(), dialog.getTelefone(), dialog.getEmail());
            atualizarView();
        }
    }
    
    private void editarContato() {
        int id = view.getIdSelecionado();
        if (id == -1) return;
        Contato c = model.getTodosContatos().stream()
            .filter(ct -> ct.getId() == id).findFirst().orElse(null);
        if (c == null) return;

        ContatoDialog dialog = new ContatoDialog(view, c);
        dialog.setVisible(true);
        if (dialog.isConfirmado()) {
            model.atualizarContato(id, dialog.getNome(), dialog.getTelefone(), dialog.getEmail());
            atualizarView();
        }
    }
    
    private void removerContato() {
        int id = view.getIdSelecionado();
        if (id == -1) return;
        int resp = JOptionPane.showConfirmDialog(view,
            "Deseja remover este contato?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            model.removerContato(id);
            atualizarView();
        }
    }
    
    private void buscarContato() {
        String termo = view.getCampoBusca().getText();
        view.atualizarTabela(model.buscarContato(termo));
    }
    
    private void atualizarView() {
        view.atualizarTabela(model.getTodosContatos());
    }
    
    private void configurarEventos() {
        view.getBtnAdicionar().addActionListener(e -> adicionarContato());
        view.getBtnEditar().addActionListener(e -> editarContato());
        view.getBtnRemover().addActionListener(e -> removerContato());
        view.getBtnBuscar().addActionListener(e -> buscarContato());

        // Habilita botões quando linha é selecionada
        view.getTabelaContatos().getSelectionModel().addListSelectionListener(
            (ListSelectionEvent e) -> {
                boolean selecionado = view.getIdSelecionado() != -1;
                view.getBtnEditar().setEnabled(selecionado);
                view.getBtnRemover().setEnabled(selecionado);
            }
        );

        // Busca ao pressionar Enter
        view.getCampoBusca().addActionListener(e -> buscarContato());
    }
}

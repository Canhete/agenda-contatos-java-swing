
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ueg
 */
public class AgendaModel {
    private final List<Contato> contatos = new ArrayList<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);
    
    public void adicionarContato(Contato c) {
        if (c.getId() == 0) {
            c.setId(proximoId.getAndIncrement());
        }
        
        contatos.add(c);
    }
    
    public Contato novoContato(String nome, String tel, String email) {
        Contato c = new Contato(proximoId.getAndIncrement(), nome, tel, email);
        contatos.add(c);
        return c;
    }
    
    public void removerContato(int id) {
        contatos.removeIf(c -> c.getId() == id);
    }
    
    public void atualizarContato(int id, String nome, String tel, String email) {
        contatos.stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .ifPresent(c -> {
                c.setNome(nome);
                c.setTelefone(tel);
                c.setEmail(email);
            });
    }
    
    public List<Contato> buscarContato(String termo) {
        if (termo == null || termo.isBlank()) {
            return getTodosContatos();
        }
        
        String t = termo.toLowerCase();
        
        return contatos.stream()
            .filter(c -> c.getNome().toLowerCase().contains(t)
                      || c.getEmail().toLowerCase().contains(t)
                      || c.getTelefone().contains(t))
            .collect(Collectors.toList());
    }
    
    public List<Contato> getTodosContatos() {
        return new ArrayList<>(contatos);
    }
}

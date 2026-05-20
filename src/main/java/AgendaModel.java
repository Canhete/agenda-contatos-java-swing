import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// Agenda Model:
// Gerencia a parte lógica do programa, não sabe nada visual

public class AgendaModel {
    private final List<Contato> contatos = new ArrayList<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);
    
    // Nome do arquivo
    private static final String ARQUIVO = "contatos.csv";
    
    // Toda vez que é iniciado carrega o arquivo
    public AgendaModel() {
        carregarArquivo();
    }
    
    public void adicionarContato(String nome, String telefone, String email) {
        Contato c = new Contato(proximoId.getAndIncrement(), nome, telefone, email);
        
        contatos.add(c);
        salvar();
    }
    
    public void removerContato(int id) {
        contatos.removeIf(c -> c.getId() == id);
        
        salvar();
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
        
        salvar();
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
    
    // Carrega dados do arquivo
    private void carregarArquivo() {
        try {
            List<Contato> carregados = PersistenciaContato.ler(ARQUIVO);
            contatos.addAll(carregados);
            
            // Garante id único
            carregados.stream()
                    .mapToInt(Contato::getId)
                    .max()
                    .ifPresent(max -> proximoId.set(max + 1)); // Pega maior id e salva o próximo id depois desse
            
        } catch (java.io.FileNotFoundException e) {
            // Caso o arquivo não exista, ignora, na primeira vez é normal isso acontecer
        } catch (java.io.IOException e) {
            System.err.println("Erro ao carregar csv: " + e.getMessage());
        }
    }
    
    private void salvar() {
        try {
            PersistenciaContato.gravar(ARQUIVO, contatos);
        } catch (java.io.IOException e) {
            System.out.println("Erro ao salvar csv: " + e.getMessage());
        }
    }
}

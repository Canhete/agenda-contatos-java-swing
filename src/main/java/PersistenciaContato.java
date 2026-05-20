import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Persistência Contato:
// Responsável por guardar localmente os dados da agenda num arquivo .csv

public class PersistenciaContato { 
    public static void gravar(String arquivo, List<Contato> contatos) throws IOException {
        try(var escritor = new BufferedWriter(new FileWriter(arquivo))) {
            // Cabeçalho
            escritor.write("id;nome;telefone;email\n");
            
            // Escreve cada contato no arquivo
            for (Contato c : contatos) {
                escritor.write("%d;%s;%s;%s\n"
                        .formatted(c.getId(), c.getNome(), c.getTelefone(), c.getEmail())
                );
            }
        }
    }
    
    public static List<Contato> ler(String arquivo) throws IOException {
        List<Contato> lista = new ArrayList<>();
        
        try(var leitor = new BufferedReader(new FileReader(arquivo))) {
            // Pula o cabeçalho
            leitor.readLine();
            
            String linha;
            
            while ((linha = leitor.readLine()) != null) {
                var partes = linha.split(";"); // Separador é ;
                
                // Pega cada campo e instancia novo contato na lista;
                lista.add(new Contato(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3]
                ));
            }
        }
        
        return lista;
    }
}

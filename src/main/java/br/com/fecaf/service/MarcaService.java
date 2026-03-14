package br.com.fecaf.service;

import br.com.fecaf.model.Marca;
import br.com.fecaf.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listarTodas() {
        return marcaRepository.findAll();
    }

    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada com id: " + id));
    }

    public Marca salvar(Marca marca) {
        return marcaRepository.save(marca);
    }

    public void deletar(Long id) {
        Marca marca = buscarPorId(id);
        // Verifica se existem modelos associados (pode lançar exceção)
        if (!marca.getModelos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir marca com modelos associados.");
        }
        marcaRepository.deleteById(id);
    }
}
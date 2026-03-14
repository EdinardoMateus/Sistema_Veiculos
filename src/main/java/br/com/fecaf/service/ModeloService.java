package br.com.fecaf.service;

import br.com.fecaf.model.Modelo;
import br.com.fecaf.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    public List<Modelo> listarTodos() {
        return modeloRepository.findAll();
    }

    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado com id: " + id));
    }

    public Modelo salvar(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

    public void deletar(Long id) {
        Modelo modelo = buscarPorId(id);
        if (!modelo.getVeiculos().isEmpty()) {
            throw new RuntimeException("Não é possível excluir modelo com veículos associados.");
        }
        modeloRepository.deleteById(id);
    }
}
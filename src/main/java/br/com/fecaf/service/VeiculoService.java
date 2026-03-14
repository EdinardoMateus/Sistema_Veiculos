package br.com.fecaf.service;

import br.com.fecaf.model.StatusVeiculo;
import br.com.fecaf.model.Veiculo;
import br.com.fecaf.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com id: " + id));
    }

    public Veiculo salvar(Veiculo veiculo) {
        if (veiculo.getId() == null) {
            veiculo.setDataCadastro(LocalDate.now());
        }
        return veiculoRepository.save(veiculo);
    }

    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }

    // Método de filtro dinâmico usando Specification - VERSÃO CORRIGIDA
    public List<Veiculo> filtrar(String marca, String modelo, Integer anoMin, Integer anoMax,
                                 BigDecimal precoMin, BigDecimal precoMax, StatusVeiculo status) {

        Specification<Veiculo> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por marca (nome da marca contém)
            if (marca != null && !marca.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("modelo").get("marca").get("nome")),
                        "%" + marca.toLowerCase() + "%"
                ));
            }

            // Filtro por modelo (nome do modelo contém)
            if (modelo != null && !modelo.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("modelo").get("nome")),
                        "%" + modelo.toLowerCase() + "%"
                ));
            }

            // Filtro por ano (mínimo e máximo)
            if (anoMin != null && anoMax != null) {
                predicates.add(criteriaBuilder.between(root.get("anoFabricacao"), anoMin, anoMax));
            } else if (anoMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("anoFabricacao"), anoMin));
            } else if (anoMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("anoFabricacao"), anoMax));
            }

            // Filtro por preço (mínimo e máximo)
            if (precoMin != null && precoMax != null) {
                predicates.add(criteriaBuilder.between(root.get("preco"), precoMin, precoMax));
            } else if (precoMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin));
            } else if (precoMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMax));
            }

            // Filtro por status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return veiculoRepository.findAll(spec);
    }
}
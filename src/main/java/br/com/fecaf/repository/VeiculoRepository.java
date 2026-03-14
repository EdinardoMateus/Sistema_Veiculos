package br.com.fecaf.repository;

import br.com.fecaf.model.StatusVeiculo;
import br.com.fecaf.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {
    List<Veiculo> findByStatus(StatusVeiculo status);
    List<Veiculo> findByModeloId(Long modeloId);
    List<Veiculo> findByAnoFabricacao(Integer ano);
    List<Veiculo> findByPrecoBetween(BigDecimal min, BigDecimal max);
}
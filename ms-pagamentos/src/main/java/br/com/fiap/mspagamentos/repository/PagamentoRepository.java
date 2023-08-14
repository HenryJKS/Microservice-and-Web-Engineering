package br.com.fiap.mspagamentos.repository;

import br.com.fiap.mspagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query(value = "SELECT * FROM TB_PAGAMENTO WHERE ID = ?",nativeQuery = true)
    Optional<Pagamento> findById(Long id);
}

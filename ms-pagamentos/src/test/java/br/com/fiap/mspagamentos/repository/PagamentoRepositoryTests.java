package br.com.fiap.mspagamentos.repository;

import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class PagamentoRepositoryTests {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private Long existingId;
    private Long notExistingId;
    private Long countTotalPagamento;

    //vai ser executado antes de cada teste
    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        notExistingId = 10L;
        countTotalPagamento = 2L;
    }

    @Test
    @DisplayName("Deveria excluir pagamento quando o ID existe")
    public void deleteShouldDeleteObjectWhenIdExists() {
        // Act
        pagamentoRepository.deleteById(existingId);

        //Assert
        Optional<Pagamento> result = pagamentoRepository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deveria lançar EmptyResultDataAccessException quando o ID não existe")
    public void deleteDeveriaLancarEmptyResultDataAccessExceptionQuandoIdNaoExiste() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            pagamentoRepository.deleteById(notExistingId);
        });
    }

    @Test
    @DisplayName("save Deveria salvar objeto com auto incremento quando id é nulo")
    public void saveShouldPeristWithAutoIncrementWhenIdIsNull() {
        Pagamento pagamento = Factory.createPagamento();
        pagamento.setId(null);
        pagamento = pagamentoRepository.save(pagamento);
        Assertions.assertNotNull(pagamento.getId());
        // verifica se é o proximo ID
        Assertions.assertEquals(countTotalPagamento + 1, pagamento.getId());
    }

    @Test
    @DisplayName("findById deveria retornar um Optional<Pagamento> não vazio quando o id existir")
    public void returnOptionalPaymentNotEmptyWhenIdExist() {
        pagamentoRepository.findById(existingId);

        Optional<Pagamento> pagamento = pagamentoRepository.findById(existingId);

        Assertions.assertFalse(pagamento.isEmpty());
    }

    @Test
    @DisplayName("findById deve retornar um Optional<Pagamento> vazio quando o id não existir")
    public void returnOptinalPaymentEmptyWhenIdNotExist() {
        pagamentoRepository.findById(notExistingId);

        Optional<Pagamento> pagamento = pagamentoRepository.findById(notExistingId);

        Assertions.assertTrue(pagamento.isEmpty());
    }


}

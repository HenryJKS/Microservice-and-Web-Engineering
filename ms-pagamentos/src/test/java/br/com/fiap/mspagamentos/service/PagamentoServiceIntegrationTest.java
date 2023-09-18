package br.com.fiap.mspagamentos.service;

import br.com.fiap.mspagamentos.repository.PagamentoRepository;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PagamentoServiceIntegrationTest {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private Long idExsite;
    private Long idNaoExiste;
    private Long totalRegistros;

    @BeforeEach
    void setup() throws Exception {
        idExsite = 1L;
        idNaoExiste = 50L;
        totalRegistros = 5L;
    }

    @Test
    @DisplayName("Deve deletar um pagamento pelo id")
    public void deleteShouldDeletePaymentWhenIdExists() throws Exception {
        // Act
        pagamentoService.delete(idExsite);

        Assertions.assertEquals(totalRegistros - 1, pagamentoRepository.count());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar deletar um pagamento com id inexistente")
    public void deleteDeveLancarExcecaoQuandoIdNaoExiste() throws Exception {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            pagamentoService.delete(idNaoExiste);
        });
    }

    @Test
    @DisplayName("Deve listar todos pagamentos e retornar status 200")
    public void findAllDeveListarTodosPagamentos() {
        var resultado = pagamentoService.findAll();
        Assertions.assertFalse(resultado.isEmpty());
        Assertions.assertEquals(totalRegistros, resultado.size());
        Assertions.assertEquals(Double.valueOf(1200.00), resultado.get(0).getValor().doubleValue());
        Assertions.assertEquals("Amadeus", resultado.get(1).getNome());
//        Assertions.assertEquals(null, resultado.get(5).getNome());
    }
}
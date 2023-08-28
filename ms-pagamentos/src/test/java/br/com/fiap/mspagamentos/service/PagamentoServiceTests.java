package br.com.fiap.mspagamentos.service;

import br.com.fiap.mspagamentos.repository.PagamentoRepository;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PagamentoServiceTests {

    // referenciando PagamentoService
    // @Autowired - Sem injeção de dependência
    // Mock
    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    // preparando dados
    private Long existingId;
    private Long notExistingId;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        notExistingId = 10L;

        //configurando comportamento simulado usando Mockito - obj Mockado
        Mockito.doNothing().when(pagamentoRepository).deleteById(existingId);
        Mockito.when(pagamentoRepository.existsById(existingId)).thenReturn(true);
        Mockito.when(pagamentoRepository.existsById(notExistingId)).thenReturn(false);
    }

    @Test
    @DisplayName(" delete deveria não fazer nada quando id existir")
    public void DeleteReturnNothingWhenIdExist() {
        Assertions.assertDoesNotThrow(() -> {
            pagamentoService.delete(existingId);
        });

        // times, para declarar quantas vezes executar o método
        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    @DisplayName("Teste delete lança ResourceNotFoundException quando id não existe")
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            pagamentoService.delete(notExistingId);
        });
    }


}

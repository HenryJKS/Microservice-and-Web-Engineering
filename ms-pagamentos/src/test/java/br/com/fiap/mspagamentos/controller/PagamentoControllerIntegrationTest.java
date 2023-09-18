package br.com.fiap.mspagamentos.controller;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import br.com.fiap.mspagamentos.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PagamentoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private Long idExiste;
    private Long idNaoExiste;
    private Long totalRegistros;
    private PagamentoDTO pagamentoDTO;

    // converter para JSON o objeto java e enviar na requisição
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception{
        idExiste = 1L;
        idNaoExiste = 50L;
        totalRegistros = 5L;
        pagamentoDTO = Factory.createPagamentoDTO();
    }

    @Test
    @DisplayName("Deve listar todos os pagamentos e retornar status 200")
    public void findAllShouldListAllPagamentos() throws Exception {
        // chamando requisição
        mockMvc.perform(get("/pagamentos").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").isString())
                .andExpect(jsonPath("$[0].nome").value("Nicodemus"));
//                .andExpect(jsonPath("$[5].status").value("CRIADO"));
    }

    @Test
    @DisplayName("Deve retornar um pagamento pelo ID e com status 200")
    public void findByIdShouldFindPaymentById() throws Exception {
        // chamando requisição
        ResultActions resultActions = mockMvc.perform(get("/pagamentos/{id}", idExiste)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(jsonPath("$.id").value(idExiste));
        resultActions.andExpect(jsonPath("$.nome").isString());
        resultActions.andExpect(jsonPath("$.nome").value("Nicodemus"));
    }

    @Test
    @DisplayName("findById deve retornar not found quando id não existe")
    public void findByIdReturnNotFoundWhenIdNotExist() throws Exception {
        // chamando requisição
        ResultActions resultActions = mockMvc.perform(delete("/pagamentos/{id}", idNaoExiste)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());
        resultActions.andDo(print());
    }

    @Test
    @DisplayName("Deve salvar um pagamento, retornar status 201 e Location no Header")
    public void insertDeveSalvarPagament() throws Exception {
        Pagamento pagamento = Factory.createPagamento();
        pagamento.setId(null);

        String corpoJson = objectMapper.writeValueAsString(pagamento);

        mockMvc.perform(post("/pagamentos")
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.nome").value("Bach"));
    }

    @Test
    @DisplayName("Deve salvar um pagamento com os campos obrigatorios, " +
    "retornar status 201 e Location Header")
    public void insertDeveSalvarPagamentoCamposObrigatorios() throws Exception {
        Pagamento pagamento = new Pagamento(null, BigDecimal.valueOf(25.25), "Teste", "123456789", "07/25",
                "123", null, 1L, 2L);

        String corpoJson = objectMapper.writeValueAsString(pagamento);

        mockMvc.perform(post("/pagamentos")
                        .content(corpoJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.nome").isNotEmpty());

    }

    @Test
    @DisplayName("Insert deve lançar exception quando inseir dados inválidos e retornar status 422")
    public void insertDeveLancarExceptionQuandoInserirDadosInvalidos() throws Exception {
        Pagamento pagamento = new Pagamento();
        pagamento.setFormaDePagamentoId(1L);
        pagamento.setValor(BigDecimal.valueOf(25.25));

        String corpoJson = objectMapper.writeValueAsString(pagamento);

        mockMvc.perform(post("/pagamentos")
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andDo(print());
    }

    @Test
    @DisplayName("update deve atualizar pagamento quando Id existe e retornar status 200")
    public void updateDeveAtualizarPagamentoQuandoExiste() throws Exception {
        Pagamento pagamento = Factory.createPagamento();
        pagamento.setNome("Teste");

        String corpoJson = objectMapper.writeValueAsString(pagamento);

        ResultActions resultActions = mockMvc.perform(put("/pagamentos/{id}", idExiste)
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.valor").exists());
        resultActions.andExpect(jsonPath("$.nome").value("Teste"));
    }

    @Test
    @DisplayName("update deve retornar notfound quando id não existe")
    public void updateDeveRetornarNotFoundQuandoIdNaoExiste() throws Exception {
        Pagamento pagamento = Factory.createPagamento();
        pagamento.setNome("Teste");

        String corpoJson = objectMapper.writeValueAsString(pagamento);

        ResultActions resultActions = mockMvc.perform(put("/pagamentos/{id}", idNaoExiste)
                .content(corpoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());
        resultActions.andDo(print());
    }

    @Test
    @DisplayName("Delete deve retornar NoContent Quando IdExiste, status 204")
    public void deleteDeveRetornarNoContentQuandoIdExiste() throws Exception {
            ResultActions resultActions = mockMvc.perform(delete("/pagamentos/{id}", idExiste));
            resultActions.andExpect(status().isNoContent());
            resultActions.andDo(print());
    }

    @Test
    @DisplayName("Delete deve retornar NoContent Quando nao Existe, status 404")
    public void deleteDeveRetornarNotFoundQuandoIdNaoExiste() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/pagamentos/{id}", idNaoExiste));
        resultActions.andExpect(status().isNotFound());
        resultActions.andDo(print());
    }

    @Test
    @DisplayName("Deve alterar status para CONFIRMADO quando id Existe, status 200")
    public void patchDeveAlterarStatusParaConfirmadoQuandoIdExiste() throws Exception{

        mockMvc.perform(patch("/pagamentos/{id}/confirmar", idExiste)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Deve alterar status para CONFIRMADO quando id Existe, status 200")
    public void patchDeveAlterarStatusParaCanceladoQuandoIdExiste() throws Exception{

        mockMvc.perform(patch("/pagamentos/{id}/cancelado", idExiste)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Confirmar Deve retornar 404 quando Id não existe")
    public void confirmarDeveRetornar404QuandoIdNaoExiste() throws Exception {
        mockMvc.perform(patch("/pagamentos/{id}/confirmar", idNaoExiste)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("Cancelado Deve retornar 404 quando Id não existe")
    public void canceladoDeveRetornar404QuandoIdNaoExiste() throws Exception {
        mockMvc.perform(patch("/pagamentos/{id}/cancelado", idNaoExiste)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }



}

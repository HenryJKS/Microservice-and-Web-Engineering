package br.com.fiap.mspagamentos.controller;
import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Status;
import br.com.fiap.mspagamentos.service.PagamentoService;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import br.com.fiap.mspagamentos.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTests {

    //declarações
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagamentoService pagamentoService;
    private PagamentoDTO pagamentoDTO;
    private Long idExiste = 1L;
    private Long idNaoExiste = 10L;

    //Converter para JSON o objeto java para enviar na requisição
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {
        //criando um PagamentoDTO
        pagamentoDTO = Factory.createPagamentoDTO();

        //listando PagamentoDTO
        List<PagamentoDTO> list = List.of(pagamentoDTO);

        //simulando o comportamento do service
        when(pagamentoService.findAll()).thenReturn(list);

        //findbyid
        when(pagamentoService.findById(idExiste)).thenReturn(pagamentoDTO);
        // para findby id nao existe
        when(pagamentoService.findById(idNaoExiste)).thenThrow(ResourceNotFoundException.class);

        //insert
        //any qualquer objeto
        when(pagamentoService.insert(any())).thenReturn(pagamentoDTO);

        //update
        when(pagamentoService.update(eq(idExiste), any())).thenReturn(pagamentoDTO);
        when(pagamentoService.update(eq(idNaoExiste), any())).thenThrow(ResourceNotFoundException.class);

        //delete
        doNothing().when(pagamentoService).delete(idExiste);
        doThrow(ResourceNotFoundException.class).when(pagamentoService).delete(idNaoExiste);

    }

    @Test
    @DisplayName("findAll deveria retornar uma lista do tipo PagamentoDTO")
    public void findAllDeveriaRetornarUmaListaDoTipoPagamentoDTO() throws Exception {
        // chamando uma requisição com método get no caminho /pagamentos
        ResultActions resultActions = mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

        resultActions.andDo(print());
    }

    @Test
    @DisplayName("findbyid deve retornar pagamentoDTO quando ID existe")
    public void findByIdDeveRetornarPagamentoDTOQuandoIdExiste() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/pagamentos/{id}", idExiste)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        //analisa se tem os campos na resposta
        // $ - acessar o objeto da reposta
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.valor").exists());
        resultActions.andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("FindById deve retornar not found quando ID não existe")
    public void findByIdDeveRetornarNotFoundQuandoIDNaoExiste() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/pagamentos/{id}", idNaoExiste)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Insert deve retornar created PagamentoDTO com ID")
    public void insertDeveRetornarCreatedPagamentoDTOComId() throws Exception {
        // retornar 201
        PagamentoDTO pagamentoDTO = new PagamentoDTO(null, BigDecimal.valueOf(32.25), "Bach", "322345698",
                "07/25", "547", Status.CRIADO, 1L, 2L);

        String bodyJson = objectMapper.writeValueAsString(pagamentoDTO);

        ResultActions resultActions = mockMvc.perform(post("/pagamentos")
                .content(bodyJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isCreated());
        resultActions.andDo(print());
        resultActions.andExpect(header().exists("Location"));
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.valor").exists());
        resultActions.andExpect(jsonPath("$.status").exists());

    }

    @Test
    @DisplayName("update deve retornar PagamentoDTO quando IdExiste")
    public void updateDeveRetornarPagamentoDTOQuandoIdExiste() throws Exception {
        //precisamos passar o corpo da requisição
        //obejto JSON, converter obj com Bean ObjectMapper
        String bodyJson = objectMapper.writeValueAsString(pagamentoDTO);

        ResultActions resultActions = mockMvc.perform(put("/pagamentos/{id}", idExiste)
                .content(bodyJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.valor").exists());
        resultActions.andExpect(jsonPath("$.status").exists());

    }

    @Test
    @DisplayName("update deve retornar PagamentoDTO quando IdNaoExiste")
    public void updateDeveRetornarPagamentoDTOQuandoIdNaoExiste() throws Exception {
        //precisamos passar o corpo da requisição
        //obejto JSON, converter obj com Bean ObjectMapper
        String bodyJson = objectMapper.writeValueAsString(pagamentoDTO);

        ResultActions resultActions = mockMvc.perform(put("/pagamentos/{id}", idNaoExiste)
                .content(bodyJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());
        resultActions.andDo(print());

    }

    @Test
    @DisplayName("Delete deve retornar NoContent quando id existe")
    public void deteleDeveRetornarNoContentQuandoIdExiste() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/pagamentos/{id}", idExiste)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent());
        resultActions.andDo(print());
    }

    @Test
    @DisplayName("Delete deve retornar NoContent quando id nao existe")
    public void deteleDeveRetornarNoContentQuandoIdNaoExiste() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/pagamentos/{id}", idNaoExiste)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());
        resultActions.andDo(print());
    }

}


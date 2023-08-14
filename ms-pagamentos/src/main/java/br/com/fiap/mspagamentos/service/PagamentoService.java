package br.com.fiap.mspagamentos.service;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;
import br.com.fiap.mspagamentos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAll() {

       return repository.findAll().stream().map(PagamentoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(long id) {
        // Pode ou não encontrar registro
        Optional<Pagamento> result = repository.findById(id);
        Pagamento pagamento = result.get();
        // converter pagamento para dto
        PagamentoDTO dto = new PagamentoDTO(pagamento);
        return dto;
    }

    //método auxiliar para converter DTO para pagamento
    public void copyDtoToPagamento(PagamentoDTO dto, Pagamento pagamento) {
        pagamento.setValor(dto.getValor());
        pagamento.setNome(dto.getNome());
        pagamento.setNumeroDoCartao(dto.getNumeroDoCartao());
        pagamento.setValidade(dto.getValidade());
        pagamento.setCodigo(dto.getCodigo());
        pagamento.setStatus(dto.getStatus());
        pagamento.setPedidoId(dto.getPedidoId());
        pagamento.setFormaDePagamentoId(dto.getFormaDePagamentoId());
        pagamento.setStatus(Status.CRIADO);
    }

    @Transactional
    public PagamentoDTO insert (PagamentoDTO dto) {
        Pagamento pagamento = new Pagamento();
        copyDtoToPagamento(dto, pagamento);
        pagamento = repository.save(pagamento);
        return new PagamentoDTO(pagamento);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}

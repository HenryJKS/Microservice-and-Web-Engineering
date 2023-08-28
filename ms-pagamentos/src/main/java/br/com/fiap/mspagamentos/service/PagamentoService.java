package br.com.fiap.mspagamentos.service;

import br.com.fiap.mspagamentos.dto.PagamentoDTO;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;
import br.com.fiap.mspagamentos.repository.PagamentoRepository;
import br.com.fiap.mspagamentos.service.exception.DatabaseException;
import br.com.fiap.mspagamentos.service.exception.ResourceNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado ID: " + id));
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
    public PagamentoDTO update(Long id, PagamentoDTO dto) {
        try {
            Pagamento pagamento = repository.getReferenceById(id);
            // chamando metodo de conversao de dto para entity
            copyDtoToPagamento(dto, pagamento);
            pagamento = repository.save(pagamento);
            return new PagamentoDTO(pagamento);
        } catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException("Não encontrado o ID: " + id);

        }
    }

    @Transactional
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Recurso não encontrado");
        }
    }

    @Transactional
    public void confirmPayment(Long id) {
        // Recupera o pagamento do DB
        Optional<Pagamento> pagamento = repository.findById(id);

        // valida se existe
        if (!pagamento.isPresent()) {
            throw new ResourceNotFoundException("ID não encontrado");
        }
        // seta o status do pagamento como confirmado
        pagamento.get().setStatus(Status.CONFIRMADO);

        // salva alteração no DB
        repository.save(pagamento.get());
    }

    @Transactional
    public void cancelPayment(Long id) {
        // Recupera o pagamento no DB
        Optional<Pagamento> pagamento = repository.findById(id);

        // valida se existe
        if(!pagamento.isPresent()) {
            throw new ResourceNotFoundException("ID não encontrado");
        }

        // se o status do pagamento
        pagamento.get().setStatus(Status.CANCELADO);

        // salva  alteracao
        repository.save(pagamento.get());
    }

}

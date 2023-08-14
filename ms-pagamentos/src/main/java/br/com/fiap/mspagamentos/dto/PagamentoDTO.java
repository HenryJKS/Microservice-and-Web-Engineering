package br.com.fiap.mspagamentos.dto;
import br.com.fiap.mspagamentos.model.Pagamento;
import br.com.fiap.mspagamentos.model.Status;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PagamentoDTO {

    private Long id;

    private BigDecimal valor;

    private String nome; //nome

    private String numeroDoCartao; //numero

    private String validade; //data de validade

    private String codigo; //cod seguranca

    private Status status;

    private Long pedidoId;

    public Long getId() {
        return id;
    }

    private Long formaDePagamentoId; // 1 - cartao 2 - dinheiro

    public PagamentoDTO() {

    }

    public PagamentoDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.valor = pagamento.getValor();
        this.nome = pagamento.getNome();
        this.numeroDoCartao = pagamento.getNumeroDoCartao();
        this.validade = pagamento.getValidade();
        this.codigo = pagamento.getCodigo();
        this.status = pagamento.getStatus();
        this.pedidoId = pagamento.getPedidoId();
        this.formaDePagamentoId = pagamento.getFormaDePagamentoId();
    }

    // o PagamentoDTO serve para receber os dados do front e enviar para o backend
    public PagamentoDTO(BigDecimal valor, String nome, String numeroDoCartao, String validade, String codigo, Status status, Long pedidoId, Long formaDePagamentoId) {
        this.valor = valor;
        this.nome = nome;
        this.numeroDoCartao = numeroDoCartao;
        this.validade = validade;
        this.codigo = codigo;
        this.status = status;
        this.pedidoId = pedidoId;
        this.formaDePagamentoId = formaDePagamentoId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public void setNumeroDoCartao(String numeroDoCartao) {
        this.numeroDoCartao = numeroDoCartao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getFormaDePagamentoId() {
        return formaDePagamentoId;
    }

    public void setFormaDePagamentoId(Long formaDePagamentoId) {
        this.formaDePagamentoId = formaDePagamentoId;
    }

}

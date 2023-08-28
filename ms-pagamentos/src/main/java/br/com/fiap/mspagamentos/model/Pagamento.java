package br.com.fiap.mspagamentos.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;
@Entity
@Table(name = "tb_pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Campo requerido")
    @Positive(message = "O valor deve ser um numero positivo")
    private BigDecimal valor;
    @NotNull(message = "Tamanho máximo do campo: Até 150 caracteres")
    private String nome; //nome
    @NotNull(message = "Tamanho máximo do campo: Até 20 caracteres")
    private String numeroDoCartao; //numero
    @NotNull(message = "Tamanho máximo do campo: Até 07 caracteres")
    private String validade; //data de validade
    @NotNull(message = "Tamanho máximo do campo: Até 03 caracteres")
    private String codigo; //cod seguranca
    @NotNull(message = "Campo Obrigatorio")
    @Enumerated(EnumType.STRING) // definido que é string
    private Status status;

    @NotNull(message = "Campo requerido")
    @Positive
    private Long pedidoId;
    @NotNull(message = "Campo requerido")
    @Positive
    private Long formaDePagamentoId; // 1 - cartao 2 - dinheiro

    public Pagamento() {
    }

    // esse metodo serve para receber os dados do front e enviar para o backend
    public Pagamento(BigDecimal valor, String nome, String numeroDoCartao, String validade, String codigo, Status status, Long pedidoId, Long formaDePagamentoId) {
        super();
        this.valor = valor;
        this.nome = nome;
        this.numeroDoCartao = numeroDoCartao;
        this.validade = validade;
        this.codigo = codigo;
        this.status = status;
        this.pedidoId = pedidoId;
        this.formaDePagamentoId = formaDePagamentoId;

    }
    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento)) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(getId(), pagamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

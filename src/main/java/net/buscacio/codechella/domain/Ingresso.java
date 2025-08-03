package net.buscacio.codechella.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("ingressos")
public class Ingresso {

    @Id
    private Long id;
    private Long eventoId;
    private Integer totalIngressos;
    private Integer ingressosDisponiveis;
    private Double preco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public Integer getTotalIngressos() {
        return totalIngressos;
    }

    public void setTotalIngressos(Integer totalIngressos) {
        this.totalIngressos = totalIngressos;
    }

    public Integer getIngressosDisponiveis() {
        return ingressosDisponiveis;
    }

    public void setIngressosDisponiveis(Integer ingressosDisponiveis) {
        this.ingressosDisponiveis = ingressosDisponiveis;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}

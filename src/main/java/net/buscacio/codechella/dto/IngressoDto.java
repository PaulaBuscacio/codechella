package net.buscacio.codechella.dto;


import net.buscacio.codechella.domain.Ingresso;

public record IngressoDto(Long id,
                          Long eventoId,
                          Integer totalIngressos,
                          Integer ingressosDisponiveis,
                          Double preco) {

    public static IngressoDto toDto(Ingresso ingresso) {
        return new IngressoDto(ingresso.getId(), ingresso.getEventoId(), ingresso.getTotalIngressos(), ingresso.getIngressosDisponiveis(), ingresso.getPreco());
    }

    public Ingresso toEntity() {
        Ingresso ingresso = new Ingresso();
        ingresso.setId(this.id);
        ingresso.setEventoId(this.eventoId);
        ingresso.setTotalIngressos(this.totalIngressos);
        ingresso.setIngressosDisponiveis(this.ingressosDisponiveis);
        ingresso.setPreco(this.preco);
        return ingresso;
    }
}
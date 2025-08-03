package net.buscacio.codechella.dto;


import net.buscacio.codechella.domain.Ingresso;
import net.buscacio.codechella.domain.TipoEvento;
import net.buscacio.codechella.domain.Evento;

import java.time.LocalDate;

public record EventoDto(Long id,
                        TipoEvento tipo,
                        String nome,
                        LocalDate data,
                        String descricao,
                        String ingressosDisponiveis) {

    public static EventoDto toDto(Evento evento) {
        return new EventoDto(evento.getId(), evento.getTipo(), evento.getNome(),
                evento.getData(), evento.getDescricao(), null);
    }

    public Evento toEntity() {
        Evento evento = new Evento();
        evento.setId(this.id);
        evento.setNome(this.nome);
        evento.setTipo(this.tipo);
        evento.setData(this.data);
        evento.setDescricao(this.descricao);
        return evento;
    }

    public String setIngressosDisponiveis(String ingressosDisponiveis) {
        return this.ingressosDisponiveis;
    }
}
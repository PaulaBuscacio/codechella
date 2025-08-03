package net.buscacio.codechella.service;

import net.buscacio.codechella.domain.TipoEvento;
import net.buscacio.codechella.domain.Evento;
import net.buscacio.codechella.dto.EventoDto;
import net.buscacio.codechella.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repositorio;

    @Autowired
    private IngressoService ingressoService;

    public Flux<EventoDto> obterTodos() {
        return repositorio.findAll().map(EventoDto::toDto);
    }


    public Mono<EventoDto> obterPorId(Long id) {
        //todo: inserir mens no eventoDto e trasnsformar o eventoDto em um Mono<EventoDto>
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .flatMap(evento ->
                        ingressoService.obterIngressosDisponiveis(id)
                                .map(ingressosDisponiveis -> {
                                    EventoDto dto = EventoDto.toDto(evento);
                                    return new EventoDto(dto.id(),
                                            dto.tipo(),
                                            dto.nome(),
                                            dto.data(),
                                            dto.descricao(),
                                            ingressosDisponiveis);
                                }));

    }

    public Mono<EventoDto> cadastrar(EventoDto dto) {
        return repositorio.save(dto.toEntity())
                .map(EventoDto::toDto);
    }

    public Mono<Void> excluir(Long id) {
        return repositorio.findById(id)
                .flatMap((repositorio::delete));
    }

    public Mono<EventoDto> atualizar(Long id, EventoDto dto) {

        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .flatMap(eventoExistente -> {
                    Evento eventoAtualizado = dto.toEntity();
                    eventoAtualizado.setId(id);
                    return repositorio.save(eventoAtualizado);
                })
                .map(EventoDto::toDto);

    }

    public Flux<EventoDto> obterPorTipo(String tipo) {
        return repositorio.findByTipo(TipoEvento.valueOf(tipo.toUpperCase(Locale.ROOT)))
                .map(EventoDto::toDto);
    }
}

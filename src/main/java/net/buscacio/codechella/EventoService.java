package net.buscacio.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repositorio;

    public Flux<EventoDto> obterTodos() {
        return repositorio.findAll().map(EventoDto::toDto);
    }


    public Mono<EventoDto> obterPorId(@PathVariable Long id) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .map(EventoDto::toDto);

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
}

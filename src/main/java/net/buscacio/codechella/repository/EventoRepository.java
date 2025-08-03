package net.buscacio.codechella.repository;

import net.buscacio.codechella.domain.TipoEvento;
import net.buscacio.codechella.domain.Evento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {

    Flux<Evento> findByTipo(TipoEvento tipoEvento);
}

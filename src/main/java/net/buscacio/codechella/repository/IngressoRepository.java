package net.buscacio.codechella.repository;

import net.buscacio.codechella.domain.Ingresso;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IngressoRepository extends ReactiveCrudRepository<Ingresso, Long> {

    Mono<Ingresso> findByEventoId(Long id);
}

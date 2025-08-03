package net.buscacio.codechella.service;

import net.buscacio.codechella.dto.IngressoDto;
import net.buscacio.codechella.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IngressoService {


    @Autowired
    private IngressoRepository repositorio;


    public Flux<IngressoDto> obterTodos() {
        return repositorio.findAll().map(IngressoDto::toDto);
    }


    public Mono<IngressoDto> obterPorEventoId(@PathVariable Long id) {
        return repositorio.findByEventoId(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .map(IngressoDto::toDto);

    }

    public Mono<IngressoDto> cadastrar(IngressoDto dto) {
        return repositorio.save(dto.toEntity())
                .map(IngressoDto::toDto);
    }


    public Mono<IngressoDto> comprar(Long id, int quantidade) {

        return repositorio.findByEventoId(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingresso para não encontrado para este evento.")))
                .flatMap(ingressoExistente -> {
                    ingressoExistente.setIngressosDisponiveis(
                            ingressoExistente.getIngressosDisponiveis() - quantidade);
                    if (ingressoExistente.getIngressosDisponiveis() < 0) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de ingressos indisponível."));
                    }
                    return repositorio.save(ingressoExistente);
                })
                .map(IngressoDto::toDto);
    }

    public Mono<String> obterIngressosDisponiveis(Long id) {
        return obterPorEventoId(id)
                .map(dto -> "Restam " + dto.ingressosDisponiveis() + " ingressos disponíveis para o evento.");
    }




}

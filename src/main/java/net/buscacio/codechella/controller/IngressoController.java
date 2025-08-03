package net.buscacio.codechella.controller;

import net.buscacio.codechella.dto.EventoDto;
import net.buscacio.codechella.dto.IngressoDto;
import net.buscacio.codechella.service.EventoService;
import net.buscacio.codechella.service.IngressoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

//todo: acertar o endpoint de obter ingressos por evento
    private final IngressoService servico;
    private final Sinks.Many<IngressoDto> eventoSink;

    public IngressoController(IngressoService servico) {
        this.servico = servico;
        this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping
    public Flux<IngressoDto> obterTodos() {
        return servico.obterTodos();
    }

    @GetMapping("/evento/{id}")
    public Mono<IngressoDto> obterPorId(@PathVariable Long id) {
        return servico.obterPorEventoId(id);
    }

    @PostMapping
    public Mono<IngressoDto> cadastrar(@RequestBody IngressoDto dto) {
        return servico.cadastrar(dto)
                .doOnSuccess(eventoSink::tryEmitNext);
    }

    @PutMapping("/evento/{id}")
    public Mono<IngressoDto> comprar(@PathVariable Long id, @RequestParam int quantidade) {
        return servico.comprar(id, quantidade);

    }

    @GetMapping(value = "/evento/{id}/disponiveis", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> obterIngressosDisponiveis(@PathVariable Long id) {
        return servico.obterIngressosDisponiveis(id)
                .delayElement(Duration.ofSeconds(4)); // Simula um atraso para o exemplo
    }
}

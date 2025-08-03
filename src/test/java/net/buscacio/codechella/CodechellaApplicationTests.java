package net.buscacio.codechella;

import net.buscacio.codechella.domain.TipoEvento;
import net.buscacio.codechella.dto.EventoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodechellaApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCadastrarEvento() {

        EventoDto dto = new EventoDto(null, TipoEvento.SHOW, "Teatro Rock", LocalDate.parse("2025-10-16"), "É rock e teatro", null);
        webTestClient.post()
                .uri("/eventos")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EventoDto.class)
                .value(response -> {
                    assertNotNull(response.id(), "O ID do evento não pode ser nulo");
                    assertEquals(dto.tipo(), response.tipo(), "O tipo do evento deve ser SHOW");
                    assertEquals(dto.nome(), response.nome(), "O nome do evento deve ser 'Teatro Rock'");
                    assertEquals(dto.data(), response.data(), "A data do evento deve ser 2025-10-16");
                    assertEquals(dto.descricao(), response.descricao(), "A descrição do evento deve ser 'É rock e teatro'");

                });
    }

    @Test
    void testObterEventoTodos() {

        webTestClient.get()
                .uri("/eventos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EventoDto.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    void testObterPorId() {

        webTestClient.get()
                .uri("/eventos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EventoDto.class)
                .value(response -> {
                    EventoDto eventoResponse = response.get(2);
                    assertEquals(TipoEvento.CONCERTO, eventoResponse.tipo(), "O tipo do evento deve ser Concerto");
                    assertEquals(3, eventoResponse.id(), "O ID do evento deve ser 3");
                } );
    }

}

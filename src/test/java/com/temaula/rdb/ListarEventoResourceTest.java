package com.temaula.rdb;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ListarEventoResourceTest {

    @Transactional
    public EventoRegistrado adicionarEvento() {
        final var novoEvento = new Evento();
        novoEvento.nome = UUID.randomUUID().toString();
        novoEvento.descricao = UUID.randomUUID().toString();
        novoEvento.periodoVigencia = Periodo.of(LocalDate.now(), LocalDate.now().plusDays(1));
        novoEvento.persist();
        return new EventoRegistrado(novoEvento);
    }

    @Transactional
    @BeforeAll
    @AfterAll
    public static void removerEventos() {
        Evento.removerTodos();
    }

    @Test
    @DisplayName("GET /eventos -> deve retornar 200 e retornar o uma lista de eventos registrados")
    public void deveRetornar200() {

        List<EventoRegistrado> eventoRegistrados = listarEventosRegistrados();

        Assertions.assertTrue(eventoRegistrados.isEmpty(),"deveria retornar uma lista vazia");

        EventoRegistrado eventoRegistrado=adicionarEvento();

        eventoRegistrados = listarEventosRegistrados();

        Assertions.assertFalse(eventoRegistrados.isEmpty(), "não deveria retornar uma lista vazia");

        Assertions.assertTrue(eventoRegistrados.stream()
                .anyMatch(eventoRegistrado::equals));
    }

    private List<EventoRegistrado> listarEventosRegistrados() {
        return given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .get("/eventos")
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .as(new TypeRef<List<EventoRegistrado>>() {
                });
    }
}

package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ListarItemEventoResourceTest {

    Faker faker;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        faker = new Faker();
        ItemEvento.deleteAll();
        Evento.removerTodos();
        Item.removerTodos();
    }


    @AfterEach
    @Transactional
    public void afterEach() {
        ItemEvento.deleteAll();
        Evento.removerTodos();
        Item.removerTodos();
    }

    @Test
    @DisplayName("GET /eventos/{eventoId}/itens -> deve retornar 200 em case em que o evento registrado não tem itens" +
            " relacionados")
    public void deveRetornar200_case1() {
        Evento evento = novoEvento();
        final var items = listarItemEventos(evento);
        assertThat(items, empty());
    }


    @Test
    @DisplayName("GET /eventos/{eventoId}/itens -> deve retornar 200 em case em que o evento registrado tem itens " +
            "relacionados")
    public void deveRetornar200_case2() {
        Evento evento = novoEvento();
        Item item1 = novoItemEvento(evento, novoItem()).item;
        Item item2 = novoItemEvento(evento, novoItem()).item;
        Item item3 = novoItemEvento(evento, novoItem()).item;

        final var items = listarItemEventos(evento);

        assertThat(items, hasSize(3));
        assertThat(items, containsInAnyOrder(item1, item2, item3));
    }

    private List<Item> listarItemEventos(Evento evento) {
        return given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .get("/eventos/{eventoId}/itens", Map.of("eventoId", evento.id))
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .as(new TypeRef<List<Item>>() {
                });
    }

    @Transactional
    protected ItemEvento novoItemEvento(Evento evento, Item item) {
        return ItemEvento.criarItemEvento(
                evento,
                item
        );
    }

    @Transactional
    protected Evento novoEvento() {
        return Evento.criarEvento(
                faker.artist().name(),
                faker.book().title(),
                Periodo.of(
                        LocalDate.now(),
                        LocalDate.now().plusDays(1)
                )
        );
    }

    @Transactional
    protected Item novoItem() {
        return Item.criarItem(
                faker.book().title(),
                BigDecimal.valueOf(
                        faker.number().randomDouble(
                                2,
                                0,
                                1000
                        )
                )
        );
    }


}
package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
class RemoverItemEventoResourceTest {
    Faker faker;
    ItemEvento itemEvento;

    @Transactional
    @BeforeEach
    public void setup() {
        ItemEvento.deleteAll();
        Evento.removerTodos();
        Item.removerTodos();
        faker = new Faker();
        Item item = Item.criarItem(
                faker.book().title(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 0, 100))
        );

        Evento evento = Evento.criarEvento(
                faker.name().firstName(),
                faker.artist().name(),
                Periodo.of(LocalDate.now(), LocalDate.now().plusDays(1))
        );

        itemEvento = ItemEvento.criarItemEvento(
                evento,
                item
        );
    }

    @Transactional
    @AfterEach
    public void limparTudo() {
        ItemEvento.deleteAll();
        Evento.removerTodos();
        Item.removerTodos();
    }

    @Test
    @DisplayName("DELETE /eventos/{eventoId}/itens/{itemId} -> deve retornar 204 quando for removido o relacionamento" +
            " evento x item")
    void deveRetornar204() {
        given()
                .log().ifValidationFails()
                .when()
                .delete("/eventos/{eventoId}/itens/{itemId}",
                        Map.of(
                                "eventoId", itemEvento.evento.id,
                                "itemId", itemEvento.item.id))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }


    @Test
    @DisplayName("DELETE /eventos/{eventoId}/itens/{itemId} -> deve retornar 404 quando o item ou o evento ou o " +
            "relacionamento não existir")
    void deveRetornar404() {
        given()
                .log().ifValidationFails()
                .when()
                .delete("/eventos/{eventoId}/itens/{itemId}",
                        Map.of(
                                "eventoId", faker.number().randomNumber(),
                                "itemId", faker.number().randomNumber()))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
        given()
                .log().ifValidationFails()
                .when()
                .delete("/eventos/{eventoId}/itens/{itemId}",
                        Map.of(
                                "eventoId", faker.number().randomNumber(),
                                "itemId", itemEvento.item.id))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
        given()
                .log().ifValidationFails()
                .when()
                .delete("/eventos/{eventoId}/itens/{itemId}",
                        Map.of(
                                "eventoId", itemEvento.evento.id,
                                "itemId", faker.number().randomNumber()))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}
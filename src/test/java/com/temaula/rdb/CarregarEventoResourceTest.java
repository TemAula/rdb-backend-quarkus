package com.temaula.rdb;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Random;

import static com.temaula.rdb.AdicionarEventoResourceTest.pegarEventoRegistradoValido;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CarregarEventoResourceTest {


    @Transactional
    @AfterAll
    public static void removerEventos() {
        Evento.deleteAll();
    }

    @Test
    @DisplayName("GET /eventos/{eventoId} -> deve retornar 200 e o evento registrado")
    public void deveRetornar200() {

        EventoRegistrado eventoRegistrado = pegarEventoRegistradoValido();

        EventoRegistrado eventoPesquisado =
                given()
                        .log().ifValidationFails()
                        .when()
                        .accept(ContentType.JSON)
                        .get("/eventos/{eventoId}", Map.of("eventoId", eventoRegistrado.id))
                        .then()
                        .log().ifValidationFails()
                        .statusCode(Response.Status.OK.getStatusCode())
                        .extract()
                        .as(new TypeRef<EventoRegistrado>() {
                        });

        assertThat(eventoPesquisado, equalTo(eventoRegistrado));

    }

    @Test
    @DisplayName("GET /eventos/{eventoId} -> deve retornar 404 quando pesquisar evento que não está registrado")
    public void deveRetornar404() {
        given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .get("/eventos/{eventoId}", Map.of("eventoId", new Random().nextLong()))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
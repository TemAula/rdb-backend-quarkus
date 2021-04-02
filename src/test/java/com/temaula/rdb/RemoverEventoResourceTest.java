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
public class RemoverEventoResourceTest {


    @Transactional
    @AfterAll
    public static void removerEventos() {
        Evento.removerTodos();
    }

    @Test
    @DisplayName("DELETE /eventos/{eventoId} -> deve retornar 202")
    public void deveRetornar200() {

        EventoRegistrado eventoRegistrado = pegarEventoRegistradoValido();

        given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .delete("/eventos/{eventoId}", Map.of("eventoId", eventoRegistrado.id))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.ACCEPTED.getStatusCode());

        given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .delete("/eventos/{eventoId}", Map.of("eventoId", eventoRegistrado.id))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());


    }

    @Test
    @DisplayName("DELETE /eventos/{eventoId} -> deve retornar 404 o evento informado não está registrado")
    public void deveRetornar404() {
        given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .delete("/eventos/{eventoId}", Map.of("eventoId", new Random().nextLong()))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
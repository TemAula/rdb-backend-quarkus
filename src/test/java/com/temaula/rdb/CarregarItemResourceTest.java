package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.Map;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class CarregarItemResourceTest {
    private Faker faker;
    private Item itemRegistrado;

    @AfterEach
    @Transactional
    public void removerTodosOsItens() {
        Item.deleteAll();
    }

    @BeforeEach
    @Transactional
    public void beforeEach() {
        faker = new Faker();
        Item.deleteAll();
        itemRegistrado = Item
            .create(
                faker.book().title(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 0, 100))
            );
    }

    @Test
    @DisplayName(
        "GET /itens/{id} -> deve retornar 404 quando consultar um id de um item não cadastrado")
    public void deveRetornar404() {
        given()
            .log().ifValidationFails()
            .when()
            .accept(ContentType.JSON)
            .get("/itens/{id}", Map.of("id", faker.number().randomNumber()))
            .then()
            .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("GET /itens/{id} -> deve retornar 200 e retornar o item registrado")
    public void deveRetornar200() {
        Assertions.assertEquals(
            itemRegistrado,
            given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .get("/itens/{id}", Map.of("id", itemRegistrado.id))
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract()
                .as(Item.class)
        );
    }
}

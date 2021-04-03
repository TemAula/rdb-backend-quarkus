package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.Map;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class RemoverItemResourceTest {
    static Faker faker;
    static Item itemRegistrado;

    @Transactional
    @BeforeAll
    public static void setup() {
        Item.removerTodos();
        faker = new Faker();
        itemRegistrado = Item.criarItem(
            faker.book().title(),
            BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000))
        );
    }

    @Transactional
    @AfterAll
    public static void removerItens() {
        Item.removerTodos();
    }

    @Test
    @DisplayName(
        "DELETE /itens/{id} -> deve retornar 202 caso o item registrado foi corretamente deletado")
    public void deveRetornar204() {
        given()
            .log().ifValidationFails()
            .when()
            .accept(ContentType.JSON)
            .delete("/itens/{id}", Map.of("id", itemRegistrado.id))
            .then()
            .log().ifValidationFails()
            .statusCode(Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @DisplayName(
        "DELETE /itens/{id} -> deve retornar 404 caso o não exista o item com o id informado")
    public void deveRetornar404() {
        given()
            .log().ifValidationFails()
            .when()
            .accept(ContentType.JSON)
            .delete("/itens/{id}", Map.of("id", faker.number().randomNumber()))
            .then()
            .log().ifValidationFails()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}
package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

@QuarkusTest
public class ListarItemResourceTest {
    Faker faker;

    @BeforeEach
    @AfterEach
    @Transactional
    public void removerTodosOsItens() {
        Item.removerTodos();
    }

    @BeforeEach
    public void beforeEach() {
        faker = new Faker();
    }

    @Transactional
    protected Item adicionarItem() {
        return Item
                .criarItem(
                        faker.book().title(),
                        BigDecimal.valueOf(faker.number().randomDouble(2, 0, 100))
                );
    }

    @Test
    @DisplayName("GET /itens -> deve retornar 200 e retornar o uma lista de itens registrados")
    public void deveRetornar200() {

        List<Item> itensRegistrados = listarItems();

        Assertions.assertTrue(itensRegistrados.isEmpty(), "deveria retornar uma lista vazia");

        Item itemAdicionado = adicionarItem();

        itensRegistrados = listarItems();

        Assertions.assertFalse(itensRegistrados.isEmpty(), "não deveria retornar uma lista vazia");

        assertThat(itensRegistrados, contains(itemAdicionado));
    }

    private List<Item> listarItems() {
        return given()
                .log().ifValidationFails()
                .when()
                .accept(ContentType.JSON)
                .get("/itens")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .as(new TypeRef<List<Item>>() {
                });
    }
}

package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class CriarItemEventoResourceTest {

    static Faker faker;

    static Evento eventoRegistrado;
    static Item itemRegistrado;

    @BeforeAll
    @Transactional
    public static void beforeAll() {
        faker = new Faker();
        Evento.deleteAll();
        Item.deleteAll();

        eventoRegistrado = Evento.criarEvento(
                faker.book().title(),
                faker.book().author(),
                Periodo.of(LocalDate.now(), LocalDate.now()));

        itemRegistrado = Item.criarItem(
                faker.name().fullName(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000))
        );

    }

    @AfterAll
    @Transactional
    public static void afterAll() {
        ItemEvento.deleteAll();
        Evento.deleteAll();
        Item.deleteAll();
    }

    @BeforeEach
    @Transactional
    public void beforeEach() {
        ItemEvento.deleteAll();
    }

    @AfterEach
    @Transactional
    public void afterEach() {
        ItemEvento.deleteAll();
    }

    @Test
    @DisplayName("POST /eventos/{eventoId}/itens -> deve retornar 200 e retornar o item relacionado")
    public void deveRetornar200() {
        Item itemRelacionado = given()
                .log().ifValidationFails()
                .when()
                .contentType(ContentType.JSON)
                .body(itemRegistrado)
                .post("/eventos/{eventoId}/itens", Map.of("eventoId", eventoRegistrado.id))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(Item.class);

        assertThat(itemRegistrado, equalTo(itemRelacionado));
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("POST /eventos/{eventoId}/itens -> deve retornar 404 em caso de falha")
    @MethodSource("deveRetornar404Args")
    public void deveRetornar404(
            final String desc,
            final Map<String, Boolean> body
    ) {
        given()
                .log().ifValidationFails()
                .when()
                .contentType(ContentType.JSON)
                .body(Map.of("id", body.get("itemId") ? itemRegistrado.id : faker.number().randomNumber()))
                .post("/eventos/{eventoId}/itens", Map.of("eventoId", body.get("eventoId") ? eventoRegistrado.id : faker
                        .number()
                        .randomNumber()))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    public static Stream<Arguments> deveRetornar404Args() {
        return Stream.of(
                Arguments.of(
                        "quando eventoId informado for inválido",
                        Map.of(
                                "eventoId", false,
                                "itemId", true
                        )
                ),
                Arguments.of(
                        "quando id do item informado for inválido",
                        Map.of(
                                "eventoId", true,
                                "itemId", false
                        )
                )
        );
    }

}
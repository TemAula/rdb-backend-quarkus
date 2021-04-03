package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

@QuarkusTest
public class EditarItemResourceTest {
    static Faker faker = new Faker();
    Item itemRegistrado;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        Item.removerTodos();
        itemRegistrado = Item
            .criarItem(
                faker.book().title(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 0, 100))
            );
    }

    @AfterEach
    @Transactional
    public void afterEach() {
        Item.removerTodos();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("PUT /itens -> deve retornar 200 e retornar o item persistido e atualizado")
    @MethodSource("deveRetornar200Args")
    public void deveRetornar200(final String desc, final Map<?, ?> body) {
        given()
            .log().ifValidationFails()
            .when()
            .contentType(ContentType.JSON)
            .body(body)
            .put("/itens/{id}", Map.of("id", itemRegistrado.id))
            .then()
            .log().ifValidationFails()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("id", is(itemRegistrado.id.intValue()))
            .body("descricao", is(body.get("descricao")))
            .body("valorReferencia", isA(Number.class));
    }

    public static Stream<Arguments> deveRetornar200Args() {
        return Stream.of(
            Arguments.of(
                "quando todos os parâmetros fornecidos forem válidos",
                Map.of(
                    "descricao", faker.name().fullName(),
                    "valorReferencia", faker.number().randomDouble(2, 0, 100)
                )
            )
        );
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName(
        "PUT /itens -> deve retornar 404 caso o item do id informado não estiver registrado")
    @MethodSource("deveRetornar200Args")
    public void deveRetornar404(
        final String desc,
        final Map<?, ?> body
    ) {
        given()
            .log().ifValidationFails()
            .when()
            .contentType(ContentType.JSON)
            .body(body)
            .put("/itens/{id}", Map.of("id", faker.number().randomNumber()))
            .then()
            .log().ifValidationFails()
            .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("PUT /itens -> deve retornar 400 em caso de falha")
    @MethodSource("deveRetornar400Args")
    public void deveRetornar400(
        final String desc,
        final Map<?, ?> body
    ) {
        given()
            .log().ifValidationFails()
            .when()
            .contentType(ContentType.JSON)
            .body(body)
            .put("/itens/{id}", Map.of("id", itemRegistrado.id))
            .then()
            .log().ifValidationFails()
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    public static Stream<Arguments> deveRetornar400Args() {
        return Stream.of(
            Arguments.of(
                "quando descricao for omitido, por ele é requerido",
                Map.of(
                    // "descricao", faker.name().fullName(),
                    "valorReferencia", faker.number().randomDouble(2, 0, 100)
                )
            ),
            Arguments.of(
                "quando descricao estiver em branco, por ele é requerido",
                Map.of(
                    "descricao", "      ",
                    "valorReferencia", faker.number().randomDouble(2, 0, 100)
                )
            ),
            Arguments.of(
                "quando descricao estiver vazio, por ele é requerido",
                Map.of(
                    "descricao", "",
                    "valorReferencia", faker.number().randomDouble(2, 0, 100)
                )
            ),
            Arguments.of(
                "quando valorReferencia for omitido, por ele é requerido",
                Map.of(
                    "descricao", faker.name().fullName()
                    //"valorReferencia", faker.number().randomDouble(2, 0, 100)
                )
            ),
            Arguments.of(
                "quando valorReferencia estiver em branco , por ele é requerido",
                Map.of(
                    "descricao", faker.name().fullName(),
                    "valorReferencia", ""
                )
            ),
            Arguments.of(
                "quando valorReferencia estiver em branco, por ele é requerido",
                Map.of(
                    "descricao", faker.name().fullName(),
                    "valorReferencia", " "
                )
            ),
            Arguments.of(
                "quando valorReferencia não for um número válido, por ele é requerido",
                Map.of(
                    "descricao", faker.name().fullName(),
                    "valorReferencia", "0.00 "
                )
            ),
            Arguments.of(
                "quando todos os atributos forem omitidos",
                Map.of(
                    //"descricao", faker.name().fullName()
                    //"valorReferencia", faker.number().randomDouble(2, 0, 100)
                )
            )
        );
    }
}

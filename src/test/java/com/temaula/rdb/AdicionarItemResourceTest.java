package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Map;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

@QuarkusTest
public class AdicionarItemResourceTest {
    static Faker faker = new Faker();

    @BeforeEach
    @Transactional
    public void beforeEach(){
        Item.deleteAll();
    }

    @BeforeEach
    @Transactional
    public void afterEach(){
        Item.deleteAll();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("POST /itens -> deve retornar 200 e retornar o item persistido")
    @MethodSource("deveRetornar200Args")
    public void deveRetornar200(final String desc, final Map<?, ?> body) {
        given()
            .log().ifValidationFails()
            .when()
            .contentType(ContentType.JSON)
            .body(body)
            .post("/itens")
            .then()
            .log().ifValidationFails()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("id", isA(Number.class))
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
    @DisplayName("POST /itens -> deve retornar 400 em caso de falha")
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
            .post("/itens")
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

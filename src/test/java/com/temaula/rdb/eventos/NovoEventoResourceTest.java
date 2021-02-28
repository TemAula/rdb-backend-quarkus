package com.temaula.rdb.eventos;

import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class NovoEventoResourceTest {

    @Transactional
    @BeforeEach
    @AfterEach
    public void cleanUp() {
        JpaOperations.deleteAll(Evento.class);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("POST /eventos -> deve retornar 200 mais o novo evento registrado")
    @MethodSource("testDeveRetornar200MaisNovoEventoRegistradoArgs")
    public void testDeveRetornar200MaisNovoEventoRegistrado(final String desc, final Map<?, ?> body) {
        deveRetornar200MaisNovoEventoRegistrado(body);
    }

    private static Map deveRetornar200MaisNovoEventoRegistrado(Map<?, ?> body) {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/eventos")
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", isA(Number.class))
                .body("nome", is(body.get("nome")))
                .body("descricao",
                        body.containsKey("descricao")
                                ? is(body.get("descricao"))
                                : nullValue())
                .body("dataInicio", matchesRegex("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}"))
                .body("dataInicio", is(body.get("dataInicio")))
                .body("dataFim", matchesRegex("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}"))
                .body("dataFim", is(
                        body.containsKey("dataFim")
                                ? body.get("dataFim")
                                : body.get("dataInicio")))
                .extract().body().as(Map.class);
    }

    private static Stream<Arguments> testDeveRetornar200MaisNovoEventoRegistradoArgs() {
        return Stream.of(
                Arguments.arguments(
                        "quando todos os parâmetros fornecidos forem válidos",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros fornecidos são válidos porém omitindo a " +
                                "descricao",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros fornecidos são válidos em especial com a descricao vazia",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", "",
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros fornecidos são válidos em especial com a descricao somente com espaços " +
                                "em branco",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", " ".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros fornecidos são válidos porém omitindo dataFim, sendo que dataFim será" +
                                " igual a dataInicio",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", "f".repeat(400),
                                "dataInicio", "2021-01-28"
                        )
                )
        );
    }


    @ParameterizedTest(name = "{0}")
    @DisplayName("POST /eventos -> deve retornar 400 em caso de falha")
    @MethodSource("testDeveRetornar400EmCasoDeFalhaArgs")
    public void testDeveRetornar400EmCasoDeFalha(final String desc, final Map<?, ?> eventoPreExistente,
                                                 final Map<?, ?> body) throws InterruptedException {

        Optional.ofNullable(eventoPreExistente).ifPresent(this::persistir);

        given()
                .log().ifValidationFails()
                .when()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/eventos")
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Transactional
    void persistir(Map<?,?> evento) {
        deveRetornar200MaisNovoEventoRegistrado(evento);
    }



    private static Stream<Arguments> testDeveRetornar400EmCasoDeFalhaArgs() {

        final var arguments = Stream.of(
                Arguments.arguments(
                        "quando todos os parâmetros fornecidos forem válidos porém com nome de evento já registrado",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        ),
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando nome for omitido, por ele é requerido",
                        null,
                        Map.of(
                                //"nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando nome estiver vazio, por ele é requerido",
                        null,
                        Map.of(
                                "nome", "",
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando nome só tiver espaco em branco, por ele é requerido",
                        null,
                        Map.of(
                                "nome", " ".repeat(10),
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando dataInicio for omitido, pois ela é requirida",
                        null,
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                //"dataInicio", "2021-01-28",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando dataInicio não seguir o padrão ISO 8601 ( YYYY-MM-DD ), pois ela é requirida",
                        null,
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "dataInicio", "20210228",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando dataFim for fornecida mas não seguir o padrão ISO 8601 ( YYYY-MM-DD )",
                        null,
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-02-28",
                                "dataFim", "20210228"
                        )

                ),
                Arguments.arguments(
                        "quando descricao exceder o tamanho de 400 caracteres",
                        null,
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(401),
                                "dataInicio", "2021-02-28",
                                "dataFim", "2021-02-28"
                        )

                ),
                Arguments.arguments(
                        "quando dataFim for fornecida mas for menor que a dataInicio, gerando um período inválido",
                        null,
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "dataInicio", "2021-02-28",
                                "dataFim", "2021-02-27"
                        )

                )
        );
        return arguments;
    }

}

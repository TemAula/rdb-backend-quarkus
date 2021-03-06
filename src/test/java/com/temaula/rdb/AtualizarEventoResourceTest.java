package com.temaula.rdb;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesRegex;

@QuarkusTest
public class AtualizarEventoResourceTest {

    @Transactional
    @AfterAll
    public static void removerEventos() {
        Evento.deleteAll();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("PUT /eventos/{id} -> deve retornar 200 e retornar o evento atualizado")
    @MethodSource("deveRetornar200Args")
    public void deveRetornar200(final String desc, final Map<?, ?> body) {
        var eventId = AdicionarEventoResourceTest.pegarEventoRegistradoValido().id;
        given()
                .log().ifValidationFails()
                .when()
                .contentType(ContentType.JSON)
                .body(body)
                .put("/eventos/{eventId}", Map.of("eventId", eventId))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", is(eventId.intValue()))
                .body("nome", is(body.get("nome")))
                .body("periodoVigencia.dataInicio", matchesRegex("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
                .body("periodoVigencia.dataFim", matchesRegex("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
                .body("periodoVigencia.dataInicio", is(Optional.of(body.get("periodoVigencia"))
                        .map(Map.class::cast)
                        .get()
                        .get("dataInicio")))
                .body("periodoVigencia.dataFim", is(Optional.of(body.get("periodoVigencia"))
                        .map(Map.class::cast)
                        .get()
                        .get("dataFim")));
    }

    private static Stream<Arguments> deveRetornar200Args() {
        return Stream.of(
                Arguments.arguments(
                        "quando todos os parâmetros fornecidos forem válidos",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros requeridos fornecidos forem válidos, omitindo descrição",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros requeridos fornecidos forem válidos, porém com a descricao vazia",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", "",
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )
                ),
                Arguments.arguments(
                        "quando os parâmetros requeridos fornecidos forem válidos, porém com descricao em branco" +
                                "em branco",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", " ".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )
                )
        );
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("PUT /eventos -> deve retornar 400 em caso de falha")
    @MethodSource("deveRetornar400Args")
    public void deveRetornar400(final String desc,
                                final Map<?, ?> body) throws InterruptedException {
        var eventId = AdicionarEventoResourceTest.pegarEventoRegistradoValido().id;
        given()
                .log().ifValidationFails()
                .when()
                .contentType(ContentType.JSON)
                .body(body)
                .put("/eventos/{eventId}", Map.of("eventId", eventId))
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private static Stream<Arguments> deveRetornar400Args() {

        final var arguments = Stream.of(
                Arguments.arguments(
                        "quando nome for omitido, por ele é requerido",
                        Map.of(
                                //"nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )

                ),
                Arguments.arguments(
                        "quando nome estiver vazio, por ele é requerido",
                        Map.of(
                                "nome", "",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )

                ),
                Arguments.arguments(
                        "quando nome só tiver espaco em branco, por ele é requerido",
                        Map.of(
                                "nome", " ".repeat(10),
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )

                ),
                Arguments.arguments(
                        "quando periodoDeVigencia for omitido, por ele é requerido",
                        Map.of(
                                "nome", UUID.randomUUID().toString(),
                                "descricao", "d".repeat(400)
                        )

                ),
                Arguments.arguments(
                        "quando periodoDeVigencia.dataInicio for omitido, pois ela é requirida",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataFim", "2021-02-28")
                        )

                ),
                Arguments.arguments(
                        "quando periodoDeVigencia.dataInicio não seguir o padrão ISO 8601 ( YYYY-MM-DD ), pois ela é " +
                                "requirida",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "20210128", "dataFim", "2021-02-28")
                        )

                ),
                Arguments.arguments(
                        "quando periodoDeVigencia.dataFim for omitido, pois ela é requirida",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28")
                        )

                ),
                Arguments.arguments(
                        "quando periodoDeVigencia.dataFim for fornecida mas não seguir o padrão ISO 8601 ( YYYY-MM-DD" +
                                " )",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "20210228")
                        )

                ),
                Arguments.arguments(
                        "quando descricao exceder o tamanho de 400 caracteres",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(401),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-02-28")
                        )

                ),
                Arguments.arguments(
                        "quando dataFim for fornecida mas for menor que a dataInicio, gerando um período inválido",
                        Map.of(
                                "nome", "Sopão",
                                "descricao", "d".repeat(400),
                                "periodoVigencia", Map.of("dataInicio", "2021-01-28", "dataFim", "2021-01-27")
                        )

                )
        );
        return arguments;
    }

}

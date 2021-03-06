package com.temaula.rdb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.wildfly.common.Assert;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PeriodoTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("ofArgs")
    void of(final String cenario,
            final boolean deveLançarExcecao,
            final LocalDate dataInicio, LocalDate dataFim) {

        if (deveLançarExcecao) {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> {
                        Periodo.of(dataInicio, dataFim);
                    }, cenario);
            return;
        }
        Assertions.assertDoesNotThrow(
                () -> {
                    Periodo.of(dataInicio, dataFim);
                }, cenario);

    }


    static Stream<Arguments> ofArgs() {
        return Stream.of(
                Arguments.of(
                        "deve aceitar periodo onde data inicial e data final são iguais",
                        false,
                        LocalDate.now(),
                        LocalDate.now()
                ),
                Arguments.of(
                        "deve aceitar periodo onde data inicial é menor do que a data final",
                        false,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1)
                ),
                Arguments.of(
                        "não deve aceitar periodo onde data inicial é maior do que a data final",
                        true,
                        LocalDate.now().plusDays(1),
                        LocalDate.now()
                ),
                Arguments.of(
                        "não deve aceitar periodo onde data inicial não for informada",
                        true,
                        null,
                        LocalDate.now()
                ),
                Arguments.of(
                        "não deve aceitar periodo onde data final não for informada",
                        true,
                        LocalDate.now(),
                        null
                ),
                Arguments.of(
                        "não deve aceitar periodo onde nem data final e nem data final forem informadas",
                        true,
                        null,
                        null
                )
        );
    }

}
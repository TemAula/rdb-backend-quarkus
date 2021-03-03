package com.temaula.rdb.security;

import com.temaula.rdb.Pessoa;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UsuarioControllerTest {

    @Test
    public void shouldReturnUser() {
        String entity = given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .get("/usuarios/me")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(String.class);
        Assertions.assertNotNull(entity);
    }
}
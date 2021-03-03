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
        UsuarioDTO usuario = given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .get("/usuarios/me")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(UsuarioDTO.class);
        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("user", usuario.username);
        Assertions.assertEquals("user", usuario.role);
    }

    @Test
    public void shouldReturnAdmin() {
        UsuarioDTO usuario = given()
                .auth()
                .basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get("/usuarios/me")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(UsuarioDTO.class);
        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("admin", usuario.username);
        Assertions.assertEquals("admin", usuario.role);
    }
}
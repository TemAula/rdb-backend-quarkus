package com.temaula.rdb.security;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
class UsuarioControllerTest {

    private Faker faker;

    @BeforeEach
    public void setUp() {
        this.faker = new Faker();
    }

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

    @Test
    public void shouldCreateUser() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.username = faker.name().username();
        dto.email = dto.username + "@email.com";
        dto.password = faker.programmingLanguage().name();

        UsuarioDTO usuario = given()
                .contentType(ContentType.JSON)
                .when()
                .body(dto)
                .post("/usuarios/")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().as(UsuarioDTO.class);

        UsuarioDTO usuarioLogado = given()
                .auth()
                .basic(dto.username, dto.password)
                .contentType(ContentType.JSON)
                .when()
                .get("/usuarios/me")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(UsuarioDTO.class);
        Assertions.assertNotNull(usuarioLogado);
        Assertions.assertEquals(usuario.id, usuarioLogado.id);
        Assertions.assertEquals(usuario.username, usuarioLogado.username);
        Assertions.assertEquals(usuario.email, usuarioLogado.email);
    }
}
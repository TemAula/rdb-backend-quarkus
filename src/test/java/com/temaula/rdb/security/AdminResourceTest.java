package com.temaula.rdb.security;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@QuarkusTest
class AdminResourceTest {


    @Test
    public void shouldReturnForbidden() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin")
                .then()
                .statusCode(FORBIDDEN.getStatusCode());
    }

    @Test
    public void shouldReturnUnauthorized() {
        given()
                .auth()
                .preemptive()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void shouldReturnOk() {
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode());
    }
}
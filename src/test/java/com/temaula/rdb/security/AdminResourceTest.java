package com.temaula.rdb.security;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@QuarkusTest
class AdminResourceTest {


    @Test
    public void shouldReturnForbidden() {
        given()
                .when()
                .get("/api/admin")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void shouldReturnUnauthorized() {
        given()
                .auth()
                .basic("user", "user")
                .when()
                .get("/api/admin")
                .then()
                .statusCode(FORBIDDEN.getStatusCode());
    }

    @Test
    public void shouldReturnOk() {
        given()
                .auth()
                .preemptive()
                .basic("admin", "admin")
                .when()
                .get("/api/admin")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }
}
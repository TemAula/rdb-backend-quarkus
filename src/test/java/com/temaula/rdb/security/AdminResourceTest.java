package com.temaula.rdb.security;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;

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
}
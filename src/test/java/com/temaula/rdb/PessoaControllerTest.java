package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@QuarkusTest
class PessoaControllerTest {

    private Faker faker;

    @BeforeEach
    public void setUp() {
        this.faker = new Faker();
    }

    @Test
    public void shouldInsert() {
        Pessoa pessoa = createPerson();
        Assertions.assertNotNull(pessoa);
    }

    @Test
    public void shouldFindAll() {
        Pessoa pessoa = createPerson();

        List<Pessoa> entities = given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(new TypeRef<List<Pessoa>>() {
                });

        Assertions.assertFalse(entities.isEmpty());
        Assertions.assertTrue(entities.stream().map(p -> p.nome)
                .anyMatch(n -> n.equals(pessoa.nome)));

    }

    @Test
    public void shouldFindOne() {
        Pessoa pessoa = createPerson();
        Pessoa entity = given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas/{id}", pessoa.id)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(Pessoa.class);
        Assertions.assertEquals(pessoa, entity);
    }

    @Test
    public void shouldDelete() {
        Pessoa pessoa = createPerson();
        given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .delete("/pessoas/{id}", pessoa.id)
                .then()
                .statusCode(ACCEPTED.getStatusCode());

        given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas/{id}", pessoa.id)
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldUpdate() {
        Pessoa pessoa = createPerson();
        pessoa.nome = faker.dragonBall().character();

        Pessoa pessoaAtualizada = given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .body(pessoa)
                .put("/pessoas/{id}", pessoa.id)
                .then()
                .statusCode(ACCEPTED.getStatusCode())
                .extract().as(Pessoa.class);
        Assertions.assertEquals(pessoa, pessoaAtualizada);
    }

    private Pessoa createPerson() {
        Pessoa pessoa = getPessoa();
        return given()
                .auth()
                .basic("user", "user")
                .contentType(ContentType.JSON)
                .when()
                .body(pessoa)
                .post("/pessoas")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().as(Pessoa.class);
    }

    private Pessoa getPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.nome = (faker.name().firstName());
        pessoa.email = faker.bothify("????##@gmail.com");
        pessoa.telefone = faker.phoneNumber().cellPhone();
        pessoa.endereco = faker.address().cityName();
        pessoa.senha = faker.number().digits(3);
        return pessoa;
    }
}
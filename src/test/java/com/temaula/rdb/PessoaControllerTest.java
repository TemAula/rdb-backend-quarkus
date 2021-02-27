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
import static javax.ws.rs.core.Response.Status.CREATED;
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
    }

    @Test
    public void shouldFindAll() {
        Pessoa pessoa = createPerson();

        List<Pessoa> entities = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(new TypeRef<List<Pessoa>>() {});

        Assertions.assertFalse(entities.isEmpty());
        Assertions.assertTrue(entities.stream().map(Pessoa::getNome)
                .anyMatch(n -> n.equals(pessoa.getNome())));

    }

    @Test
    public void shouldFindOne() {
        Pessoa pessoa = createPerson();

        Pessoa entity = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas/{id}", pessoa.getId())
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(Pessoa.class);
        Assertions.assertEquals(pessoa, entity);
    }

    private Pessoa createPerson() {
        Pessoa pessoa = getPessoa();
        return given()
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
        pessoa.setNome(faker.name().firstName());
        pessoa.setEmail(faker.bothify("????##@gmail.com"));
        pessoa.setTelefone(faker.phoneNumber().cellPhone());
        pessoa.setEndereco(faker.address().cityName());
        pessoa.setSenha(faker.number().digits(3));
        return pessoa;
    }
}
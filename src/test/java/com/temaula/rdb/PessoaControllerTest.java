package com.temaula.rdb;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class PessoaControllerTest {

    private Faker faker;

    @BeforeEach
    public void setUp() {
        this.faker = new Faker();
    }

    @Test
    public void shouldInsert() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(faker.name().firstName());
        pessoa.setEmail(faker.bothify("????##@gmail.com"));
        pessoa.setTelefone(faker.phoneNumber().cellPhone());
        pessoa.setEndereco(faker.address().cityName());
        pessoa.setSenha(faker.number().digits(3));
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(pessoa)
                .post("/pessoas")
                .then()
                .statusCode(201);
    }
}
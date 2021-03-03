package com.temaula.rdb.security;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UsuarioServiceTest {

    @Inject
    private UsuarioService service;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        this.faker = new Faker();
    }


    @Test
    public void shouldCreateNewUser() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@meil.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = faker.dragonBall().character();

        UsuarioDTO newUser = service.criar(usuarioDTO);
        Assertions.assertNotNull(newUser.id);
        Assertions.assertEquals(usuarioDTO.email, newUser.email);
        Assertions.assertNull(newUser.password);
        Assertions.assertEquals(usuarioDTO.username, newUser.username);
    }

    @Test
    public void shouldReturnErrorThereIsDuplicateUserName() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@meil.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = faker.dragonBall().character();
        service.criar(usuarioDTO);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.criar(usuarioDTO));
    }
}
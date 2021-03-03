package com.temaula.rdb.security;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UsuarioServiceTest {

    @Inject
    private UsuarioService service;

    @Test
    public void shouldCreateNewUser() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@meil.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = "my";

        UsuarioDTO newUser = service.criar(usuarioDTO);
        Assertions.assertNotNull(newUser.id);
        Assertions.assertEquals(usuarioDTO.email, newUser.email);
        Assertions.assertNotEquals(usuarioDTO.password, newUser.password);
        Assertions.assertEquals(usuarioDTO.username, newUser.username);
    }
}
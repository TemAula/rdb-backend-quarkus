package com.temaula.rdb.security;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.resource.spi.SecurityException;

import java.security.Principal;

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
        assertThrows(IllegalArgumentException.class, () -> service.criar(usuarioDTO));
    }

    @Test
    public void shouldUpdateUser() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@mail.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = faker.dragonBall().character();

        UsuarioDTO newUser = service.criar(usuarioDTO);
        newUser.email = "update@mail.com";
        newUser.password = "password";
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn(newUser.username);
        UsuarioDTO usarioAtualizado = service.atualizar(newUser.id, newUser, principal);

        Assertions.assertNotNull(usarioAtualizado);
        Assertions.assertEquals("update@mail.com", usarioAtualizado.email);

    }

    @Test
    public void shouldNotUpdateUserNameWhenItTaken() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@mail.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = faker.dragonBall().character();

        UsuarioDTO newUser = service.criar(usuarioDTO);
        newUser.username = "user";
        newUser.password = "password";
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn(usuarioDTO.username);
        assertThrows(IllegalArgumentException.class,
                        () -> service.atualizar(newUser.id, newUser, principal));
    }

    @Test
    public void shouldReturnErrorWhenUserUpdateAnotherUser() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@mail.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = faker.dragonBall().character();

        UsuarioDTO newUser = service.criar(usuarioDTO);
        newUser.password = "password";
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("user");

        assertThrows(NotAuthorizedException.class,
                () -> service.atualizar(newUser.id, newUser, principal));
    }

    @Test
    public void shouldUpdateWhenUserIsAdmin() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.email = "my@mail.com";
        usuarioDTO.password = "password";
        usuarioDTO.username = faker.dragonBall().character();

        UsuarioDTO newUser = service.criar(usuarioDTO);
        newUser.password = "password";
        newUser.email = "update@mail.com";
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("admin");
        UsuarioDTO usarioAtualizado = service.atualizar(newUser.id, newUser, principal);
        Assertions.assertNotNull(usarioAtualizado);
        Assertions.assertEquals("update@mail.com", usarioAtualizado.email);

    }
    //pode atualizar se o usario for admin outra pessoa
}
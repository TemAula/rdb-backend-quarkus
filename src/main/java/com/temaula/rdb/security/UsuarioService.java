package com.temaula.rdb.security;


import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {

        if (jaExisteUsuario(dto.username)) {
            throw new IllegalArgumentException("Username já utilizado");
        }
        Usuario usuario = dto.toUsuario();
        usuario.role = Roles.USER.get();
        usuario.password = BcryptUtil.bcryptHash(dto.password);
        usuario.persist();
        return UsuarioDTO.of(usuario);
    }

    public Optional<UsuarioDTO> me(Principal principal) {
        Optional<Usuario> usuario = Usuario.find("username", principal.getName()).singleResultOptional();
        return usuario.map(UsuarioDTO::of);
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto, Principal principal) {
        Usuarios usuarios = validarUsuarios(id, principal);
        Usuario usuario = usuarios.usuario;

        if (!usuario.username.equals(dto.username) && jaExisteUsuario(dto.username)) {
            throw new IllegalArgumentException("Username já utilizado");
        }
        usuario.atualizar(dto.toUsuario(), BcryptUtil.bcryptHash(dto.password));
        usuario.persist();
        return UsuarioDTO.of(usuario);
    }

    @Transactional
    public UsuarioDTO remover(Long id, Principal principal) {
        Usuarios usuarios = validarUsuarios(id, principal);
        Usuario usuario = usuarios.usuario;
        usuario.delete();
        return UsuarioDTO.of(usuario);
    }

    private Usuarios validarUsuarios(Long id, Principal principal) {
        Usuario usuario = Usuario
                .<Usuario>findByIdOptional(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado com o Id " + id));

        Usuario usuarioLogado = Usuario.find("username", principal.getName())
                .<Usuario>singleResultOptional()
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao logado corretamente"));

        if (usuarioLogado.isNotAdmin() && usuarioLogado.outroUsername(usuario)) {
            throw new NotAuthorizedException("Operacao indevida realizada pelo usuario");
        }
        return new Usuarios(usuario, usuarioLogado);
    }

    private boolean jaExisteUsuario(String username) {
        long count = Usuario.find("username", username).count();
        return count > 0;
    }

    private static final class Usuarios {
        private final Usuario usuario;
        private final Usuario usuarioLogado;

        private Usuarios(Usuario usuario, Usuario usuarioLogado) {
            this.usuario = usuario;
            this.usuarioLogado = usuarioLogado;
        }
    }
}

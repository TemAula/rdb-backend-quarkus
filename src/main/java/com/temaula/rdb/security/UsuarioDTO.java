package com.temaula.rdb.security;

public class UsuarioDTO {

    public Long id;

    public String username;

    public String password;

    public String role;

    public String email;

    public Usuario toUsuario() {
        Usuario usuario = new Usuario();
        usuario.id = this.id;
        usuario.email = this.email;
        usuario.username = this.username;
        usuario.password = this.password;
        usuario.role = this.role;
        return usuario;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static UsuarioDTO of(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.id = usuario.id;
        dto.email = usuario.email;
        dto.username = usuario.username;
        dto.role = usuario.role;
        return dto;
    }
}

package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.util.Objects;

@Entity(name = "pessoa")
public class Pessoa extends PanacheEntity {

    @Column(length = 32, nullable = false)
    public String nome;

    @Column(length = 32, nullable = false)
    @Email
    public String email;

    @Column(length = 16, nullable = false)
    public String telefone;

    @Column(length = 128)
    public String endereco;

    @Column(length = 16, nullable = false)
    public String senha;

    public void atualizar(Pessoa pessoa) {
        this.senha = pessoa.senha;
        this.nome = pessoa.nome;
        this.telefone = pessoa.telefone;
        this.email = pessoa.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

}
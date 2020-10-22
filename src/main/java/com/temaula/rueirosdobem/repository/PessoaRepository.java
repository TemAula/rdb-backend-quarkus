package com.temaula.rueirosdobem.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

import com.temaula.rueidodobem.model.Pessoa;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {

}

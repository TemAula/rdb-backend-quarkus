package com.temaula.rueirosdobem.repository;

import javax.enterprise.context.ApplicationScoped;

import com.temaula.rueirosdobem.model.Evento;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EventoRepository implements PanacheRepository<Evento>{

}

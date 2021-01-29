package com.temaula.rdb.eventos;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class  EventoRepository implements PanacheRepository<Evento> {
}

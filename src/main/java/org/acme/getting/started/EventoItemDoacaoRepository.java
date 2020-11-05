package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EventoItemDoacaoRepository implements PanacheRepository<EventoItemDoacao>{

}

package org.acme.getting.started;

import javax.enterprise.context.ApplicationScoped;

import com.temaula.rueirosdobem.model.ItemDoacao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ItemDoacaoRepository implements PanacheRepository<ItemDoacao> {

}

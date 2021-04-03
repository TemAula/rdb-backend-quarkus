package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
public class ItemEvento extends PanacheEntityBase {

    public static ItemEvento criarItemEvento(Evento evento, Item item) {

        Optional<ItemEvento> itemEventoRef = ItemEvento.findByEventoAndItem(evento, item);
        if (itemEventoRef.isPresent()) {
            return itemEventoRef.get();
        }

        ItemEvento itemEvento = new ItemEvento();
        itemEvento.id = ItemEventoId.of(evento,item);
        itemEvento.evento = evento;
        itemEvento.item = item;
        itemEvento.persist();

        return itemEvento;

    }

    public static Optional<ItemEvento> findByEventoAndItem(Evento evento, Item item) {
        return find(
                "evento.id = ?1 and item.id = ?2",
                evento.id,
                item.id).firstResultOptional();
    }

    public static Stream<ItemEvento> streamByEvento(Evento evento) {
        return stream("evento.id = ?1", evento.id);
    }


    public static Stream<Item> streamByItem(Item item) {
        return stream("item.id = ?1", item.id);
    }

    @EmbeddedId
    public ItemEventoId id;

    @ManyToOne
    @JoinColumn(name = "evento_id", insertable = false, updatable = false)
    public Evento evento;

    @ManyToOne
    @JoinColumn(name = "item_id",  insertable = false, updatable = false)
    public Item item;

    public static void deleteByEvento(Evento evento) {
        ItemEvento.delete("evento.id = ?1", evento.id);
    }


    public static void deleteByItem(Item item) {
        ItemEvento.delete("item.id = ?1", item.id);
    }
}

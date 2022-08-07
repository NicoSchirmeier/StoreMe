package org.lunic.data.builder;

import org.lunic.data.Item;
import org.lunic.data.Tag;
import org.lunic.data.type.ItemType;

import java.time.LocalDate;
import java.util.HashSet;

public class ItemBuilder {

    private Item itemTemplate;

    private String name;
    private ItemType type;
    private int amount = -1;
    private LocalDate expirationDate;
    private LocalDate consumptionDate;
    private HashSet<Tag> tags;

    public ItemBuilder(Item itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    public ItemBuilder() {
        this.itemTemplate = new Item(null, null,  0, null, null, null);
    }

    public Item create() {
        if(name == null) name = itemTemplate.name();
        if(type == null) type = itemTemplate.type();
        if(amount <= 0) amount = itemTemplate.amount();
        if(expirationDate == null) expirationDate = itemTemplate.expirationDate();
        if(consumptionDate == null) consumptionDate = itemTemplate.consumptionDate();
        if(tags == null) tags = itemTemplate.tags();

        return new Item(name, type, amount, expirationDate, consumptionDate, tags);
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ItemBuilder setConsumptionDate(LocalDate consumptionDate) {
        this.consumptionDate = consumptionDate;
        return this;
    }

    public ItemBuilder setTags(HashSet<Tag> tags) {
        this.tags = tags;
        return this;
    }
}

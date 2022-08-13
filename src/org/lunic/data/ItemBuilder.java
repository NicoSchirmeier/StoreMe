package org.lunic.data;

import org.lunic.data.type.ItemType;

import java.time.LocalDate;
import java.util.HashSet;

public class ItemBuilder {

    private final Item itemTemplate;

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
        this.itemTemplate = new Item(null, null, 0, null, null, null);
    }

    public Item build() {
        if (name == null) name = itemTemplate.name();
        if (type == null) type = itemTemplate.type();
        if (amount <= 0) amount = itemTemplate.amount();
        if (expirationDate == null) expirationDate = itemTemplate.expirationDate();
        if (consumptionDate == null) consumptionDate = itemTemplate.consumptionDate();
        if (tags == null) tags = itemTemplate.tags();

        return new Item(this);
    }

    protected String getName() {
        return name;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    protected ItemType getType() {
        return type;
    }

    public ItemBuilder setType(ItemType type) {
        this.type = type;
        return this;
    }

    protected int getAmount() {
        return amount;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    protected LocalDate getExpirationDate() {
        return expirationDate;
    }

    public ItemBuilder setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    protected LocalDate getConsumptionDate() {
        return consumptionDate;
    }

    public ItemBuilder setConsumptionDate(LocalDate consumptionDate) {
        this.consumptionDate = consumptionDate;
        return this;
    }

    protected HashSet<Tag> getTags() {
        return tags;
    }

    public ItemBuilder setTags(HashSet<Tag> tags) {
        this.tags = tags;
        return this;
    }
}



package org.lunic.data;

import org.lunic.data.type.ItemType;

import java.time.LocalDate;
import java.util.HashSet;

public record Item(String name, ItemType type, int amount,
                   LocalDate expirationDate, LocalDate consumptionDate,
                   HashSet<Tag> tags) {

    Item(ItemBuilder builder) {
        this(builder.getName(), builder.getType(), builder.getAmount(), builder.getExpirationDate(), builder.getConsumptionDate(), builder.getTags());
    }

    @Override
    public String toString() {
        return amount + "x " + name + " | Type: " + type + " | Expiration Date: " + expirationDate + " | Consumption Date: " + consumptionDate;
    }

    public String toPrettyString() {
        StringBuilder tagString = new StringBuilder();
        for (Tag tag : tags) {
            tagString.append("\n   | ").append(tag);
        }

        return name +
                "\n | Amount: " + amount +
                "\n | Type: " + type +
                "\n | Expiration Date: " + expirationDate +
                "\n | Consumption Date: " + consumptionDate +
                "\n | Tags: " + tagString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item item) {
            return item.name().equals(name) && item.tags.equals(tags()) && item.type.equals(type());
        }
        return false;
    }

    public String toTemplateString() {
        return name + " | Type: " + type + " | Tags: " + tags;
    }
}

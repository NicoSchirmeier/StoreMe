package org.lunic.data;

import org.lunic.data.type.ItemType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public record Item(String name, ItemType type, int amount,
                   LocalDate expirationDate, LocalDate consumptionDate,
                   HashSet<Tag> tags) {

    @Override
    public String toString() {
        return amount + "x " + name + " | Type: " + type + " | Expiration Date: " + expirationDate + " | Consumption Date: " + consumptionDate;
    }

    public String toPrettyString() {
        String tagString = "";
        for (Tag tag : tags) {
            tagString = tagString + "\n   | " + tag;
        }

        return name +
                "\n | Amount: " + amount +
                "\n | Type: " + type +
                "\n | Expiration Date: " + expirationDate +
                "\n | Consumption Date: " + consumptionDate +
                "\n | Tags: " + tagString;
    }
}

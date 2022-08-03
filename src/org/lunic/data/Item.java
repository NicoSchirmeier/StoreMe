package org.lunic.data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public record Item(String name, ItemType type, int amount,
                   LocalDate expirationDate,
                   LocalDate consumptionDate, HashSet<Tag> tags) {

    @Override
    public String toString() {
        return "[" + amount + "x " + name + " | Type: " + type + " | Expiration Date: " +
                expirationDate + " | Consumption Date: " + consumptionDate +
                " | Tags: " + Arrays.toString(tags.toArray()) + "]";
    }
}

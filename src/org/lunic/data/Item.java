package org.lunic.data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

public record Item(String name, ItemType type, int amount,
                   LocalDate expirationDate,
                   Time timeUntilConsumption, HashSet<Tag> tags) {
    @Override
    public String toString() {
        return amount + "x " + name + " | type: " + type + " | expr. date: " +
                expirationDate + " | cons. date: " + timeUntilConsumption +
                " | tags: " + Arrays.toString(tags.toArray());
    }
}

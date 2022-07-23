package org.lunic.data;

import java.util.HashSet;

public record Item(String name, ItemType type, int amount, Time timeUntilExpiration,
                   Time timeUntilConsumption, HashSet<Tag> tags) {
}

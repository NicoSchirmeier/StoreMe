package org.lunic.data;

import java.util.HashSet;

public record Recipe(String name, RecipeType type,
                     HashSet<Item> items, boolean canBeCooked, Time duration,
                     HashSet<Tag> tags, String instruction) {
}

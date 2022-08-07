package org.lunic.data;

import org.lunic.data.type.ContainerType;

import java.util.HashSet;

public record Container(String name, String location, HashSet<Item> items,
                        ContainerType type) {

    @Override
    public String toString() {
        return "[" + name + " | Location: " + location + " | Type: " + type + "]";
    }
}

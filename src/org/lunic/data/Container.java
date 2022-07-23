package org.lunic.data;

import java.util.HashSet;

public record Container(String name, String location, HashSet<Item> items,
                        ContainerType type) {
}

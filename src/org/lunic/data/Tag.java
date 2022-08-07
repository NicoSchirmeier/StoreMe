package org.lunic.data;

import org.lunic.data.type.TagType;

public record Tag(String name, TagType type, String description) {

    @Override
    public String toString() {
        return name + " | description: " + description;
    }
}

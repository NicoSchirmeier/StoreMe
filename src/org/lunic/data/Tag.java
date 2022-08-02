package org.lunic.data;

public record Tag(String name, TagType type, String description) {

    @Override
    public String toString() {
        return name + " | type: " + type;
    }
}

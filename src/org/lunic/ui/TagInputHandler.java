package org.lunic.ui;

import org.lunic.data.Tag;

import java.util.HashSet;

public class TagInputHandler implements InputHandler {

    public void print() {
        System.out.println(getClass());
    }

    public HashSet<Tag> printSelectTagsDialog() {
        System.err.println("NOT IMPLEMENTED");
        return new HashSet<>();
    }
}

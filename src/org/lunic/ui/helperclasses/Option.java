package org.lunic.ui.helperclasses;

public record Option(String text, Object rootObject) {

    public String getText() {
        return text;
    }

    public Printable getPrintable() {
        return (Printable) rootObject;
    }

    public Object getRootObject() {
        return rootObject;
    }

    public boolean hasPrintable() {
        if (rootObject != null) {
            return rootObject instanceof Printable printable;
        }
        return false;
    }
}
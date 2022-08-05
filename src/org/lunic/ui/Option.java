package org.lunic.ui;

public class Option {
   private final String text;
   private final InputHandler inputHandler;
   private final Object rootObject;

   public Option(String text, InputHandler root) {
       this.text = text;
       this.inputHandler = root;
       this.rootObject = null;
   }

   public Option(String text, Object rootObject) {
       this.text = text;
       this.rootObject = rootObject;
       this.inputHandler = null;
   }

    public String getText() {
        return text;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public Object getRootObject() {
        return rootObject;
    }
}
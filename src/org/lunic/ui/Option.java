package org.lunic.ui;

public class Option {
   private final String text;
   private final InputHandler inputHandler;
   private final Object rootObject;
   private final OptionType type;

   public Option(String text, InputHandler root) {
       this.text = text;
       this.inputHandler = root;
       this.rootObject = null;
       this.type = OptionType.SUB_MENU;
   }

   public Option(String text, Object action) {
       this.text = text;
       this.rootObject = action;
       this.inputHandler = null;
       this.type = OptionType.ACTION;
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

    public OptionType getType() {
        return type;
    }
}
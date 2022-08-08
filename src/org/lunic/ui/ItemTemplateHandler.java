package org.lunic.ui;

import org.lunic.DataManager;
import org.lunic.data.Item;
import org.lunic.data.ItemBuilder;
import org.lunic.ui.helperclasses.*;

import javax.swing.plaf.OptionPaneUI;
import java.util.ArrayList;
import java.util.HashSet;

public class ItemTemplateHandler implements Printable {
    @Override
    public void print() {
        System.out.println("- Item Templates -");
        ArrayList<Option> additionalOptions = new ArrayList<Option>();
        for (Item template : DataManager.ITEM_TEMPLATE_REPOSITORY.Read()) {
            additionalOptions.add(new Option( template.toTemplateString(), template));
        }
        Option option = ConsoleSelectionUtils.displayActions(additionalOptions, Action.BACK);
        if(option.getRootObject() instanceof Item template) {
            printItemTemplateDetails(template);
        }
    }

    private void printItemTemplateDetails(Item template) {
        System.out.println("- Template Item - ");
        System.out.println(template.toPrettyString());
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.DELETE);
        if(option.getRootObject() instanceof Action action) {
            if(action.equals(Action.DELETE)) {
                boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete Item Template");
                if(confirmed) {
                    DataManager.ITEM_TEMPLATE_REPOSITORY.Delete(template);
                }
            }
        }
        print();
    }

    public Item printSelectTemplate() {
        System.out.println("- Item Templates -");
        ArrayList<Option> additionalOptions = new ArrayList<>();
        for (Item template : DataManager.ITEM_TEMPLATE_REPOSITORY.Read()) {
            additionalOptions.add(new Option(template.toTemplateString(), template));
        }
        Option option = ConsoleSelectionUtils.displayActions(additionalOptions, Action.BACK);
        if(option.getRootObject() instanceof Item template) {
            return template;
        }
        return null;
    }

    public HashSet<Item> printAddTemplateItemsDialog(boolean selectAmount) {
        HashSet<Item> itemTemplates = new HashSet<>();
        while (true) {
            Item template = printSelectTemplate();
            if(template != null) {
                if(selectAmount) {
                    int amount = ConsoleReadingUtils.getAmount(1, Integer.MAX_VALUE);
                    itemTemplates.add(new ItemBuilder(template).setAmount(amount).build());
                } else {
                    itemTemplates.add(template);
                }
            }
            Option option = ConsoleSelectionUtils.displayActions(Action.ADD, Action.DONE);
            if(option.getRootObject() instanceof Action action) {
                if(action.equals(Action.DONE)) break;
            }
        }
        return itemTemplates;
    }
}

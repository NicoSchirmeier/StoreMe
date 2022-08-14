package org.lunic.ui;

import org.lunic.data.Item;
import org.lunic.data.ItemBuilder;
import org.lunic.repositories.ItemTemplateRepository;
import org.lunic.ui.helperclasses.*;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemTemplateHandler implements Printable {

    private static ItemTemplateHandler INSTANCE;

    public static ItemTemplateHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ItemTemplateHandler();
        }
        return INSTANCE;
    }

    private ItemTemplateHandler() {}

    @Override
    public void print() {
        if (printItemTemplateOptions().getRootObject() instanceof Item template) {
            printItemTemplateDetails(template);
        }
    }

    private void printItemTemplateDetails(Item template) {
        System.out.println("- Template Item - ");
        System.out.println(template.toPrettyString());
        Option option = ConsoleSelectionUtils.displayActions(Action.BACK, Action.DELETE);
        if (option.getRootObject() instanceof Action action) {
            if (action.equals(Action.DELETE)) {
                boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete Item Template");
                if (confirmed) {
                    ItemTemplateRepository.getInstance().delete(template);
                }
            }
        }
        print();
    }

    public Item printSelectTemplate() {
        if (printItemTemplateOptions().getRootObject() instanceof Item template) {
            return template;
        }
        return null;
    }

    private Option printItemTemplateOptions() {
        System.out.println("- Item Templates -");
        ArrayList<Option> additionalOptions = new ArrayList<>();
        for (Item template : ItemTemplateRepository.getInstance().read()) {
            additionalOptions.add(new Option(template.toTemplateString(), template));
        }
        return ConsoleSelectionUtils.displayActions(additionalOptions, Action.BACK);
    }

    public HashSet<Item> printAddTemplateItemsDialog(boolean selectAmount, boolean canBeSkipped) {
        HashSet<Item> itemTemplates = new HashSet<>();
        if (canBeSkipped) {
            boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Skip");
            if (confirmed) return null;
        }

        while (true) {
            Item template = printSelectTemplate();
            if (template != null) {
                if (selectAmount) {
                    System.out.println("Enter needed amount:");
                    int amount = ConsoleReadingUtils.getAmount(1, Integer.MAX_VALUE);
                    if (amount < 0) return null;
                    itemTemplates.add(new ItemBuilder(template).setAmount(amount).build());
                } else {
                    itemTemplates.add(template);
                }
            }
            Option option = ConsoleSelectionUtils.displayActions(Action.ADD, Action.DONE);
            if (option.getRootObject() instanceof Action action) {
                if (action.equals(Action.DONE)) break;
            }
        }
        return itemTemplates;
    }
}

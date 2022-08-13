package org.lunic.repositories;

import org.lunic.data.Item;
import org.lunic.persistance.*;

import java.util.ArrayList;

public class ItemTemplateRepository extends Repository {

    public ItemTemplateRepository() {
        super(ItemTemplateJsonDriver.getInstance());
    }

    public void create(Item itemTemplate) {
        super.create(itemTemplate);
    }

    public ArrayList<Item> read() {
        ArrayList<Item> itemTemplates = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Item) {
                itemTemplates.add((Item) record);
            }
        }

        return itemTemplates;
    }

    public void update(Item itemTemplateToBeUpdated, Item updatedItemTemplate) {
        super.update(itemTemplateToBeUpdated, updatedItemTemplate);
    }

    public void delete(Item itemTemplate) {
        super.delete(itemTemplate);
    }

}

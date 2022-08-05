package org.lunic.repositories;

import org.lunic.data.Item;
import org.lunic.persistance.ItemTemplateJsonDriver;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemTemplateRepository extends Repository {

    public ItemTemplateRepository() {
        super(new ItemTemplateJsonDriver());
    }

    public void Create(Item itemTemplate) {
        super.Create(itemTemplate);
    }

    public ArrayList<Item> Read() {
        ArrayList<Item> itemTemplates = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Item) {
                itemTemplates.add((Item) record);
            }
        }

        return itemTemplates;
    }

    public void Update(Item itemTemplateToBeUpdated, Item updatedItemTemplate) {
        super.Update(itemTemplateToBeUpdated, updatedItemTemplate);
    }

    public void Delete(Item itemTemplate) {
        super.Delete(itemTemplate);
    }

}

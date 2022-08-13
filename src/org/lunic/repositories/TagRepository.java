package org.lunic.repositories;

import org.lunic.DataManager;
import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.Recipe;
import org.lunic.data.Tag;
import org.lunic.persistance.*;

import java.util.ArrayList;

public class TagRepository extends Repository {

    public TagRepository() {
        super(new TagJsonDriver());
    }

    public void create(Tag tag) {
        super.create(tag);
    }

    public ArrayList<Tag> read() {
        ArrayList<Tag> tags = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Tag) {
                tags.add((Tag) record);
            }
        }

        return tags;
    }

    public void update(Tag tagToBeUpdated, Tag updatedTag) {
        for (Container container : DataManager.CONTAINER_REPOSITORY.read()) {
            for (Item item : container.items()) {
                if(item.tags().contains(tagToBeUpdated)) {
                    item.tags().remove(tagToBeUpdated);
                    item.tags().add(updatedTag);
                    DataManager.CONTAINER_REPOSITORY.update(container, container);
                }
            }
        }
        for(Recipe recipe : DataManager.RECIPE_REPOSITORY.read()) {
            if(recipe.tags().contains(tagToBeUpdated)) {
                recipe.tags().remove(tagToBeUpdated);
                recipe.tags().add(updatedTag);
                DataManager.RECIPE_REPOSITORY.update(recipe, recipe);
            }
        }
        super.update(tagToBeUpdated, updatedTag);
    }

    @Override
    public void delete(Record record) {
        if(record instanceof Tag tag) {
            for (Container container : DataManager.CONTAINER_REPOSITORY.read()) {
                for (Item item : container.items()) {
                    if(item.tags().contains(tag)) {
                        item.tags().remove(tag);
                        DataManager.CONTAINER_REPOSITORY.update(container, container);
                    }
                }
            }
            for(Recipe recipe : DataManager.RECIPE_REPOSITORY.read()) {
                if(recipe.tags().contains(tag)) {
                    recipe.tags().remove(tag);
                    DataManager.RECIPE_REPOSITORY.update(recipe, recipe);
                }
            }
            super.delete(tag);
        }
    }
}

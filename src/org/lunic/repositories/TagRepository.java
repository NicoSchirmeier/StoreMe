package org.lunic.repositories;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.data.Recipe;
import org.lunic.data.Tag;
import org.lunic.persistance.TagJsonDriver;

import java.util.ArrayList;

public class TagRepository extends Repository {

    private static TagRepository INSTANCE;
    private TagRepository() {
        super(TagJsonDriver.getInstance());
    }

    public static TagRepository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TagRepository();
        }
        return INSTANCE;
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
        for (Container container : ContainerRepository.getInstance().read()) {
            for (Item item : container.items()) {
                if (item.tags().contains(tagToBeUpdated)) {
                    item.tags().remove(tagToBeUpdated);
                    item.tags().add(updatedTag);
                    ContainerRepository.getInstance().update(container, container);
                }
            }
        }
        for (Recipe recipe : RecipeRepository.getInstance().read()) {
            if (recipe.tags().contains(tagToBeUpdated)) {
                recipe.tags().remove(tagToBeUpdated);
                recipe.tags().add(updatedTag);
                RecipeRepository.getInstance().update(recipe, recipe);
            }
        }
        super.update(tagToBeUpdated, updatedTag);
    }

    @Override
    public void delete(Record record) {
        if (record instanceof Tag tag) {
            for (Container container : ContainerRepository.getInstance().read()) {
                for (Item item : container.items()) {
                    if (item.tags().contains(tag)) {
                        item.tags().remove(tag);
                        ContainerRepository.getInstance().update(container, container);
                    }
                }
            }
            for (Recipe recipe : RecipeRepository.getInstance().read()) {
                if (recipe.tags().contains(tag)) {
                    recipe.tags().remove(tag);
                    RecipeRepository.getInstance().update(recipe, recipe);
                }
            }
            super.delete(tag);
        }
    }
}

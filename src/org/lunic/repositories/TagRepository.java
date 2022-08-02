package org.lunic.repositories;

import org.lunic.data.Tag;
import org.lunic.persistance.TagJsonDriver;

import java.util.ArrayList;

public class TagRepository extends Repository {

    public TagRepository() {
        super(new TagJsonDriver());
    }

    public void Create(Tag tag) {
        super.Create(tag);
    }

    public ArrayList<Tag> Read() {
        ArrayList<Tag> tags = new ArrayList<>();
        for (Record record : recordList) {
            if (record instanceof Tag) {
                tags.add((Tag) record);
            }
        }

        return tags;
    }

    public void Update(Tag tagToBeUpdated, Tag updatedTag) {
        super.Update(tagToBeUpdated, updatedTag);
    }

    public void Delete(Tag tag) {
        super.Delete(tag);
    }

}

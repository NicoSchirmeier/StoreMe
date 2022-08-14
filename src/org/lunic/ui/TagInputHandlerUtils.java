package org.lunic.ui;

import org.lunic.data.Tag;
import org.lunic.data.type.TagType;
import org.lunic.repositories.TagRepository;
import org.lunic.ui.helperclasses.Action;
import org.lunic.ui.helperclasses.ConsoleReadingUtils;
import org.lunic.ui.helperclasses.ConsoleSelectionUtils;
import org.lunic.ui.helperclasses.Option;

import java.util.ArrayList;
import java.util.HashSet;

public class TagInputHandlerUtils {
    public static void printCreationDialog() {
        Tag tag = createOrChange(null);
        System.out.println(tag);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Create Tag");
        if (confirmed) {
            TagRepository.getInstance().create(tag);
        }
    }

    public static void printChangeDialog(Record toChange) {
        Tag tag = createOrChange((Tag) toChange);

        System.out.println(toChange);
        System.out.println("->");
        System.out.println(tag);
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Change Tag");
        if (confirmed) {
            TagRepository.getInstance().update((Tag) toChange, tag);
        }
    }

    public static Tag createOrChange(Tag tagToEdit) {
        if (tagToEdit == null) {
            System.out.println("- Create Tag -");
        } else {
            System.out.println("- Edit Tag -");
            System.out.println("Skip by typing \"!\"");
        }

        System.out.println("Enter Name: (Write \"!\" to skip)");
        String name = ConsoleReadingUtils.readString();
        System.out.println("Select Type:");
        TagType type = (TagType) ConsoleSelectionUtils.printTypeSelection(TagType.values());
        System.out.println("Enter Description: (One Line)");
        String description = ConsoleReadingUtils.readString();

        if (tagToEdit != null) {
            if (name.equals("!")) name = tagToEdit.name();
            if (description.equals("!")) description = tagToEdit.description();
        }

        return new Tag(name, type, description);
    }

    public static HashSet<Tag> printSelectTagsDialog(boolean canBeSkipped) {
        HashSet<Tag> tags = new HashSet<>();
        while (true) {
            System.out.println("Currently added Tags: " + tags);
            ArrayList<Option> options = new ArrayList<>();
            options.add(new Option(Action.DONE.name(), Action.DONE));
            options.add(new Option(Action.CREATE.name(), Action.CREATE));
            for (Tag tag : TagRepository.getInstance().read()) {
                options.add(new Option(tag.name(), tag));
            }
            Option option = ConsoleSelectionUtils.displayOptions(options, canBeSkipped);
            if (option.getRootObject() == null) return null;

            if (option.getRootObject() instanceof Action action) {
                if (action.equals(Action.DONE)) {
                    break;
                } else if (action.equals(Action.CREATE)) {
                    printCreationDialog();
                }
            } else if (option.getRootObject() instanceof Tag tag) {
                tags.add(tag);
            }
        }
        if (tags.isEmpty() && canBeSkipped) return null;

        return tags;
    }
}

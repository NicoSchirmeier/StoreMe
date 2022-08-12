package org.lunic.ui;

import org.lunic.repositories.*;
import org.lunic.ui.helperclasses.ConsoleReadingUtils;

public abstract class InputHandler {
    private final Repository repository;
    public InputHandler(Repository repository) {
        this.repository = repository;
    }



    public void printDeletionDialog(Record toDelete) {
        boolean confirmed = ConsoleReadingUtils.printConfirmationDialog("Delete " + toDelete.getClass().getSimpleName());
        if(confirmed) repository.Delete(toDelete);
    }
}

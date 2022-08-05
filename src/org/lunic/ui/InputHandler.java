package org.lunic.ui;

import org.lunic.repositories.Repository;

public abstract class InputHandler {
    Repository repository;
    public InputHandler(Repository repository) {
        this.repository = repository;
    }

    public abstract void print();

    public abstract void printCreationDialog();
    public abstract void printChangeDialog(Record toChange);

    public void printDeletionDialog(Record toDelete) {
        boolean confirmed = ConsoleUtils.printConfirmationDialog("Delete " + toDelete.getClass().getSimpleName());
        if(confirmed) repository.Delete(toDelete);
    }
}

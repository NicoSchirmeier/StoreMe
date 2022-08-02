package org.lunic.observer;

import org.lunic.data.Container;
import org.lunic.data.Item;
import org.lunic.repositories.ContainerRepository;

import java.time.LocalDate;
import java.util.ArrayList;


public class ExpirationObserver {

    public static ArrayList<Item> getSoonExpiringItems(
            ContainerRepository currentRepository
    ) {
        LocalDate currentDate = LocalDate.now();
        for (Container container : currentRepository.Read()) {
            for (Item item : container.items()) {
                //TODO check expiration date
            }
        }
        return null;
    }

}

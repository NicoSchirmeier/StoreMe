package org.lunic;

import org.lunic.observer.ItemConsumptionObserver;
import org.lunic.observer.ItemExpirationObserver;
import org.lunic.repositories.ContainerRepository;
import org.lunic.repositories.ItemTemplateRepository;
import org.lunic.repositories.RecipeRepository;
import org.lunic.repositories.TagRepository;
import org.lunic.ui.*;

public class DataManager {
    //Repositories
    public static final ContainerRepository CONTAINER_REPOSITORY = ContainerRepository.getInstance(); //Singleton
    public static final ItemTemplateRepository ITEM_TEMPLATE_REPOSITORY = new ItemTemplateRepository();
    public static final RecipeRepository RECIPE_REPOSITORY = new RecipeRepository();
    public static final TagRepository TAG_REPOSITORY = new TagRepository();

    //InputHandler
    public static final ContainerInputHandler CONTAINER_INPUT_HANDLER = new ContainerInputHandler();
    public static final RecipeInputHandler RECIPE_INPUT_HANDLER = new RecipeInputHandler();
    public static final TagInputHandler TAG_INPUT_HANDLER = new TagInputHandler();
    public static final ItemInputHandler ITEM_INPUT_HANDLER = new ItemInputHandler();
    public static final ItemTemplateHandler ITEM_TEMPLATE_HANDLER = new ItemTemplateHandler();

    //Observer
    public static final ItemExpirationObserver ITEM_EXPIRATION_OBSERVER = new ItemExpirationObserver();
    public static final ItemConsumptionObserver ITEM_CONSUMPTION_OBSERVER = new ItemConsumptionObserver();
}
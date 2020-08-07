package ru.pavel.diploma;

import ru.pavel.diploma.model.Dish;
import ru.pavel.diploma.model.User;

import java.util.Collections;

import static ru.pavel.diploma.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringComparator(Dish.class, "restaurant");

    public static final int NOT_FOUND = 10;
    public static final int HAMBURGER_ID = START_SEQ + 4;
    public static final int FRENCH_FRIES_ID = START_SEQ + 5;
    public static final int CHICKEN_SOUP_ID = START_SEQ + 6;

    public static final Dish HAMBURGER = new Dish(HAMBURGER_ID, "Hamburger", 700);
    public static final Dish FRENCH_FRIES = new Dish(FRENCH_FRIES_ID, "French fries", 150);
    public static final Dish CHICKEN_SOUP = new Dish(CHICKEN_SOUP_ID, "Chicken soup", 250);

    public static Dish getNew() {
        return new Dish(null, "Surstromming", 1000);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(HAMBURGER);
        updated.setName("UpdatedName");
        return updated;
    }
}

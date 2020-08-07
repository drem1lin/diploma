package ru.pavel.diploma;

import ru.pavel.diploma.model.Restaurant;

import static ru.pavel.diploma.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsWithIgnoringComparator(Restaurant.class, "menu");

    public static final int NOT_FOUND = 10;
    public static final int THE_CASTLE_ID = START_SEQ + 2;
    public static final int RED_SQUARE_ID = START_SEQ + 3;

    public static final Restaurant THE_CASTLE = new Restaurant(THE_CASTLE_ID, "THE CASTLE");
    public static final Restaurant RED_SQUARE = new Restaurant(RED_SQUARE_ID, "RED SQUARE");

    public static Restaurant getNew() {
        return new Restaurant(null, "Surstromming");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RED_SQUARE);
        updated.setName("UpdatedName");
        return updated;
    }
}

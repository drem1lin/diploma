package ru.pavel.diploma;

import ru.pavel.diploma.model.Dish;
import ru.pavel.diploma.model.User;

import java.time.Month;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.pavel.diploma.RestaurantTestData.RED_SQUARE;
import static ru.pavel.diploma.RestaurantTestData.THE_CASTLE;
import static ru.pavel.diploma.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class, "restaurant");
    public static TestMatcher<Dish> DISH_WITH_RESTAURANT_MATCHER =
            TestMatcher.usingAssertions(Dish.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("restaurant.menu", "restaurant.votes").ignoringAllOverriddenEquals().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int NOT_FOUND = 10;
    public static final int HAMBURGER_ID = START_SEQ + 4;
    public static final int FRENCH_FRIES_ID = START_SEQ + 5;
    public static final int CHICKEN_SOUP_ID = START_SEQ + 6;

    public static final Dish HAMBURGER = new Dish(HAMBURGER_ID, "Hamburger", of(2020, Month.JANUARY, 30),700);
    public static final Dish FRENCH_FRIES = new Dish(FRENCH_FRIES_ID, "French fries", of(2020, Month.JANUARY, 30), 150);
    public static final Dish CHICKEN_SOUP = new Dish(CHICKEN_SOUP_ID, "Chicken soup", of(2020, Month.JANUARY, 31), 250);

    public static final List<Dish> DISHES_30_01 = List.of(HAMBURGER, CHICKEN_SOUP);
    public static final List<Dish> THE_CASTLE_DISHES = List.of(HAMBURGER, FRENCH_FRIES);
    public static final List<Dish> DISHES_ALL = List.of(HAMBURGER, CHICKEN_SOUP, FRENCH_FRIES);

    static {
        HAMBURGER.setRestaurant(THE_CASTLE);
        FRENCH_FRIES.setRestaurant(THE_CASTLE);
        CHICKEN_SOUP.setRestaurant(RED_SQUARE);
    }

    public static Dish getNew() {
        return new Dish(null, "Surstromming", of(2020, Month.JANUARY, 30), 1000);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(HAMBURGER);
        updated.setName("UpdatedName");
        return updated;
    }
}

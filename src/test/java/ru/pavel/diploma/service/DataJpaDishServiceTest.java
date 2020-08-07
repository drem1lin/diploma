package ru.pavel.diploma.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.pavel.diploma.model.Dish;
import ru.pavel.diploma.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertThrows;
import static ru.pavel.diploma.DishTestData.*;
import static ru.pavel.diploma.RestaurantTestData.THE_CASTLE_ID;

public class DataJpaDishServiceTest extends ru.pavel.diploma.service.AbstractServiceTest {

    @Autowired
    protected DishService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        Objects.requireNonNull(cacheManager.getCache("dishes")).clear();
    }

    @Test
    public void create() throws Exception {
        Dish created = service.create(getNew(), THE_CASTLE_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, THE_CASTLE_ID), newDish);
    }

    @Test
    public void delete() throws Exception {
        service.delete(HAMBURGER_ID, THE_CASTLE_ID);
        assertThrows(NotFoundException.class, () -> service.get(HAMBURGER_ID, THE_CASTLE_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, THE_CASTLE_ID));
    }

    @Test
    public void get() throws Exception {
        Dish dish = service.get(HAMBURGER_ID, THE_CASTLE_ID);
        DISH_MATCHER.assertMatch(dish, HAMBURGER);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, THE_CASTLE_ID));
    }

    @Test
    public void update() throws Exception {
        Dish updated = getUpdated();
        service.update(updated, THE_CASTLE_ID);
        DISH_MATCHER.assertMatch(service.get(HAMBURGER_ID, THE_CASTLE_ID), getUpdated());
    }

    @Test
    public void getAll() throws Exception {
        List<Dish> all = service.getAll(THE_CASTLE_ID);
        DISH_MATCHER.assertMatch(all, HAMBURGER, FRENCH_FRIES);
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, "  ", 1000), THE_CASTLE_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "User", 5001), THE_CASTLE_ID), ConstraintViolationException.class);
    }
}

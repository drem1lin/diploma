package ru.pavel.diploma.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pavel.diploma.model.Restaurant;
import ru.pavel.diploma.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.pavel.diploma.RestaurantTestData.*;

public class DataJpaRestaurantServiceTest extends ru.pavel.diploma.service.AbstractServiceTest {

    @Autowired
    protected RestaurantService service;

    @Test
    public void create() throws Exception {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant restaurant = getNew();
        restaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, restaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), restaurant);
    }

    @Test
    public void delete() throws Exception {
        service.delete(THE_CASTLE_ID);
        assertThrows(NotFoundException.class, () -> service.get(THE_CASTLE_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = service.get(THE_CASTLE_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, THE_CASTLE);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RED_SQUARE_ID), getUpdated());
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        RESTAURANT_MATCHER.assertMatch(all, THE_CASTLE, RED_SQUARE);
    }

    @Test
    public void createWithException() throws Exception {
        validateRootCause(() -> service.create(new Restaurant(null, "  ")), ConstraintViolationException.class);
    }
}
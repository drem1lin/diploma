package ru.pavel.diploma.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pavel.diploma.model.Restaurant;
import ru.pavel.diploma.model.User;
import ru.pavel.diploma.service.RestaurantService;

import java.util.List;

import static ru.pavel.diploma.util.ValidationUtil.assureIdConsistent;
import static ru.pavel.diploma.util.ValidationUtil.checkNew;

public class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Restaurant restaurant) {
        log.info("update {} with id={}", restaurant);
        service.update(restaurant);
    }

    public Restaurant getWithDishes(int id) {
        log.info("getWithDishes {}", id);
        return service.getWithDishes(id);
    }
}

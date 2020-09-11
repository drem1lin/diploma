package ru.pavel.diploma.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pavel.diploma.View;
import ru.pavel.diploma.model.Restaurant;
import ru.pavel.diploma.service.RestaurantService;

import java.net.URI;
import java.util.List;

@RestController
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurant";
    static final String REST_ADMIN_URL = "/rest/admin/restaurant";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    @GetMapping(value = RestaurantRestController.REST_URL + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return service.get(id);
    }

    @DeleteMapping(value = RestaurantRestController.REST_ADMIN_URL + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        service.delete(id);
    }

    @GetMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("getAll for restaurants");
        return service.getAll();
    }

    @PutMapping(value = RestaurantRestController.REST_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        log.info("update restaurant {}", restaurant);
        service.update(restaurant);
    }

    @PostMapping(value = RestaurantRestController.REST_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated(View.Web.class) @RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = RestaurantRestController.REST_URL + "/{id}/with-dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getWithDishes(@PathVariable int id) {
        return service.getWithDishes(id);
    }
}
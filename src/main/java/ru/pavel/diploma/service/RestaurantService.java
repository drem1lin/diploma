package ru.pavel.diploma.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.pavel.diploma.model.Restaurant;
import ru.pavel.diploma.repository.DataJpaRestaurantRepository;

import java.util.List;

import static ru.pavel.diploma.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private final DataJpaRestaurantRepository repository;

    public RestaurantService(DataJpaRestaurantRepository repository){
        this.repository=repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.id());
    }

    public Restaurant getWithDishes(int id) {
        return checkNotFoundWithId(repository.getWithDishes(id), id);
    }
}

package ru.pavel.diploma.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.pavel.diploma.model.Dish;
import ru.pavel.diploma.repository.DataJpaDishRepository;

import java.util.List;

import static ru.pavel.diploma.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DataJpaDishRepository repository;

    public DishService(DataJpaDishRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @Cacheable("dishes")
    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.id());
    }
}

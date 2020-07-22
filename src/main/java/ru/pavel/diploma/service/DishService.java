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
    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("dishes")
    public List<Dish> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish), dish.id());
    }
}

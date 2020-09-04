package ru.pavel.diploma.repository;

import org.springframework.stereotype.Repository;
import ru.pavel.diploma.model.Restaurant;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository {

    private final CrudRestaurantRepository crudRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return crudRepository.save(restaurant);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return crudRepository.findAll();
    }

    public Restaurant getWithDishes(int id) {
        return crudRepository.getWithDishes(id);
    }
}

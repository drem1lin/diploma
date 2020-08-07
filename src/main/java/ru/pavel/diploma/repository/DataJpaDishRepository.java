package ru.pavel.diploma.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.diploma.model.Dish;

import java.util.List;

@Repository
public class DataJpaDishRepository {

    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaDishRepository(CrudDishRepository crudRepository,CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    public boolean delete(int id, int restaurantId) {
        return crudDishRepository.delete(id, restaurantId) != 0;
    }

    public Dish get(int id, int restaurantId) {
        return crudDishRepository.findById(id)
                .filter(rest -> rest.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    public List<Dish> getAll(int restaurantId) {
        return crudDishRepository.getAll(restaurantId);
    }
}

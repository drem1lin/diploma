package ru.pavel.diploma.repository;

import org.springframework.stereotype.Repository;
import ru.pavel.diploma.model.Dish;

import java.util.List;

@Repository
public class DataJpaDishRepository {

    private final CrudDishRepository crudRepository;

    public DataJpaDishRepository(CrudDishRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Dish save(Dish dish) {
        return crudRepository.save(dish);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Dish get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Dish> getAll() {
        return crudRepository.findAll();
    }
}

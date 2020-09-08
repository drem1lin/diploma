package ru.pavel.diploma.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.diploma.model.Vote;

import java.util.List;

@Repository
public class DataJpaVoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAll(userId);
    }

    public Vote getWithUserAndRestaurant(int id, int userId, int restaurantId) {
        return crudVoteRepository.getWithUserAndRestaurant(id, restaurantId, userId);
    }
}

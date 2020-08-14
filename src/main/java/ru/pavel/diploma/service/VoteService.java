package ru.pavel.diploma.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.pavel.diploma.model.Vote;
import ru.pavel.diploma.repository.DataJpaVoteRepository;

import java.util.List;

import static ru.pavel.diploma.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private final DataJpaVoteRepository repository;

    public VoteService(DataJpaVoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId, restaurantId);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Vote get(int id, int userId, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, userId, restaurantId), id);
    }

    public List<Vote> getAll() {
        return repository.getAll();
    }

    public void update(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "dish must not be null");
        checkNotFoundWithId(repository.save(vote, userId, restaurantId), vote.id());
    }
}

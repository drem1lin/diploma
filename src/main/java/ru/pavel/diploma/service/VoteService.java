package ru.pavel.diploma.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.pavel.diploma.model.Vote;
import ru.pavel.diploma.repository.DataJpaVoteRepository;

import java.time.LocalTime;
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

    @Transactional
    public void update(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "vote must not be null");
        Vote v = repository.get(vote.getId(), userId, restaurantId);
        checkNotFoundWithId(v, vote.getId());
        Assert.isTrue(v.getTime().isBefore(LocalTime.of(11,00)), "It's too late to update you vote");
        checkNotFoundWithId(repository.save(vote, userId, restaurantId), vote.id());
    }

    public Vote getWithUserAndRestaurant(int id, int userId, int restaurantId) {
        return checkNotFoundWithId(repository.getWithUserAndRestaurant(id, userId, restaurantId), id);
    }
}

package ru.pavel.diploma.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.pavel.diploma.UserTestData;
import ru.pavel.diploma.VoteTestData;
import ru.pavel.diploma.model.User;
import ru.pavel.diploma.model.Vote;
import ru.pavel.diploma.util.exception.NotFoundException;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.junit.Assert.assertThrows;
import static ru.pavel.diploma.RestaurantTestData.*;
import static ru.pavel.diploma.UserTestData.*;
import static ru.pavel.diploma.VoteTestData.*;
import static ru.pavel.diploma.VoteTestData.NOT_FOUND;
import static ru.pavel.diploma.VoteTestData.getNew;

public class DataJpaVoteServiceTest extends ru.pavel.diploma.service.AbstractServiceTest{

    @Autowired
    VoteService service;

    @Test
    public void create() throws Exception {
        Vote created = service.create(getNew(), USER_ID, THE_CASTLE_ID);
        int newId = created.id();
        Vote newVote = getNew();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.get(newId, USER_ID, THE_CASTLE_ID), newVote);
    }

    @Test
    public void duplicateVoteCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Vote(null, of(2020, Month.JANUARY, 30, 10, 0)), USER_ID, THE_CASTLE_ID));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_VOTE_1_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_VOTE_1_ID, USER_ID, THE_CASTLE_ID));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() throws Exception {
        Vote vote = service.get(USER_VOTE_1_ID, USER_ID, THE_CASTLE_ID);
        VOTE_MATCHER.assertMatch(vote, USER_VOTE_1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID, THE_CASTLE_ID));
    }

    @Test
    public void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        service.update(updated, USER_ID, THE_CASTLE_ID);
        VOTE_MATCHER.assertMatch(service.get(USER_VOTE_1_ID, USER_ID, THE_CASTLE_ID), VoteTestData.getUpdated());
    }

    @Test
    public void lateUpdate() throws Exception {
        Vote updated = VoteTestData.getLateUpdated();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.update(updated, ADMIN_ID, RED_SQUARE_ID));
    }

    @Test
    public void getAll() throws Exception {
        List<Vote> all = service.getAll();
        VOTE_MATCHER.assertMatch(all, USER_VOTE_1, ADMIN_VOTE, USER_VOTE_2);
    }

    @Test
    void getWithUserAndRestaurant() throws Exception {
        Vote vote = service.getWithUserAndRestaurant(USER_VOTE_1_ID, USER_ID, THE_CASTLE_ID);
        VOTE_WITH_ALL_MATCHER.assertMatch(vote, USER_VOTE_1);
    }

    @Test
    void getWithVotesNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithUserAndRestaurant(NOT_FOUND, NOT_FOUND, NOT_FOUND));
    }
}

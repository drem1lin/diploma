package ru.pavel.diploma.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pavel.diploma.model.Vote;

import static ru.pavel.diploma.RestaurantTestData.THE_CASTLE_ID;
import static ru.pavel.diploma.UserTestData.USER_ID;
import static ru.pavel.diploma.VoteTestData.VOTE_MATCHER;
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
}

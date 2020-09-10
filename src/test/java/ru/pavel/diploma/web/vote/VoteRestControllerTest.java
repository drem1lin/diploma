package ru.pavel.diploma.web.vote;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.diploma.VoteTestData;
import ru.pavel.diploma.model.Vote;
import ru.pavel.diploma.service.VoteService;
import ru.pavel.diploma.util.exception.NotFoundException;
import ru.pavel.diploma.web.AbstractControllerTest;
import ru.pavel.diploma.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.pavel.diploma.RestaurantTestData.THE_CASTLE_ID;
import static ru.pavel.diploma.VoteTestData.*;
import static ru.pavel.diploma.TestUtil.readFromJson;
import static ru.pavel.diploma.TestUtil.userHttpBasic;
import static ru.pavel.diploma.UserTestData.*;
import static ru.pavel.diploma.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.pavel.diploma.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_DATETIME;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(USER_VOTE_1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_VOTE_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VoteTestData.NOT_FOUND)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_VOTE_1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.get(USER_VOTE_1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VoteTestData.NOT_FOUND)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(THE_CASTLE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(voteService.get(USER_VOTE_1_ID, USER_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Vote vote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(THE_CASTLE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote))
                .with(userHttpBasic(USER)));

        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        vote.setId(newId);
        VOTE_MATCHER.assertMatch(created, vote);
        VOTE_MATCHER.assertMatch(voteService.get(newId, USER_ID), vote);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(USER_VOTES));
    }

    @Test
    void createInvalid() throws Exception {
        Vote invalid = new Vote(null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        Vote invalid = new Vote(null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(THE_CASTLE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Vote invalid = new Vote(USER_VOTE_1);
        invalid.setVoteDate(USER_VOTE_2.getVoteDate());
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(THE_CASTLE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DATETIME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Vote invalid = new Vote(null, USER_VOTE_2.getVoteDate(), USER_VOTE_2.getVoteTime());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(THE_CASTLE_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DATETIME));
    }
}
package ru.pavel.diploma.web.json;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import ru.pavel.diploma.UserTestData;
import ru.pavel.diploma.View;
import ru.pavel.diploma.model.Vote;
import ru.pavel.diploma.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.pavel.diploma.VoteTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN_VOTE);
        System.out.println(json);
        Vote vote = JsonUtil.readValue(json, Vote.class);
        VOTE_MATCHER.assertMatch(vote, ADMIN_VOTE);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(VOTES);
        System.out.println(json);
        List<Vote> votes = JsonUtil.readValues(json, Vote.class);
        VOTE_MATCHER.assertMatch(votes, VOTES);
    }

    @Test
    public void writeWithView() throws Exception {
        ObjectWriter uiWriter = JacksonObjectMapper.getMapper().writerWithView(View.JsonUI.class);
        String json = JsonUtil.writeValue(ADMIN_VOTE, uiWriter);
        System.out.println(json);
        assertThat(json, containsString("dateTimeUI"));
    }

    @Test
    void writeOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}
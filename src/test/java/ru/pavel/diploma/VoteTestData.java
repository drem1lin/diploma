package ru.pavel.diploma;

import ru.pavel.diploma.model.Vote;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.pavel.diploma.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsWithIgnoringComparator(Vote.class, "user", "restaurant");

    public static final int NOT_FOUND = 100;
    public static final int USER_VOTE_1_ID = START_SEQ + 7;
    public static final int ADMIN_VOTE_ID = START_SEQ + 8;
    public static final int USER_VOTE_2_ID = START_SEQ + 9;

    public static final Vote USER_VOTE_1 = new Vote(USER_VOTE_1_ID, of(2020, Month.JANUARY, 30, 10, 0));
    public static final Vote ADMIN_VOTE = new Vote(ADMIN_VOTE_ID, of(2020, Month.JANUARY, 30, 12, 0));
    public static final Vote USER_VOTE_2 = new Vote(USER_VOTE_2_ID, of(2020, Month.JANUARY, 31, 10, 0));

    public static Vote getNew() {
        return new Vote(null, of(2020, Month.JANUARY, 30, 17, 0));
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(USER_VOTE_1);
        updated.setVoteDate(of(2020, Month.JANUARY, 30, 11, 30));
        return updated;
    }
}

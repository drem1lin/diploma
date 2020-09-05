package ru.pavel.diploma;

import ru.pavel.diploma.model.User;
import ru.pavel.diploma.model.Vote;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.pavel.diploma.RestaurantTestData.RED_SQUARE;
import static ru.pavel.diploma.RestaurantTestData.THE_CASTLE;
import static ru.pavel.diploma.UserTestData.*;
import static ru.pavel.diploma.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Vote.class, "user", "restaurant");
    public static TestMatcher<Vote> VOTE_WITH_ALL_MATCHER =
            TestMatcher.usingAssertions(Vote.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("user.votes", "restaurant.votes", "restaurant.menu").ignoringAllOverriddenEquals().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int NOT_FOUND = 100;
    public static final int USER_VOTE_1_ID = START_SEQ + 7;
    public static final int ADMIN_VOTE_ID = START_SEQ + 8;
    public static final int USER_VOTE_2_ID = START_SEQ + 9;

    public static final Vote USER_VOTE_1 = new Vote(USER_VOTE_1_ID, of(2020, Month.JANUARY, 30, 10, 0));
    public static final Vote ADMIN_VOTE = new Vote(ADMIN_VOTE_ID, of(2020, Month.JANUARY, 30, 12, 0));
    public static final Vote USER_VOTE_2 = new Vote(USER_VOTE_2_ID, of(2020, Month.JANUARY, 31, 10, 0));

    public static final List<Vote> VOTES = List.of(USER_VOTE_2, ADMIN_VOTE, USER_VOTE_1);

    public static Vote getNew() {
        return new Vote(null, of(2020, Month.JANUARY, 30, 17, 0));
    }

    static {
        USER_VOTE_1.setUser(USER);
        ADMIN_VOTE.setUser(ADMIN);
        USER_VOTE_2.setUser(USER);

        USER_VOTE_1.setRestaurant(THE_CASTLE);
        ADMIN_VOTE.setRestaurant(RED_SQUARE);
        USER_VOTE_2.setRestaurant(RED_SQUARE);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(USER_VOTE_1);
        updated.setVoteDate(of(2020, Month.JANUARY, 30, 11, 30));
        return updated;
    }

    public static Vote getLateUpdated() {
        Vote updated = new Vote(ADMIN_VOTE);
        updated.setVoteDate(of(2020, Month.JANUARY, 30, 11, 30));
        return updated;
    }
}

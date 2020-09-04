package ru.pavel.diploma;

import ru.pavel.diploma.model.Role;
import ru.pavel.diploma.model.User;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.pavel.diploma.DishTestData.*;
import static ru.pavel.diploma.VoteTestData.*;
import static ru.pavel.diploma.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(User.class, "roles", "votes", "password");
    public static TestMatcher<User> USER_WITH_VOTES_MATCHER =
            TestMatcher.usingAssertions(User.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("votes.user", "votes.restaurant", "password").ignoringAllOverriddenEquals().isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });


    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    static {
        USER.setVotes(List.of(USER_VOTE_1, USER_VOTE_2));
        ADMIN.setVotes(List.of(ADMIN_VOTE));
    }

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        return updated;
    }
}

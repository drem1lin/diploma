package ru.pavel.diploma.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.diploma.model.User;
import ru.pavel.diploma.model.Vote;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant JOIN FETCH v.user WHERE v.id = ?1 and v.restaurant.id = ?2 and v.user.id = ?3")
    Vote getWithUserAndRestaurant(int id, int restaurantId, int userId);
}

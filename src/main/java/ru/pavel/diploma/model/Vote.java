package ru.pavel.diploma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.pavel.diploma.View;
import ru.pavel.diploma.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"voteDate", "user_id"}, name = "votes_unique_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "voteDate", nullable = false)
    @NotNull
    @JsonView(View.JsonREST.class)
    private LocalDateTime voteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("User_Back_reference")
    @NotNull(groups = View.Persist.class)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("Restaurant_Back_reference")
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.getId(), v.getVoteDate());
    }

    public Vote(Integer id, LocalDateTime voteDate) {
        super(id);
        this.voteDate = voteDate;
    }

    public LocalDateTime getVoteDate() {
        return voteDate;
    }

    public LocalDate getDate() {
        return voteDate.toLocalDate();
    }

    public LocalTime getTime() {
        return voteDate.toLocalTime();
    }

    public void setVoteDate(LocalDateTime voteDate) {
        this.voteDate = voteDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonGetter
    @JsonView(View.JsonUI.class)
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    public LocalDateTime getDateTimeUI() {
        return voteDate;
    }

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    public void setDateTimeUI(LocalDateTime dateTime) {
        this.voteDate = dateTime;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voteDate" + voteDate +
                '}';
    }
}

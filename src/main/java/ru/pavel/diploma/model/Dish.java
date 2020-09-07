package ru.pavel.diploma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.pavel.diploma.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "id", name = "dishes_unique_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference("RDReference")
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    @Column(name = "menuDate", nullable = false)
    @NotNull
    private LocalDate menuDate;

    public Dish() {
    }

    public Dish(Dish u) {
        this(u.getId(), u.getName(), u.getMenuDate(), u.getPrice());
    }

    public Dish(Integer id, String name, LocalDate date, Integer price) {
        super(id, name);
        this.price = price;
        this.menuDate = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int newPrice) {
        price = newPrice;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant rest) {
        this.restaurant = rest;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", price='" + price + '\'' +
                ", menuDate=" + menuDate +
                '}';
    }
}

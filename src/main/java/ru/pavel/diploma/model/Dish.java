package ru.pavel.diploma.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "id", name = "dishes_unique_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Dish u) {
        this(u.getId(), u.getName(), u.getPrice());
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int newPrice) {
        price = newPrice;
    }

    public Restaurant getRestaurant(){return restaurant;}

    public void setRestaurant(Restaurant rest){this.restaurant = rest;}
}

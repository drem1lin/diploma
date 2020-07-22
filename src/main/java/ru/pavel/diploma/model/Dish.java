package ru.pavel.diploma.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.EnumSet;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "id", name = "dishes_unique_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    private int price;

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
}

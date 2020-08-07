package ru.pavel.diploma.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public Restaurant(){}

    public Restaurant(Restaurant u) {
        this(u.getId(), u.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JsonIgnore
    List<Dish> menu;

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu){
        this.menu = menu;
    }
}

package ru.pavel.diploma.model;

import java.util.List;

public class Restaraunt extends AbstractNamedEntity {
    List<Dish> menu;

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu){
        this.menu = menu;
    }
}

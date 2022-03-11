package ru.voting.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Restaurant extends AbstractBaseEntity{
    @NotNull
    @Size(min = 3, max = 320)
    private String name;

    @Min(0)
    private int votes;
    private List<Dish> dishes;

    public Restaurant(Integer id, String name, int votes, List<Dish> dishes) {
        super(id);
        this.name = name;
        this.votes = votes;
        this.dishes = dishes;
    }

    public Restaurant() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return votes == that.votes && name.equals(that.name) && Objects.equals(dishes, that.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, votes, dishes);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", votes=" + votes +
                ", dishes=" + dishes +
                '}';
    }
}

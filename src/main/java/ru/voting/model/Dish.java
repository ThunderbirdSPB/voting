package ru.voting.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class Dish extends AbstractBaseEntity{
    @NotNull
    @Size(min = 3, max = 320)
    private String name;
    @NotNull
    @DecimalMin("0.01")
    private Double price;

    public Dish() {}

    public Dish(Integer id, String name, Double price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(name, dish.name) && Objects.equals(price, dish.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}

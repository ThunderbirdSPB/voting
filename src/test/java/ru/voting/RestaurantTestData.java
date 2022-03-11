package ru.voting;

import ru.voting.model.Dish;
import ru.voting.model.Restaurant;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RestaurantTestData {
    public static final int DISH_ID = 1;
    public static final int NOT_EXISTING_DISH_ID = Integer.MAX_VALUE;

    public static final int RESTAURANT_ID = 1;
    public static final int NOT_EXISTING_RESTAURANT_ID = Integer.MAX_VALUE;

    public static final Dish newDish = new Dish(null, "newDish", 1.1);
    public static final Dish DISH = new Dish(DISH_ID, "ceasar salad", 1.2);
    public static final Dish dishWithNegativePrice = new Dish(null,"newDish", -1.1);
    public static final Dish dishWithEmptyName = new Dish(null,"", -1.1);
    public static final Dish dishWithNullName = new Dish(null, null, -1.1);

    public static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID, "KFC", 0, null);
    public static final Restaurant newRestaurant = new Restaurant(null, "new", 0, null);
    public static final Restaurant restaurantWithNullName = new Restaurant(null,null, 0, null);
    public static final Restaurant restaurantWithNegativeVotes = new Restaurant(null,null, 0, null);

    //dishes in restaurant with id = 1
    public static final Collection<Dish> dishes = List.of(new Dish(null,"fried chicken",3.0),
            new Dish(null, "chicken legs", 1.2));

    public static final Collection<Restaurant> restaurants = List.of(new Restaurant(null, "DODO Pizza", 0, null),
            new Restaurant(null, "KFC", 0, null), new Restaurant(null, "Mac", 0, null),
            new Restaurant(null, "Sicilia pizza", 0, null), new Restaurant(null, "Starbucks", 0, null));
}

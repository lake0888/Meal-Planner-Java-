package mealplanner.Services;

import mealplanner.Model.Meal.Ingredient;

import java.util.List;

public interface IIngredient {
    List<Ingredient> findAll(int meal_id);
    Ingredient save(Ingredient ingredient);

    int lastId();
}

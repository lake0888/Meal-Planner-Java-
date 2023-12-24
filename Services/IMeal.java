package mealplanner.Services;

import mealplanner.Model.Meal.Category;
import mealplanner.Model.Meal.Meal;
import java.util.List;

public interface IMeal {

    List<Meal> findAll();

    Meal save(Meal meal);

    List<Meal> findByCategory(Category category);

    int lastId();
}

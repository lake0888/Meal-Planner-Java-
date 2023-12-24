package mealplanner.Services;

import mealplanner.DAO.IngredientDAO;
import mealplanner.Model.Meal.Ingredient;

import java.util.List;

public class IngredientService implements IIngredient {
    private IngredientDAO ingredientDAO;

    public IngredientService() {
        this.ingredientDAO = new IngredientDAO();
    }

    @Override
    public List<Ingredient> findAll(int meal_id) {
        return ingredientDAO.findAll(meal_id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredientDAO.save(ingredient);
    }

    @Override
    public int lastId() {
        return ingredientDAO.lastId();
    }
}

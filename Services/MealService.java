package mealplanner.Services;

import mealplanner.DAO.MealDAO;
import mealplanner.Model.Meal.Category;
import mealplanner.Model.Meal.Meal;

import java.util.List;

public class MealService implements IMeal {

    private MealDAO mealDAO;

    public MealService() {
        this.mealDAO = new MealDAO();
    }

    @Override
    public List<Meal> findAll() {
        return mealDAO.findAll();
    }

    @Override
    public Meal save(Meal meal) {
        return mealDAO.save(meal);
    }

    @Override
    public List<Meal> findByCategory(Category category) {
        return mealDAO.findByCategory(category);
    }

    @Override
    public int lastId() {
        return mealDAO.lastId();
    }
}

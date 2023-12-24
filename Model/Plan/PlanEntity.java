package mealplanner.Model.Plan;

import mealplanner.Model.Meal.Category;

import java.util.Objects;

public class PlanEntity {
    private int id;
    private Category category;
    private int meal_id;
    public static int lastId = 1;

    public PlanEntity(Category category, int meal_id) { this(0, category, meal_id); }
    public PlanEntity(int id, Category category, int meal_id) {
        this.id = id;
        this.category = category;
        this.meal_id = meal_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanEntity that = (PlanEntity) o;
        return id == that.id && meal_id == that.meal_id && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, meal_id);
    }

    @Override
    public String toString() {
        return "PlanEntity{" +
                "id=" + id +
                ", category=" + category +
                ", meal_id=" + meal_id +
                '}';
    }
}

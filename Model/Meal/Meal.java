package mealplanner.Model.Meal;

import java.util.List;
import java.util.Objects;

public class Meal {
    private int id;
    private String name;
    private List<Ingredient> ingredientList;
    private Category category;

    public static int lastId = 1;

    public Meal() {
        this("", Category.OTHER);
    }

    public Meal(String name, Category category) { this(0, name, category); }
    public Meal(int id, String name, Category category) {
        this(id, name, category, List.of());
    }

    public Meal(int id, String name, Category category, List<Ingredient> ingredientList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ingredientList = ingredientList;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public List<Ingredient> getIngredientList() { return this.ingredientList; }
    public void setIngredientList(List<Ingredient> ingredientList) { this.ingredientList = ingredientList; }

    public Category getCategory() { return this.category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(id, meal.id) && Objects.equals(name, meal.name) && Objects.equals(ingredientList, meal.ingredientList) && Objects.equals(category, meal.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, ingredientList);
    }

    @Override
    public String toString() {
        //String categoryName = "Category: " + category.getCategoryName() + "\n";
        String mealName = "Name: " + name + "\n";
        StringBuilder ingredients = new StringBuilder();
        ingredients.append("Ingredients:").append("\n");
        for (Ingredient i : ingredientList) {
            ingredients.append(i).append("\n");
        }
        //return categoryName + mealName + ingredients;
        return mealName + ingredients;
    }
}

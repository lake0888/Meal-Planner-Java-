package mealplanner.Model.Meal;

import java.util.Objects;

public class Ingredient {
    private int id;
    private String name;
    private int mead_id;
    public static int lastId;

    public Ingredient() {
        this("", 0);
    }
    public Ingredient(String name, int mead_id) {
        this(0, name, mead_id);
    }
    public Ingredient(int id, String name, int meal_id) {
        this.id = id;
        this.name = name;
        this.mead_id = meal_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMead_id() {
        return mead_id;
    }

    public void setMead_id(int mead_id) {
        this.mead_id = mead_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient other = (Ingredient) o;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(mead_id, other.mead_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mead_id);
    }

    @Override
    public String toString() {
        return name;
    }
}

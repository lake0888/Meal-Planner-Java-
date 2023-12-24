package mealplanner.Model.Plan;

import java.util.Arrays;

public class Plan {
    private MealDay[] mealWeek;
    public Plan() { this(new MealDay[7]); }
    public Plan(MealDay[] mealWeek) {
        this.mealWeek = mealWeek;
    }

    public MealDay[] getMealWeek() {
        return mealWeek;
    }
    public void setMealWeek(MealDay[] mealWeek) { this.mealWeek = mealWeek; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan other = (Plan) o;
        return Arrays.equals(mealWeek, other.mealWeek);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mealWeek);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Arrays.stream(mealWeek).forEach(MealDay -> {
            str.append(MealDay.toString()).append("\n");
        });
        return str.toString();
    }
}

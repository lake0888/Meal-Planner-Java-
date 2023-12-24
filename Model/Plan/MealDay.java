package mealplanner.Model.Plan;

import mealplanner.Model.Meal.Meal;

import java.util.Arrays;
import java.util.Objects;

public class MealDay {
    private Weekday weekday;
    private Meal[] mealList;

    public MealDay() {
        this(Weekday.MONDAY, new Meal[3]);
    }

    public MealDay(Weekday weekday, Meal[] mealList) {
        this.weekday = weekday;
        this.mealList = mealList;
    }

    public Weekday getWeekdays() {
        return weekday;
    }

    public void setWeekdays(Weekday weekday) {
        this.weekday = weekday;
    }

    public Meal[] getMealList() {
        return mealList;
    }

    public void setMealList(Meal[] mealList) {
        this.mealList = mealList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealDay other = (MealDay) o;
        return Objects.equals(weekday, other.weekday) && Arrays.equals(mealList, other.mealList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekday, Arrays.hashCode(mealList));
    }

    @Override
    public String toString() {
        String menu = "";
        for (Meal meal : mealList) {
            menu += meal.getCategory().getCategoryName() + ": " + meal.getName() + "\n";
        }
        return weekday.getNameWeekday() + "\n" + menu;
    }
}

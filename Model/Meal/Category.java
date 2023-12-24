package mealplanner.Model.Meal;

public enum Category {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),
    OTHER("other");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category getCategoryByName(String name) {
        for (Category category : Category.values()) {
            if (category.categoryName.equalsIgnoreCase(name))
                return category;
        }
        return Category.OTHER;
    }

    public String getCategoryName() { return this.categoryName; }
}

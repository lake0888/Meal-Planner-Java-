package mealplanner;

import mealplanner.Model.Meal.Category;
import mealplanner.Model.Meal.Ingredient;
import mealplanner.Model.Meal.Meal;
import mealplanner.Model.Plan.MealDay;
import mealplanner.Model.Plan.Plan;
import mealplanner.Model.Plan.PlanEntity;
import mealplanner.Model.Plan.Weekday;
import mealplanner.Services.IngredientService;
import mealplanner.Services.MealService;
import mealplanner.Services.PlanService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static List<Meal> mealList = new ArrayList<>();
    static Plan plan = new Plan();
    static final MealService mealService = new MealService();
    static final IngredientService ingredientService = new IngredientService();
    static final PlanService planService = new PlanService();

    public static void main(String[] args) {
        //INIT DATABASE
        ConnectionUtil.initDatabase();
        initMeals();
        Meal.lastId = mealService.lastId() + 1;
        Ingredient.lastId = ingredientService.lastId() + 1;
        PlanEntity.lastId = planService.lastId() + 1;
        mainMenu();
    }

    public static void initMeals() {
        mealList = mealService.findAll();
        mealList.forEach(meal -> meal.setIngredientList(ingredientService.findAll(meal.getId())));

        loadPlan();
    }

    public static void mainMenu() {
        String option = "";
        while (!option.equalsIgnoreCase("exit")) {
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            option = scanner.nextLine();
            if (option.equalsIgnoreCase("add")) {
                createMeal();
            } else if (option.equalsIgnoreCase("show")) {
                showMeals();
            } else if (option.equalsIgnoreCase("plan")) {
                createPlan();
                showPlan();
            } else if (option.equalsIgnoreCase("save")) {
                save();
            } else if (option.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
            }
        }
    }

    public static Category selectCategory(String message) {
        System.out.println(message);

        Category category = Category.OTHER;
        while ((category = Category.getCategoryByName(scanner.nextLine())) == Category.OTHER) {
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
        }
        return category;
    }

    public static String createMealName() {
        System.out.println("Input the meal's name:");

        String mealName = "";
        while (!(mealName = scanner.nextLine()).matches("[a-zA-Z ]+")) {
            System.out.println("Wrong format. Use letters only!");
        }
        return mealName;
    }

    public static List<Ingredient> createIngredientList() {
        System.out.println("Input the ingredients:");

        List<Ingredient> ingredientList = new ArrayList<>();
        List<Ingredient> validList = new ArrayList<>();
        do {
            ingredientList = Arrays.stream(scanner.nextLine().split(","))
                    .map(name -> {
                        name = name.replaceAll("\\s+", " ").replaceAll("^\\s+", "");
                        return new Ingredient(name, 0);
                    }).toList();

            validList = ingredientList.stream()
                    .filter(ingredient -> ingredient.getName().matches("[a-zA-Z ]+"))
                    .toList();

            if (ingredientList.size() != validList.size()) {
                System.out.println("Wrong format. Use letters only!");
            }
        } while (ingredientList.size() != validList.size());
        return ingredientList;
    }

    public static void createMeal() {
        Category category = selectCategory("Which meal do you want to add (breakfast, lunch, dinner)?");
        String mealName = createMealName();
        List<Ingredient> ingredientList = createIngredientList();
        createMeal(category, mealName, ingredientList);
    }

    public static void createMeal(Category category, String mealName, List<Ingredient> ingredientList) {
        Meal meal = mealService.save(new Meal(mealName, category));

        if (meal != null) {
            ingredientList.forEach(ingredient -> {
                ingredient.setMead_id(meal.getId());
                ingredient.setId(ingredientService.save(ingredient).getId());
            });

            meal.setIngredientList(ingredientList);
        }

        mealList.add(meal);
        System.out.println("The meal has been added!");
    }

    public static void showMeals() {
        if (mealList.isEmpty()) {
            System.out.println("No meals saved. Add a meal first.");
        } else {
            Category category = selectCategory("Which category do you want to print (breakfast, lunch, dinner)?");
            var filterList = mealList.stream()
                    .filter(meal -> meal.getCategory() == category)
                    .toList();
            //var filterList = mealService.findByCategory(category);
            if (filterList.isEmpty()) {
                System.out.println("No meals found.");
            } else {
                System.out.printf("Category: %s%n%n", category.getCategoryName());
                //filterList.forEach(meal -> meal.setIngredientList(ingredientService.findAll(meal.getId())));
                filterList.forEach(System.out::println);
            }
        }
    }

    public static void createPlan() {
        for (Weekday weekday : Weekday.values()) {
            //WEEKDAY
            System.out.println(weekday.getNameWeekday());
            //BREAKFAST
            var breakfastList = mealService.findByCategory(Category.BREAKFAST);
            breakfastList.forEach(meal -> System.out.println(meal.getName()));
            Meal breakfast = selectMeal(breakfastList, Category.BREAKFAST, weekday);
            planService.save(new PlanEntity(Category.BREAKFAST, breakfast.getId()));

            //LUNCH
            var lunchList = mealService.findByCategory(Category.LUNCH);
            lunchList.forEach(meal -> System.out.println(meal.getName()));
            Meal lunch = selectMeal(lunchList, Category.LUNCH, weekday);
            planService.save(new PlanEntity(Category.LUNCH, lunch.getId()));

            //DINNER
            var dinnerList = mealService.findByCategory(Category.DINNER);
            dinnerList.forEach(meal -> System.out.println(meal.getName()));
            Meal dinner = selectMeal(dinnerList, Category.DINNER, weekday);
            planService.save(new PlanEntity(Category.DINNER, dinner.getId()));

            MealDay mealDay = new MealDay(weekday, new Meal[]{breakfast, lunch, dinner});
            plan.getMealWeek()[weekday.ordinal()] = mealDay;
            System.out.printf("Yeah! We planned the meals for %s.%n%n", weekday.getNameWeekday());
        }
    }

    public static Meal selectMeal(List<Meal> mealList, Category category, Weekday weekday) {
        System.out.printf("Choose the %s for %s from the list above:%n", category.getCategoryName(), weekday.getNameWeekday());

        Meal meal = new Meal();
        while ((meal = findMeal(mealList, scanner.nextLine())).getId() == 0) {
            System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
        }
        return meal;
    }

    public static Meal findMeal(List<Meal> mealList, String name) {
        for (Meal meal : mealList) {
            if (meal.getName().equalsIgnoreCase(name))
                return meal;
        }
        return new Meal();
    }

    public static void showPlan() {
        System.out.println(plan);
    }

    public static void loadPlan() {
        /*
         * FOR EACH DAY YOU HAVE 3 MEALS
         * */
        int cont = 0;
        int day = 0;
        Meal[] mealList = new Meal[3];
        List<PlanEntity> planEntityList = planService.findAll();
        for (PlanEntity planEntity : planEntityList) {
            int meal_id = planEntity.getMeal_id();

            Meal meal = Main.mealList.stream().filter(currentMeal -> currentMeal.getId() == meal_id).findFirst().get();
            if (cont < 3) {
                mealList[cont++] = meal;
            }
            if (cont == 3) {
                cont = 0;
                MealDay mealDay = new MealDay(Weekday.values()[day], mealList);
                plan.getMealWeek()[day++] = mealDay;
                mealList = new Meal[3];
            }
        }
    }

    public static void save() {
        if (Arrays.stream(plan.getMealWeek()).anyMatch(Objects::isNull)) {
            System.out.println("Unable to save. Plan your meals first.");
        } else {
            Map<String, Integer> shoppingList = createShoppingList();
            StringBuilder printList = printShoppingList(shoppingList);

            System.out.println("Input a filename:");
            String fileName = scanner.nextLine();

            File file = new File(fileName);
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(printList.toString());
                writer.close();
                System.out.println("Saved!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Map<String, Integer> createShoppingList() {
        Map<String, Integer> map = new HashMap<>();
        Arrays.stream(plan.getMealWeek()).forEach(mealDay -> {
            var mealList = mealDay.getMealList();
            Arrays.stream(mealList).forEach(meal -> {
                var ingredientList = meal.getIngredientList();
                ingredientList.forEach(ingredient -> {
                    String nameIngredient = ingredient.getName();
                    if (!map.containsKey(nameIngredient)) {
                        map.put(nameIngredient, 1);
                    } else {
                        map.put(nameIngredient, map.get(nameIngredient) + 1);
                    }
                });
            });
        });
        return map;
    }

    public static StringBuilder printShoppingList(Map<String, Integer> map) {
        StringBuilder shoppingList = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String ingredient = entry.getKey();
            String cont = (entry.getValue() != 1) ? "x" + entry.getValue() : "";
            shoppingList.append(ingredient);
            if (!cont.isEmpty()) shoppingList.append(" ").append(cont);
            shoppingList.append("\n");
        }
        return shoppingList;
    }
}
package mealplanner.DAO;

import mealplanner.Model.Meal.Category;
import mealplanner.ConnectionUtil;
import mealplanner.Model.Meal.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDAO {
    private final Connection connection;

    public MealDAO() {
        this.connection = ConnectionUtil.getConnection();
    }

    public List<Meal> findAll() {
        List<Meal> mealList = new ArrayList<>();

        String sql = "SELECT * FROM meals ORDER BY meal_id ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int meal_id = rs.getInt("meal_id");
                String mealName = rs.getString("meal");
                String categoryName = rs.getString("category");

                mealList.add(new Meal(meal_id, mealName, Category.getCategoryByName(categoryName), List.of()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return mealList;
    }

    public Meal save(Meal meal) {
        String sql = "INSERT INTO meals (meal_id, meal, category) VALUES (?, ?, ?)";
        try {
            //PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Meal.lastId);
            ps.setString(2, meal.getName());
            ps.setString(3, meal.getCategory().getCategoryName());

            ps.executeUpdate();
            meal.setId(Meal.lastId++);
            /*
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                meal.setId(id);
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return meal;
    }

    public List<Meal> findByCategory(Category category) {
        List<Meal> mealList = new ArrayList<>();
        String sql = "SELECT * FROM meals WHERE category = ? ORDER BY meal ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, category.getCategoryName());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int meal_id = rs.getInt("meal_id");
                String mealName = rs.getString("meal");

                Meal meal = new Meal(meal_id, mealName, category);
                mealList.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return mealList;
    }

    public int lastId() {
        String sql = "SELECT MAX(meal_id) FROM meals";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

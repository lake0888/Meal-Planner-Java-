package mealplanner.DAO;

import mealplanner.ConnectionUtil;
import mealplanner.Model.Meal.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    private final Connection connection;

    public IngredientDAO() {
        this.connection = ConnectionUtil.getConnection();
    }

    public List<Ingredient> findAll(int meal_id) {
        List<Ingredient> ingredientList = new ArrayList<>();

        String sql = "SELECT * FROM ingredients WHERE meal_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, meal_id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ingredient_id");
                String name = rs.getString("ingredient");
                ingredientList.add(new Ingredient(id, name, meal_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return ingredientList;
    }

    public Ingredient save(Ingredient ingredient) {
        String sql = "INSERT INTO ingredients (ingredient_id, ingredient, meal_id) VALUES (?, ?, ?)";
        try {
            //PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Ingredient.lastId);
            ps.setString(2, ingredient.getName());
            ps.setInt(3, ingredient.getMead_id());

            ps.executeUpdate();
            ingredient.setId(Ingredient.lastId++);
            /*
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                ingredient.setId(id);
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return ingredient;
    }

    public int lastId() {
        String sql = "SELECT MAX(ingredient_id) FROM ingredients";

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

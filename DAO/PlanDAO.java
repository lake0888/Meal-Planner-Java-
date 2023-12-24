package mealplanner.DAO;

import mealplanner.ConnectionUtil;
import mealplanner.Model.Meal.Category;
import mealplanner.Model.Plan.Plan;
import mealplanner.Model.Plan.PlanEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    private final Connection connection;

    public PlanDAO() {
        this.connection = ConnectionUtil.getConnection();
    }

    public List<PlanEntity> findAll() {
        List<PlanEntity> planList = new ArrayList<>();
        String sql = "SELECT * FROM plan ORDER BY plan_id ASC";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("plan_id");
                Category category = Category.getCategoryByName(rs.getString("category"));
                int meal_id = rs.getInt("meal_id");

                planList.add(new PlanEntity(id, category, meal_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return planList;
    }

    public PlanEntity save(PlanEntity planEntity) {
        String sql = "INSERT INTO plan (plan_id, category, meal_id) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, PlanEntity.lastId);
            ps.setString(2, planEntity.getCategory().getCategoryName());
            ps.setInt(3, planEntity.getMeal_id());

            ps.executeUpdate();
            planEntity.setId(PlanEntity.lastId++);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return planEntity;
    }

    public int lastId() {
        String sql = "SELECT MAX(plan_id) FROM plan";

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

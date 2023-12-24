package mealplanner;

import java.sql.*;

public class ConnectionUtil {

    private static String url = "jdbc:postgresql://localhost:5432/meals_db";
    private static String username = "postgres";
    private static String password = "1111";
    private static Connection connection = null;

    public ConnectionUtil() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
                initDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void initDatabase() {
        if (connection != null) {
            /*
            String sql = """
                    CREATE SEQUENCE IF NOT EXISTS meals_meal_id_seq INCREMENT 1 START 1;
                    
                    CREATE TABLE IF NOT EXISTS meals (
                        meal_id INTEGER NOT NULL DEFAULT nextval('meals_meal_id_seq'::regclass),
                        meal VARCHAR,
                        category VARCHAR,
                        PRIMARY KEY (meal_id)
                    );
                    
                    CREATE SEQUENCE IF NOT EXISTS ingredients_ingredient_id_seq INCREMENT 1 START 1;
                                    
                    CREATE TABLE IF NOT EXISTS ingredients (
                        ingredient_id INTEGER NOT NULL DEFAULT nextval('ingredients_ingredient_id_seq'::regclass),
                        ingredient VARCHAR,
                        meal_id INTEGER,
                        PRIMARY KEY (ingredient_id),
                        FOREIGN KEY (meal_id) REFERENCES meals (meal_id)
                    );
                    """;
            */

            String sql = """
                    CREATE TABLE IF NOT EXISTS meals (
                        meal_id INTEGER NOT NULL,
                        meal VARCHAR,
                        category VARCHAR,
                        PRIMARY KEY (meal_id)
                    );
                                    
                    CREATE TABLE IF NOT EXISTS ingredients (
                        ingredient_id INTEGER NOT NULL,
                        ingredient VARCHAR,
                        meal_id INTEGER NOT NULL,
                        PRIMARY KEY (ingredient_id),
                        FOREIGN KEY (meal_id) REFERENCES meals (meal_id)
                    );
                    
                    CREATE TABLE IF NOT EXISTS plan (
                        plan_id INTEGER NOT NULL,
                        category VARCHAR NOT NULL,
                        meal_id INTEGER NOT NULL,
                        PRIMARY KEY (plan_id),
                        FOREIGN KEY (meal_id) REFERENCES meals (meal_id)
                    );
                    """;
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            connection = getConnection();
        }
    }
}

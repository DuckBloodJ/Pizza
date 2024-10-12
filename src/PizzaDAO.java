import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO {
    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        String query = "SELECT * FROM pizzas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pizzas.add(new Pizza(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getBoolean("vegetarian"),
                    rs.getBoolean("vegan"),
                    rs.getString("ingredients"),
                    rs.getBoolean("isPizza")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzas;
    }

    public boolean addPizza(Pizza pizza) {
        String query = "INSERT INTO pizzas (name, price, vegetarian, vegan, ingredients) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pizza.getName());
            stmt.setDouble(2, pizza.getPrice());
            stmt.setBoolean(3, pizza.isVegetarian());
            stmt.setBoolean(4, pizza.isVegan());
            stmt.setString(5, pizza.getIngredients());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
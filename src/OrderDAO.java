import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                String status = rs.getString("status");

                // Fetch pizzas for this order
                PizzaDAO pizzaDAO = new PizzaDAO();
                List<Product> products = pizzaDAO.getAllPizzas(); // Assuming getPizzasByOrderId method

                orders.add(new Order(orderId, customerId, products, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}